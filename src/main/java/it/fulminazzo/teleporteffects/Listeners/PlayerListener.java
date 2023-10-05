package it.fulminazzo.teleporteffects.Listeners;

import it.fulminazzo.teleporteffects.Enums.ConfigOption;
import it.fulminazzo.teleporteffects.Enums.TeleportPermission;
import it.fulminazzo.teleporteffects.Objects.TeleportPlayer;
import it.fulminazzo.teleporteffects.TeleportEffects;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Optional;

public class PlayerListener implements Listener {
    private final TeleportEffects<? extends TeleportPlayer> plugin;

    public PlayerListener(TeleportEffects<? extends TeleportPlayer> plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();
        if (to != null && from.distance(to) > 0.05) getTeleportPlayer(event).ifPresent(TeleportPlayer::stopTask);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.isCancelled()) return;
        Location teleportLocation = event.getTo();
        if (teleportLocation == null) return;
        if (ConfigOption.WHITELISTED_TELEPORT_CAUSES.getTeleportCauses().contains(event.getCause())) return;
        Player player = event.getPlayer();
        if (player.hasPermission(TeleportPermission.BYPASS.getPermission())) return;
        TeleportPlayer teleportPlayer = plugin.getPlayersManager().getPlayer(player);
        if (teleportPlayer == null) return;
        Player teleporter = Bukkit.getOnlinePlayers().stream()
                .filter(p -> p.getWorld().equals(teleportLocation.getWorld()))
                .filter(p -> {
                    Location playerLocation = p.getLocation();
                    return teleportLocation.getBlock().equals(playerLocation.getBlock()) &&
                            teleportLocation.getYaw() == playerLocation.getYaw() &&
                            teleportLocation.getPitch() == playerLocation.getPitch();
                })
                .findAny().orElse(null);
        if (teleporter != null && event.getCause().equals(PlayerTeleportEvent.TeleportCause.COMMAND)
                && teleporter.hasPermission(TeleportPermission.BYPASS.getPermission())) return;
        teleportPlayer.teleportPlayer(teleportLocation);
        event.setCancelled(true);
    }

    public Optional<TeleportPlayer> getTeleportPlayer(PlayerEvent event) {
        return Optional.of(plugin.getPlayersManager().getPlayer(event.getPlayer()));
    }
}