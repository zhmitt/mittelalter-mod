package de.mittelalter.gametest;

import java.util.UUID;
import java.util.function.Consumer;

import com.mojang.serialization.MapCodec;

import de.mittelalter.MittelalterMod;
import de.mittelalter.camelot.CamelotSavedData;
import de.mittelalter.camelot.RenownSavedData;
import de.mittelalter.registry.ModEntities;
import de.mittelalter.role.ArthurianRole;
import de.mittelalter.role.RoleSavedData;
import de.mittelalter.role.RoleTransitions;
import de.mittelalter.soldier.SoldierEntity;
import de.mittelalter.soldier.SoldierOrder;
import de.mittelalter.soldier.SoldierRole;
import de.mittelalter.soldier.SoldierRules;
import de.mittelalter.tournament.TournamentMode;
import de.mittelalter.tournament.TournamentSession;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestInstance;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Rotation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;

/** Dedicated-server integration coverage for the V1 release candidate. */
@EventBusSubscriber(modid = MittelalterMod.MODID)
public final class MittelalterGameTests {
    private static final Identifier EMPTY_STRUCTURE = Identifier.withDefaultNamespace("empty");
    private static final Holder<TestEnvironmentDefinition<?>> DEFAULT_ENVIRONMENT =
            Holder.direct(new TestEnvironmentDefinition.AllOf());

    private MittelalterGameTests() {}

    @SubscribeEvent
    public static void register(RegisterGameTestsEvent event) {
        register(event, "soldier_state_round_trip", 40, MittelalterGameTests::soldierStateRoundTrip);
        register(event, "soldier_combat_roles", 40, MittelalterGameTests::soldierCombatRoles);
        register(event, "soldier_command_authority", 20, MittelalterGameTests::soldierCommandAuthority);
        register(event, "camelot_tournament_role_progression", 20, MittelalterGameTests::camelotTournamentRoleProgression);
    }

    private static void register(RegisterGameTestsEvent event, String path, int maxTicks,
                                 Consumer<GameTestHelper> body) {
        TestData<Holder<TestEnvironmentDefinition<?>>> data = new TestData<>(
                DEFAULT_ENVIRONMENT, EMPTY_STRUCTURE, maxTicks, 0, true, Rotation.NONE);
        event.registerTest(id(path), new DirectGameTestInstance(data, body));
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MittelalterMod.MODID, path);
    }

    private static void soldierStateRoundTrip(GameTestHelper helper) {
        Player owner = helper.makeMockPlayer(GameType.CREATIVE);
        SoldierEntity.Archer original = helper.spawn(ModEntities.ARCHER_SOLDIER.get(), 1, 2, 1);
        LivingEntity target = helper.spawn(EntityType.ZOMBIE, 3, 2, 1);
        original.recruit(owner);
        original.setOrder(SoldierOrder.ATTACK, target);

        SoldierEntity.Archer restored = ModEntities.ARCHER_SOLDIER.get().create(
                helper.getLevel(), EntitySpawnReason.STRUCTURE);
        helper.assertTrue(restored != null, "Archer entity type must create an entity");
        restored.restoreFrom(original);
        helper.assertValueEqual(restored.ownerId(), owner.getUUID(), "persisted soldier owner");
        helper.assertValueEqual(restored.order(), SoldierOrder.ATTACK, "persisted soldier order");
        helper.assertValueEqual(restored.role(), SoldierRole.ARCHER, "entity subtype role");

        target.discard();
        restored.aiStep();
        helper.assertValueEqual(restored.order(), SoldierOrder.FOLLOW, "invalid target fallback order");
        helper.succeed();
    }

    private static void soldierCombatRoles(GameTestHelper helper) {
        SoldierEntity.Foot foot = helper.spawn(ModEntities.FOOT_SOLDIER.get(), 1, 2, 1);
        SoldierEntity.Archer archer = helper.spawn(ModEntities.ARCHER_SOLDIER.get(), 3, 2, 1);
        LivingEntity target = helper.spawn(EntityType.ZOMBIE, 6, 2, 1);
        helper.assertFalse(foot.getMainHandItem().getItem() instanceof BowItem,
                "Foot soldier must retain melee equipment");
        helper.assertTrue(archer.getMainHandItem().getItem() instanceof BowItem,
                "Archer must retain ranged equipment");

        int arrowsBefore = helper.getLevel().getEntitiesOfClass(AbstractArrow.class,
                helper.getBounds().inflate(8.0)).size();
        foot.performRangedAttack(target, 1.0F);
        helper.assertValueEqual(helper.getLevel().getEntitiesOfClass(AbstractArrow.class,
                helper.getBounds().inflate(8.0)).size(), arrowsBefore, "foot soldier projectile count");
        archer.performRangedAttack(target, 1.0F);
        helper.assertValueEqual(helper.getLevel().getEntitiesOfClass(AbstractArrow.class,
                helper.getBounds().inflate(8.0)).size(), arrowsBefore + 1, "archer projectile count");
        helper.getLevel().getEntitiesOfClass(AbstractArrow.class, helper.getBounds().inflate(8.0))
                .forEach(AbstractArrow::discard);
        helper.succeed();
    }

    private static void soldierCommandAuthority(GameTestHelper helper) {
        Player owner = helper.makeMockPlayer(GameType.CREATIVE);
        Player stranger = helper.makeMockPlayer(GameType.CREATIVE);
        SoldierEntity.Foot soldier = helper.spawn(ModEntities.FOOT_SOLDIER.get(), 1, 2, 1);
        soldier.recruit(owner);
        owner.snapTo(soldier.getX(), soldier.getY(), soldier.getZ(), 0.0F, 0.0F);
        stranger.snapTo(soldier.getX(), soldier.getY(), soldier.getZ(), 0.0F, 0.0F);

        double radius = 32.0;
        helper.assertTrue(SoldierRules.isCommandEligible(owner.getUUID(), soldier.ownerId(),
                owner.distanceToSqr(soldier), radius), "Owner inside radius must be eligible");
        helper.assertFalse(SoldierRules.isCommandEligible(stranger.getUUID(), soldier.ownerId(),
                stranger.distanceToSqr(soldier), radius), "Non-owner must be rejected");
        helper.assertFalse(SoldierRules.isCommandEligible(owner.getUUID(), soldier.ownerId(),
                radius * radius + 1.0, radius), "Owner outside radius must be rejected");
        helper.assertTrue(SoldierRules.canRecruit(15, 16), "Recruitment below cap must pass");
        helper.assertFalse(SoldierRules.canRecruit(16, 16), "Recruitment at cap must be rejected");
        helper.succeed();
    }

    private static void camelotTournamentRoleProgression(GameTestHelper helper) {
        UUID player = UUID.randomUUID();
        BlockPos firstCamelot = helper.absolutePos(new BlockPos(2, 2, 2));
        CamelotSavedData camelot = CamelotSavedData.get(helper.getLevel());
        helper.assertTrue(camelot.claim(firstCamelot), "First Camelot claim must succeed");
        helper.assertFalse(camelot.claim(firstCamelot.offset(20, 0, 0)), "Second Camelot claim must fail");
        helper.assertValueEqual(camelot.location().orElseThrow(), firstCamelot, "persisted Camelot location");

        RenownSavedData renown = RenownSavedData.get(helper.getLevel());
        for (int tournament = 0; tournament < 8; tournament++) {
            TournamentSession session = new TournamentSession(TournamentMode.MELEE, 0, 0, 0, 0);
            helper.assertValueEqual(session.recordKill(TournamentMode.MELEE, true, 0.5, 0.5, 0.5, 1),
                    TournamentSession.Outcome.PROGRESSED, "first tournament kill");
            helper.assertValueEqual(session.recordKill(TournamentMode.MELEE, true, 0.5, 0.5, 0.5, 2),
                    TournamentSession.Outcome.PROGRESSED, "second tournament kill");
            helper.assertValueEqual(session.recordKill(TournamentMode.MELEE, true, 0.5, 0.5, 0.5, 3),
                    TournamentSession.Outcome.CHAMPION, "champion tournament completion");
            renown.award(player, 20);
        }
        helper.assertValueEqual(renown.points(player), 160, "tournament-earned renown");
        helper.assertTrue(RoleTransitions.canTransition(ArthurianRole.ARTHUR, true, false,
                renown.points(player)), "Round Table renown must unlock Arthur transition");
        RoleSavedData roles = RoleSavedData.get(helper.getLevel());
        roles.set(player, ArthurianRole.ARTHUR);
        helper.assertValueEqual(roles.find(player).orElseThrow(), ArthurianRole.ARTHUR,
                "persisted Arthur role");
        helper.succeed();
    }

    /** Programmatic test instance; serialization is not used for mod-bus registrations. */
    private static final class DirectGameTestInstance extends GameTestInstance {
        private final Consumer<GameTestHelper> body;

        private DirectGameTestInstance(TestData<Holder<TestEnvironmentDefinition<?>>> data,
                                       Consumer<GameTestHelper> body) {
            super(data);
            this.body = body;
        }

        @Override
        public void run(GameTestHelper helper) {
            body.accept(helper);
        }

        @Override
        public MapCodec<? extends GameTestInstance> codec() {
            return FunctionGameTestInstance.CODEC;
        }

        @Override
        protected net.minecraft.network.chat.MutableComponent typeDescription() {
            return Component.literal("mittelalter:direct");
        }
    }
}
