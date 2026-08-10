package de.mittelalter.tournament;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import de.mittelalter.Config;
import de.mittelalter.registry.ModBlocks;
import de.mittelalter.registry.ModItems;
import de.mittelalter.camelot.RenownSavedData;
import de.mittelalter.camelot.RenownLedger;
import de.mittelalter.role.ArthurianRole;
import de.mittelalter.role.RoleBenefits;
import de.mittelalter.role.RoleSavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

public final class TournamentManager {
    private final Map<UUID, TournamentMode> selections = new HashMap<>();
    private final Map<UUID, TournamentSession> sessions = new HashMap<>();
    private final Map<UUID, Long> cooldowns = new HashMap<>();

    @SubscribeEvent
    public void onStandardUsed(PlayerInteractEvent.RightClickBlock event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (!player.level().getBlockState(event.getPos()).is(ModBlocks.TOURNAMENT_STANDARD.get())) return;
        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);
        if (!Config.ARTHURIAN_ENABLED.get()) {
            player.sendSystemMessage(Component.translatable("message.mittelalter.tournament.disabled"));
            return;
        }
        UUID id = player.getUUID();
        TournamentMode selected = selections.getOrDefault(id, TournamentMode.MELEE);
        if (!player.isShiftKeyDown()) {
            selected = selected.next();
            selections.put(id, selected);
            player.sendSystemMessage(Component.translatable("message.mittelalter.tournament.selected",
                    Component.translatable("message.mittelalter.tournament.mode." + selected.translationSuffix())));
            return;
        }
        long now = player.level().getGameTime();
        if (sessions.containsKey(id)) {
            player.sendSystemMessage(Component.translatable("message.mittelalter.tournament.already_active"));
            return;
        }
        long readyAt = cooldowns.getOrDefault(id, 0L);
        if (now < readyAt) {
            player.sendSystemMessage(Component.translatable("message.mittelalter.tournament.cooldown", (readyAt - now + 19) / 20));
            return;
        }
        BlockPos anchor = event.getPos();
        sessions.put(id, new TournamentSession(selected, now, anchor.getX(), anchor.getY(), anchor.getZ()));
        player.sendSystemMessage(Component.translatable("message.mittelalter.tournament.started",
                Component.translatable("message.mittelalter.tournament.mode." + selected.translationSuffix()), TournamentSession.REQUIRED_KILLS));
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        Entity attacker = event.getSource().getEntity();
        if (!(attacker instanceof ServerPlayer player)) return;
        TournamentSession session = sessions.get(player.getUUID());
        if (session == null) return;
        TournamentMode attackMode = event.getSource().getDirectEntity() instanceof Projectile
                ? TournamentMode.ARCHERY : TournamentMode.MELEE;
        Entity victim = event.getEntity();
        TournamentSession.Outcome outcome = session.recordKill(attackMode, victim instanceof Enemy,
                victim.getX(), victim.getY(), victim.getZ(), player.level().getGameTime());
        if (outcome == TournamentSession.Outcome.PROGRESSED) {
            player.sendSystemMessage(Component.translatable("message.mittelalter.tournament.progress",
                    session.kills(), TournamentSession.REQUIRED_KILLS));
        } else if (outcome == TournamentSession.Outcome.COMPLETED || outcome == TournamentSession.Outcome.CHAMPION) {
            finish(player, session, outcome == TournamentSession.Outcome.CHAMPION);
        }
    }

    private void finish(ServerPlayer player, TournamentSession session, boolean champion) {
        sessions.remove(player.getUUID());
        cooldowns.put(player.getUUID(), player.level().getGameTime() + TournamentSession.COOLDOWN_TICKS);
        giveOrDrop(player, new ItemStack(ModItems.TOURNAMENT_TOKEN.get(), 3));
        if (champion) giveOrDrop(player, new ItemStack(ModItems.CHAMPION_LAUREL.get()));
        player.sendSystemMessage(Component.translatable(champion
                ? "message.mittelalter.tournament.champion" : "message.mittelalter.tournament.completed"));
        if (Config.ARTHURIAN_ENABLED.get()) {
            ArthurianRole role = RoleSavedData.get(player.level()).find(player.getUUID())
                    .orElse(ArthurianRole.YOUNG_KNIGHT);
            int award = RenownLedger.tournamentAward(champion) + RoleBenefits.tournamentRenownBonus(role);
            int total = RenownSavedData.get(player.level()).award(player.getUUID(), award);
            player.sendSystemMessage(Component.translatable("message.mittelalter.renown.awarded", award, total));
        }
    }


    private static void giveOrDrop(ServerPlayer player, ItemStack stack) {
        if (!player.getInventory().add(stack)) player.drop(stack, false);
    }

    @SubscribeEvent
    public void onServerTick(ServerTickEvent.Post event) {
        long now = event.getServer().overworld().getGameTime();
        Iterator<Map.Entry<UUID, TournamentSession>> iterator = sessions.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UUID, TournamentSession> entry = iterator.next();
            if (!entry.getValue().isExpired(now)) continue;
            ServerPlayer player = event.getServer().getPlayerList().getPlayer(entry.getKey());
            if (player != null) player.sendSystemMessage(Component.translatable("message.mittelalter.tournament.failed"));
            iterator.remove();
        }
        cooldowns.entrySet().removeIf(entry -> entry.getValue() <= now);
    }

    @SubscribeEvent public void onLogout(PlayerEvent.PlayerLoggedOutEvent event) { sessions.remove(event.getEntity().getUUID()); }
    @SubscribeEvent public void onServerStopped(ServerStoppedEvent event) { sessions.clear(); cooldowns.clear(); selections.clear(); }
}
