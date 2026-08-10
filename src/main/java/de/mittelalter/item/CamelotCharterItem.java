package de.mittelalter.item;

import de.mittelalter.Config;
import de.mittelalter.camelot.CamelotPlan;
import de.mittelalter.camelot.CamelotSavedData;
import de.mittelalter.registry.ModBlocks;
import de.mittelalter.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;

public final class CamelotCharterItem extends Item {
    public CamelotCharterItem(Properties properties) { super(properties); }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!(context.getLevel() instanceof ServerLevel level)) return InteractionResult.SUCCESS;
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.FAIL;
        if (!Config.ARTHURIAN_ENABLED.get()) {
            player.sendSystemMessage(Component.translatable("message.mittelalter.camelot.disabled"));
            return InteractionResult.FAIL;
        }
        CamelotSavedData state = CamelotSavedData.get(level);
        if (state.location().isPresent()) {
            BlockPos existing = state.location().orElseThrow();
            player.sendSystemMessage(Component.translatable("message.mittelalter.camelot.exists", existing.getX(), existing.getY(), existing.getZ()));
            return InteractionResult.FAIL;
        }
        BlockPos center = context.getClickedPos().relative(context.getClickedFace());
        CamelotPlan.Area area = new CamelotPlan.Area() {
            @Override public boolean hasSolidFloor(int x, int z) {
                BlockPos floor = center.offset(x, -1, z);
                return level.getBlockState(floor).isFaceSturdy(level, floor, Direction.UP);
            }
            @Override public boolean isClear(int x, int y, int z) {
                return level.getBlockState(center.offset(x, y, z)).canBeReplaced();
            }
        };
        if (!CamelotPlan.isValidSite(area)) {
            player.sendSystemMessage(Component.translatable("message.mittelalter.camelot.invalid_ground"));
            return InteractionResult.FAIL;
        }
        if (!state.claim(center)) return InteractionResult.FAIL;
        for (CamelotPlan.Placement placement : CamelotPlan.placements()) {
            var block = switch (placement.element()) {
                case FLOOR -> Blocks.POLISHED_ANDESITE;
                case WALL -> Blocks.STONE_BRICKS;
                case GATE -> Blocks.OAK_FENCE_GATE;
                case TABLE -> Blocks.DARK_OAK_SLAB;
                case BANNER -> ModBlocks.TOURNAMENT_STANDARD.get();
            };
            level.setBlockAndUpdate(center.offset(placement.x(), placement.y(), placement.z()), block.defaultBlockState());
        }
        spawn(level, ModEntities.SIR_BEDIVERE.get().create(level, EntitySpawnReason.STRUCTURE), center.offset(-3, 1, 2));
        spawn(level, ModEntities.SIR_GAWAIN.get().create(level, EntitySpawnReason.STRUCTURE), center.offset(3, 1, 2));
        spawn(level, ModEntities.SIR_LANCELOT.get().create(level, EntitySpawnReason.STRUCTURE), center.offset(0, 1, 4));
        context.getItemInHand().consume(1, player);
        player.sendSystemMessage(Component.translatable("message.mittelalter.camelot.built", center.getX(), center.getY(), center.getZ()));
        return InteractionResult.SUCCESS;
    }

    private static void spawn(ServerLevel level, Mob mob, BlockPos pos) {
        if (mob == null) return;
        mob.snapTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 180.0F, 0.0F);
        level.addFreshEntity(mob);
    }
}
