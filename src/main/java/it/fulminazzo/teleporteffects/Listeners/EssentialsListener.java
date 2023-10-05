package it.fulminazzo.teleporteffects.Listeners;

import it.fulminazzo.teleporteffects.Objects.TeleportPlayer;
import it.fulminazzo.teleporteffects.TeleportEffects;
import net.ess3.api.events.teleport.PreTeleportEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EssentialsListener implements Listener {
    private final TeleportEffects<? extends TeleportPlayer> plugin;

    public EssentialsListener(TeleportEffects<? extends TeleportPlayer> plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerTeleport(PreTeleportEvent event) {
        Player player = event.getTeleportee().getBase();
        if (player == null) return;
        TeleportPlayer teleportPlayer = plugin.getPlayersManager().getPlayer(player);
        if (teleportPlayer == null) return;
        teleportPlayer.setLastLocation(event.getTeleportee().getLastLocation());
    }
}
