package it.fulminazzo.teleporteffects.Enums;

import it.angrybear.Enums.BearConfigOption;
import it.fulminazzo.teleporteffects.TeleportEffects;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ConfigOption extends BearConfigOption {
    public static final ConfigOption DURATION = new ConfigOption("duration");
    public static final ConfigOption TELEPORT_EFFECT = new ConfigOption("teleport-effect");
    public static final ConfigOption WHITELISTED_TELEPORT_CAUSES = new ConfigOption("whitelisted-teleport-causes");

    public static final ConfigOption FROM_PARTICLE_TYPE_STAGE1 = new ConfigOption("from.particle-type-stage1");
    public static final ConfigOption FROM_PARTICLE_TYPE_STAGE2 = new ConfigOption("from.particle-type-stage2");
    public static final ConfigOption FROM_POINTS = new ConfigOption("from.points");
    public static final ConfigOption FROM_INITIAL_OFFSET = new ConfigOption("from.initial-offset");
    public static final ConfigOption FROM_ROTATION_ANGLE = new ConfigOption("from.rotation-angle");
    public static final ConfigOption FROM_HEIGHT_INCREMENT = new ConfigOption("from.height-increment");
    public static final ConfigOption FROM_INITIAL_DISTANCE = new ConfigOption("from.initial-distance");
    public static final ConfigOption FROM_FINAL_DISTANCE = new ConfigOption("from.final-distance");
    public static final ConfigOption FROM_CLEAR_FIRST_STAGE_LOCATIONS = new ConfigOption("from.clear-first-stage-locations");
    public static final ConfigOption FROM_CLEAR_SECOND_STAGE_LOCATIONS = new ConfigOption("from.clear-second-stage-locations");

    public static final ConfigOption TO_PARTICLE_TYPE_STAGE1 = new ConfigOption("to.particle-type-stage1");
    public static final ConfigOption TO_PARTICLE_TYPE_STAGE2 = new ConfigOption("to.particle-type-stage2");
    public static final ConfigOption TO_DISTANCE_ANGLE = new ConfigOption("to.distance-angle");
    public static final ConfigOption TO_HEIGHT_DECREMENT = new ConfigOption("to.height-decrement");
    public static final ConfigOption TO_INITIAL_DISTANCE = new ConfigOption("to.initial-distance");
    public static final ConfigOption TO_FINAL_DISTANCE = new ConfigOption("to.final-distance");
    public static final ConfigOption STAGE4_DURATION = new ConfigOption("to.stage4-duration");

    public ConfigOption(String permission) {
        super(TeleportEffects.getPlugin(), permission);
    }

    public List<PlayerTeleportEvent.TeleportCause> getTeleportCauses() {
        List<String> list = getStringList();
        if (list == null) return null;
        else return list.stream()
                .map(t -> Arrays.stream(PlayerTeleportEvent.TeleportCause.values()).filter(e ->
                        e.name().equalsIgnoreCase(t)).findAny().orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
