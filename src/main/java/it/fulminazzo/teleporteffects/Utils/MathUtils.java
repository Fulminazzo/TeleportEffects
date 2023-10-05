package it.fulminazzo.teleporteffects.Utils;

import it.angrybear.Bukkit.Utils.ParticlesUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Arrays;

public class MathUtils {

    public static void spawnCircle(Player player, Location center, String particle, int angle, double distance) {
        for (int i = 0; i < 360; i += angle)
            Arrays.stream(getParticlesLocation(center, 1, i, distance)).forEach(location ->
                    ParticlesUtils.spawnParticleNearPlayer(player, location, particle, false));
    }

    public static Location[] getParticlesLocation(Location location, int points, int offset, double distance) {
        final double total = 360;
        Location[] startingLocations = new Location[points];
        for (int i = 0; i < points; i++)
            startingLocations[i] = rotateLocationY(location, total / points * (i + 1) + offset, distance);
        return startingLocations;
    }

    public static Location rotateLocationY(Location startingLocation, double angle, double distance) {
        Vector vector = startingLocation.getDirection();
        vector.setY(0);
        Location location = startingLocation.clone();
        location = location.setDirection(vector);
        double yaw = angle + startingLocation.getYaw();
        location.setYaw((float) yaw);
        Vector offset = location.getDirection().add(location.getDirection().normalize().multiply(distance));
        return location.add(offset);
    }
}