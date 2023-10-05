package it.fulminazzo.teleporteffects.Objects;

import it.angrybear.Objects.Timer.Timer;
import it.fulminazzo.teleporteffects.Enums.ConfigOption;
import it.fulminazzo.teleporteffects.Utils.MathUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class ToTask extends TeleportTask {
    private double height;
    private int angle;
    private double distance;

    public ToTask(TeleportPlayer teleportPlayer) {
        super(teleportPlayer);
    }

    @Override
    public void initializeVariables() {
        height = ConfigOption.TO_HEIGHT_DECREMENT.getDouble();
        angle = ConfigOption.TO_DISTANCE_ANGLE.getInt();
        distance = ConfigOption.TO_FINAL_DISTANCE.getDouble();
    }

    public void stage3(Location destinationLocation, int duration) {
        if (isOffline()) return;
        initializeVariables();
        double max = 2.0 + 0.5;
        double distance = ConfigOption.TO_FINAL_DISTANCE.getDouble();
        double deltaDistance = ConfigOption.TO_INITIAL_DISTANCE.getDouble() - ConfigOption.TO_FINAL_DISTANCE.getDouble();

        task = new Timer(() -> {
            stop();
            if (isOffline()) return;
            getPlayer().teleport(destinationLocation, PlayerTeleportEvent.TeleportCause.UNKNOWN);
            stage4();
        });
        task.setInterval(0);
        task.setSecondIntermediateAction(timer -> {
            if (isOffline()) return;
            Player player = getPlayer();
            if (player.isSneaking()) return;
            timer = timer * (max / task.getDuration());

            boolean addAngle = true;
            String particle = ConfigOption.TO_PARTICLE_TYPE_STAGE1.getString();
            if (particle != null && !particle.trim().isEmpty())
                for (double i = 0; i < timer; i += height) {
                    addAngle = !addAngle;
                    MathUtils.spawnCircle(player, destinationLocation.clone().add(0, max - i, 0),
                            particle, angle + (addAngle ? angle / 2 : 0),
                            deltaDistance - Math.sqrt(i) + distance);
                }
        });
        task.start(getPlugin(), duration, false);
    }

    public void stage4() {
        if (isOffline()) return;
        initializeVariables();
        double duration = ConfigOption.STAGE4_DURATION.getDouble();
        double deltaDistance = ConfigOption.TO_INITIAL_DISTANCE.getDouble() - ConfigOption.TO_FINAL_DISTANCE.getDouble() /
                (duration - task.getInterval());
        Location destinationLocation = getPlayer().getLocation().add(0, 0.1, 0);

        task = new Timer(this::stop);
        task.setInterval(0);
        task.setSecondIntermediateAction(timer -> {
            if (isOffline()) return;
            Player player = getPlayer();
            if (player.isSneaking()) return;
            String particle = ConfigOption.TO_PARTICLE_TYPE_STAGE2.getString();
            if (particle == null || particle.trim().isEmpty()) return;
            MathUtils.spawnCircle(player, destinationLocation, particle, angle, distance += deltaDistance);
        });
        task.start(getPlugin(), duration, false);
    }
}