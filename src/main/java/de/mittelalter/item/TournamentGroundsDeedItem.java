package de.mittelalter.item;

import de.mittelalter.Config;
import de.mittelalter.registry.ModBlocks;
import de.mittelalter.tournament.VenuePlan;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;

public final class TournamentGroundsDeedItem extends Item {
    public TournamentGroundsDeedItem(Properties properties) { super(properties); }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!(context.getLevel() instanceof ServerLevel level)) return InteractionResult.SUCCESS;
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.FAIL;
        if (!Config.ARTHURIAN_ENABLED.get()) {
            player.sendSystemMessage(Component.translatable("message.mittelalter.tournament.disabled"));
            return InteractionResult.FAIL;
        }
        BlockPos center = context.getClickedPos().relative(context.getClickedFace());
        VenuePlan.Area area = new VenuePlan.Area() {
            @Override public boolean hasSolidFloor(int x, int z) {
                BlockPos floor = center.offset(x, -1, z);
                return level.getBlockState(floor).isFaceSturdy(level, floor, Direction.UP);
            }
            @Override public boolean isClear(int x, int z, int y) {
                return level.getBlockState(center.offset(x, y, z)).canBeReplaced();
            }
        };
        if (!VenuePlan.isFlatAndClear(area)) {
            player.sendSystemMessage(Component.translatable("message.mittelalter.tournament.invalid_ground"));
            return InteractionResult.FAIL;
        }
        for (VenuePlan.Placement placement : VenuePlan.placements()) {
            BlockPos pos = center.offset(placement.x(), 0, placement.z());
            level.setBlockAndUpdate(pos, switch (placement.element()) {
                case STANDARD -> ModBlocks.TOURNAMENT_STANDARD.get().defaultBlockState();
                case FENCE -> Blocks.OAK_FENCE.defaultBlockState();
                case GATE -> Blocks.OAK_FENCE_GATE.defaultBlockState();
                case TARGET -> Blocks.TARGET.defaultBlockState();
                case SPECTATOR_MARKER -> Blocks.OAK_SLAB.defaultBlockState();
            });
        }
        context.getItemInHand().consume(1, player);
        player.sendSystemMessage(Component.translatable("message.mittelalter.tournament.venue_built"));
        return InteractionResult.SUCCESS;
    }
}
