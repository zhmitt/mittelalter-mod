package de.mittelalter.role;

import de.mittelalter.Config;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public final class RoleEvents {
    @SubscribeEvent
    public void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!Config.ARTHURIAN_ENABLED.get() || !(event.getEntity() instanceof ServerPlayer player)) return;
        RoleSavedData roles = RoleSavedData.get(player.level());
        if (roles.find(player.getUUID()).isEmpty()) {
            roles.getOrAssignDefault(player.getUUID());
            player.sendSystemMessage(Component.translatable("message.mittelalter.role.assigned.young_knight"));
        }
    }
}
