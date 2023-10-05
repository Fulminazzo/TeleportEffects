package it.fulminazzo.teleporteffects.Objects;

import it.angrybear.Bukkit.Utils.ParticlesUtils;
import it.angrybear.Objects.ReflObject;
import it.angrybear.Objects.Timer.Timer;
import it.fulminazzo.teleporteffects.Enums.ConfigOption;
import it.fulminazzo.teleporteffects.Utils.MathUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FromTask extends TeleportTask {
    private double height;
    private int angle;
    private double distance;
    private int points;
    private int initialAngleOffset;
    private Location destinationLocation;

    public FromTask(TeleportPlayer teleportPlayer) {
        super(teleportPlayer);
    }

    @Override
    public void initializeVariables() {
        height = 0;
        incrementHeight();
        angle = 0;
        distance = ConfigOption.FROM_INITIAL_DISTANCE.getDouble();
        points = ConfigOption.FROM_POINTS.getInt();
        initialAngleOffset = ConfigOption.FROM_INITIAL_OFFSET.getInt();
    }

    public void stage1(Location destinationLocation) {
        if (isOffline()) return;
        this.destinationLocation = destinationLocation;
        initializeVariables();
        List<Location> locations = new ArrayList<>();
        Location spawnLocation = getPlayer().getLocation();

        task = new Timer(() -> {
            stop();
            stage2(spawnLocation, locations.subList(locations.size() - points, locations.size()));
        });
        task.setInterval(0);
        task.setSecondIntermediateAction(timer -> {
            if (isOffline()) return;
            if (ConfigOption.FROM_CLEAR_FIRST_STAGE_LOCATIONS.getBoolean()) locations.clear();
            Location[] particlesLocation = MathUtils.getParticlesLocation(spawnLocation, points,
                    angle + initialAngleOffset, distance);
            locations.addAll(Arrays.asList(particlesLocation));
            String particle = ConfigOption.FROM_PARTICLE_TYPE_STAGE1.getString();
            if (particle != null && !particle.trim().isEmpty())
                locations.forEach(location -> ParticlesUtils.spawnParticleNearPlayer(getPlayer(), location, particle, false));
            spawnLocation.setY(spawnLocation.getY() + height * 2);
            incrementAngle();
            decrementDistance();
        });
        task.start(getPlugin(), 1, false);
    }

    public void stage2(Location startLocation, List<Location> startLocations) {
        if (isOffline()) return;
        int duration = getDuration() - 1;
        Location spawnLocation = startLocation.clone();
        Location destinationLocation = getPlayer().getLocation().add(1, 0, 0);

        task = new Timer(this::stop);
        task.setInterval(0);
        task.setSecondIntermediateAction(timer -> {
            if (isOffline()) return;
            String particle = ConfigOption.FROM_PARTICLE_TYPE_STAGE2.getString();
            if (particle != null && !particle.trim().isEmpty())
                startLocations.forEach(location -> ParticlesUtils.spawnParticleNearPlayer(getPlayer(), location, particle, false));

            if (ConfigOption.FROM_CLEAR_SECOND_STAGE_LOCATIONS.getBoolean()) startLocations.clear();

            Location[] particlesLocation = MathUtils.getParticlesLocation(spawnLocation, points,
                    angle + initialAngleOffset, distance);
            Arrays.stream(particlesLocation)
                    .map(l -> new Location[]{l, l.clone()})
                    .peek(locations -> {
                        locations[0].setY(spawnLocation.getY() + height);
                        locations[1].setY(spawnLocation.getY() - height);
                    })
                    .flatMap(Arrays::stream)
                    .forEach(startLocations::add);
            destinationLocation.setY(destinationLocation.getY() + height / 2);
            incrementHeight();
            incrementAngle();
        });
        task.start(getPlugin(), duration, false);
        teleportPlayer.getToTask().stage3(this.destinationLocation, duration);

        String effect = ConfigOption.TELEPORT_EFFECT.getString();
        if (effect == null || effect.trim().isEmpty()) return;
        if (isOffline()) return;
        Player player = getPlayer();
        ReflObject<?> effectReflObject = new ReflObject<>(PotionEffectType.class.getCanonicalName(), false);
        effectReflObject.setShowErrors(false);
        PotionEffectType effectType = effectReflObject.getFieldObject(effect);
        if (!teleportPlayer.hasDisabledEffects() && effectType != null) {
            ReflObject<Player> playerObject = new ReflObject<>(player);
            String methodName = "addPotionEffect";
            PotionEffect potionEffect = new PotionEffect(effectType, (duration + 1) * 20, 255, true, false);
            if (playerObject.getMethod(methodName, PotionEffect.class, boolean.class) != null)
                playerObject.callMethod(methodName, potionEffect, true);
            else playerObject.callMethod(methodName, potionEffect);
        }
    }

    private void incrementHeight() {
        height += ConfigOption.FROM_HEIGHT_INCREMENT.getDouble();
    }

    private void incrementAngle() {
        angle += ConfigOption.FROM_ROTATION_ANGLE.getInt();
    }

    private void decrementDistance() {
        double initialDistance = ConfigOption.FROM_INITIAL_DISTANCE.getDouble();
        double finalDistance = ConfigOption.FROM_FINAL_DISTANCE.getDouble();
        distance -= ((initialDistance - finalDistance) / (task.getDuration() / task.getInterval()));
    }
}
