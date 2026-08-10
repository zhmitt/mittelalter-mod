package de.mittelalter.soldier;

import java.util.Optional;
import java.util.UUID;

import de.mittelalter.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

public class SoldierEntity extends PathfinderMob implements RangedAttackMob {
    private final SoldierRole role;
    private @Nullable UUID ownerId;
    private SoldierOrder order = SoldierOrder.FOLLOW;
    private @Nullable BlockPos holdPosition;
    private @Nullable UUID orderedTargetId;

    public SoldierEntity(EntityType<? extends SoldierEntity> type, Level level, SoldierRole role) {
        super(type, level);
        this.role = role;
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.MAINHAND, role == SoldierRole.ARCHER
                ? new ItemStack(Items.BOW) : new ItemStack(ModItems.LONGSWORD.get()));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.28)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.FOLLOW_RANGE, 32.0);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        // Mob registers goals from its super-constructor, before our role field is assigned.
        // The concrete entity subtype is already known at that point and is therefore safe here.
        if (this instanceof Archer) {
            goalSelector.addGoal(2, new RangedBowAttackGoal<>(this, 1.0, 30, 15.0F));
        } else {
            goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.15, true));
        }
        goalSelector.addGoal(5, new SoldierMovementGoal(this));
        goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.8));
        goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        goalSelector.addGoal(9, new RandomLookAroundGoal(this));
    }

    @Override
    public void aiStep() {
        if (!level().isClientSide() && order == SoldierOrder.ATTACK) {
            LivingEntity orderedTarget = resolveOrderedTarget();
            if (orderedTarget == null || !orderedTarget.isAlive()) {
                setOrder(SoldierOrder.FOLLOW, null);
            } else if (getTarget() != orderedTarget) {
                setTarget(orderedTarget);
            }
        } else if (!level().isClientSide() && order != SoldierOrder.ATTACK && getTarget() != null) {
            setTarget(null);
        }
        super.aiStep();
    }

    public void recruit(Player owner) {
        this.ownerId = owner.getUUID();
        setOrder(SoldierOrder.FOLLOW, null);
    }

    public void setOrder(SoldierOrder newOrder, @Nullable LivingEntity target) {
        this.order = newOrder;
        this.orderedTargetId = newOrder == SoldierOrder.ATTACK && target != null ? target.getUUID() : null;
        this.holdPosition = newOrder == SoldierOrder.HOLD ? blockPosition() : null;
        if (newOrder != SoldierOrder.ATTACK) {
            setTarget(null);
        }
    }

    public @Nullable UUID ownerId() { return ownerId; }
    public SoldierOrder order() { return order; }
    public SoldierRole role() { return role; }
    public Optional<BlockPos> holdPosition() { return Optional.ofNullable(holdPosition); }

    public @Nullable Player resolveOwner() {
        return ownerId != null ? level().getPlayerByUUID(ownerId) : null;
    }

    private @Nullable LivingEntity resolveOrderedTarget() {
        if (orderedTargetId == null || !(level() instanceof ServerLevel serverLevel)) return null;
        Entity entity = serverLevel.getEntity(orderedTargetId);
        return entity instanceof LivingEntity living ? living : null;
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        if (ownerId != null) output.putString("Owner", ownerId.toString());
        output.putString("Order", order.name());
        if (holdPosition != null) output.store("HoldPosition", BlockPos.CODEC, holdPosition);
        if (orderedTargetId != null) output.putString("OrderedTarget", orderedTargetId.toString());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        ownerId = input.getString("Owner").flatMap(SoldierEntity::parseUuid).orElse(null);
        order = SoldierOrder.parse(input.getStringOr("Order", SoldierOrder.FOLLOW.name()));
        holdPosition = input.read("HoldPosition", BlockPos.CODEC).orElse(null);
        orderedTargetId = input.getString("OrderedTarget").flatMap(SoldierEntity::parseUuid).orElse(null);
        if (order == SoldierOrder.HOLD && holdPosition == null) holdPosition = blockPosition();
        if (order == SoldierOrder.ATTACK && orderedTargetId == null) order = SoldierOrder.FOLLOW;
    }

    private static Optional<UUID> parseUuid(String value) {
        try { return Optional.of(UUID.fromString(value)); }
        catch (IllegalArgumentException ignored) { return Optional.empty(); }
    }

    @Override
    public void performRangedAttack(LivingEntity target, float power) {
        if (role != SoldierRole.ARCHER || !(level() instanceof ServerLevel serverLevel)) return;
        ItemStack bow = getMainHandItem();
        ItemStack projectileStack = getProjectile(bow);
        AbstractArrow arrow = ProjectileUtil.getMobArrow(this, projectileStack, power, bow);
        double dx = target.getX() - getX();
        double dy = target.getY(0.3333333333333333) - arrow.getY();
        double dz = target.getZ() - getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        Projectile.spawnProjectileUsingShoot(arrow, serverLevel, projectileStack, dx, dy + horizontal * 0.2, dz, 1.6F, 8.0F);
    }

    public static final class Foot extends SoldierEntity {
        public Foot(EntityType<? extends SoldierEntity> type, Level level) { super(type, level, SoldierRole.FOOT); }
    }

    public static final class Archer extends SoldierEntity {
        public Archer(EntityType<? extends SoldierEntity> type, Level level) { super(type, level, SoldierRole.ARCHER); }
    }
}
