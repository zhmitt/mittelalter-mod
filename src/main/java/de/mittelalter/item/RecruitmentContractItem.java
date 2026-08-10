package de.mittelalter.item;

import de.mittelalter.Config;
import de.mittelalter.soldier.SoldierEntity;
import de.mittelalter.soldier.SoldierRules;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public final class RecruitmentContractItem extends Item {
    private final EntityTypeSupplier soldierType;

    public RecruitmentContractItem(Properties properties, EntityTypeSupplier soldierType) {
        super(properties);
        this.soldierType = soldierType;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!(context.getLevel() instanceof ServerLevel serverLevel)) return InteractionResult.SUCCESS;
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.FAIL;
        int owned = 0;
        for (Entity entity : serverLevel.getAllEntities()) {
            if (entity instanceof SoldierEntity soldier && player.getUUID().equals(soldier.ownerId()) && soldier.isAlive()) owned++;
        }
        if (!SoldierRules.canRecruit(owned, Config.SOLDIER_CAP.getAsInt())) {
            player.sendSystemMessage(Component.translatable("message.mittelalter.soldier_cap", Config.SOLDIER_CAP.getAsInt()));
            return InteractionResult.FAIL;
        }
        BlockPos spawnPos = context.getClickedPos().relative(context.getClickedFace());
        SoldierEntity soldier = soldierType.get().create(serverLevel, EntitySpawnReason.SPAWN_ITEM_USE);
        if (soldier == null) return InteractionResult.FAIL;
        soldier.snapTo(spawnPos, player.getYRot(), 0.0F);
        soldier.recruit(player);
        if (!serverLevel.addFreshEntity(soldier)) return InteractionResult.FAIL;
        context.getItemInHand().consume(1, player);
        player.sendSystemMessage(Component.translatable("message.mittelalter.recruited", soldier.getDisplayName()));
        return InteractionResult.SUCCESS;
    }

    @FunctionalInterface
    public interface EntityTypeSupplier { EntityType<? extends SoldierEntity> get(); }
}
