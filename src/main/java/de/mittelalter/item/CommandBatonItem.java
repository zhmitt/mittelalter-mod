package de.mittelalter.item;

import java.util.List;

import de.mittelalter.Config;
import de.mittelalter.soldier.SoldierEntity;
import de.mittelalter.soldier.SoldierOrder;
import de.mittelalter.soldier.SoldierRules;
import de.mittelalter.role.ArthurianRole;
import de.mittelalter.role.RoleBenefits;
import de.mittelalter.role.RoleSavedData;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public final class CommandBatonItem extends Item {
    public CommandBatonItem(Properties properties) { super(properties); }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!(level instanceof ServerLevel serverLevel)) return InteractionResult.SUCCESS;
        List<SoldierEntity> soldiers = eligible(serverLevel, player);
        SoldierOrder order = soldiers.stream().anyMatch(s -> s.order() == SoldierOrder.FOLLOW)
                ? SoldierOrder.HOLD : SoldierOrder.FOLLOW;
        soldiers.forEach(s -> s.setOrder(order, null));
        player.sendSystemMessage(Component.translatable("message.mittelalter.command", order.name().toLowerCase(), soldiers.size()));
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (!(player.level() instanceof ServerLevel serverLevel)) return InteractionResult.SUCCESS;
        if (!(target instanceof Enemy) || target == player) return InteractionResult.FAIL;
        List<SoldierEntity> soldiers = eligible(serverLevel, player);
        soldiers.forEach(s -> s.setOrder(SoldierOrder.ATTACK, target));
        player.sendSystemMessage(Component.translatable("message.mittelalter.attack", target.getDisplayName(), soldiers.size()));
        return InteractionResult.SUCCESS;
    }

    private static List<SoldierEntity> eligible(ServerLevel level, Player player) {
        ArthurianRole role = Config.ARTHURIAN_ENABLED.get()
                ? RoleSavedData.get(level).find(player.getUUID()).orElse(ArthurianRole.YOUNG_KNIGHT)
                : ArthurianRole.YOUNG_KNIGHT;
        double radius = RoleBenefits.effectiveCommandRadius(Config.COMMAND_RADIUS.getAsInt(), role);
        AABB area = player.getBoundingBox().inflate(radius);
        return level.getEntitiesOfClass(SoldierEntity.class, area, soldier -> SoldierRules.isCommandEligible(
                player.getUUID(), soldier.ownerId(), player.distanceToSqr(soldier), radius));
    }
}
