package de.mittelalter.item;

import de.mittelalter.Config;
import de.mittelalter.camelot.RenownSavedData;
import de.mittelalter.role.ArthurianRole;
import de.mittelalter.role.RoleSavedData;
import de.mittelalter.role.RoleTransitions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public final class RoleTokenItem extends Item {
    private final ArthurianRole target;
    public RoleTokenItem(Properties properties, ArthurianRole target) { super(properties); this.target = target; }

    @Override
    public InteractionResult use(Level level, net.minecraft.world.entity.player.Player player, InteractionHand hand) {
        if (!(level instanceof ServerLevel) || !(player instanceof ServerPlayer serverPlayer)) return InteractionResult.SUCCESS;
        int renown = RenownSavedData.get(serverPlayer.level()).points(serverPlayer.getUUID());
        boolean allowed = Config.ARTHURIAN_ENABLED.get() && RoleTransitions.canTransition(target,
                Config.SPECIAL_ROLES_ENABLED.get(), Config.MYTHIC_ENABLED.get(), renown);
        if (!allowed) {
            serverPlayer.sendSystemMessage(Component.translatable("message.mittelalter.role.denied." + target.name().toLowerCase()));
            return InteractionResult.FAIL;
        }
        RoleSavedData.get(serverPlayer.level()).set(serverPlayer.getUUID(), target);
        if (!serverPlayer.getAbilities().instabuild) serverPlayer.getItemInHand(hand).shrink(1);
        serverPlayer.sendSystemMessage(Component.translatable("message.mittelalter.role.changed",
                Component.translatable("role.mittelalter." + target.name().toLowerCase())));
        return InteractionResult.SUCCESS;
    }
}
