package it.fulminazzo.teleporteffects.Utils;

import it.fulminazzo.teleporteffects.Objects.TeleportPlayer;
import it.fulminazzo.teleporteffects.TeleportEffects;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ParticlesUtils {

    public static void spawnParticleNearPlayer(Player player, Location location, String particleName, boolean showErrors) {
        player.getWorld().getPlayers().stream().filter(p -> {
            if (p.equals(player)) return true;
            else return player.getLocation().distance(p.getLocation()) <= Bukkit.getServer().getViewDistance() * 16;
        }).filter(p -> p.canSee(player))
                .filter(p -> {
                    TeleportPlayer teleportPlayer = TeleportEffects.getPlugin().getPlayersManager().getPlayer(p);
                    return teleportPlayer == null || !teleportPlayer.hasDisabledEffects();
                })
                .forEach(p -> it.angrybear.Bukkit.Utils.ParticlesUtils.spawnParticle(p, location, particleName, showErrors));
    }
}
