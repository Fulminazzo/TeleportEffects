package it.fulminazzo.teleporteffects.Enums.ConfigOptions;

import it.angrybear.Enums.BearConfigOption;
import it.fulminazzo.teleporteffects.TeleportEffects;

public class Boss_Bar extends BearConfigOption {
    public static final Boss_Bar TELEPORTING_IN_TITLE = new Boss_Bar("teleporting-in.title");
    public static final Boss_Bar TELEPORTING_IN_BAR_COLOR = new Boss_Bar("teleporting-in.bar-color");
    public static final Boss_Bar TELEPORTING_IN_BAR_STYLE = new Boss_Bar("teleporting-in.bar-style");
    public static final Boss_Bar TELEPORTING_IN_BAR_FLAGS = new Boss_Bar("teleporting-in.bar-flags");

    public static final Boss_Bar TELEPORT_CANCELLED_TITLE = new Boss_Bar("teleport-cancelled.title");
    public static final Boss_Bar TELEPORT_CANCELLED_BAR_COLOR = new Boss_Bar("teleport-cancelled.bar-color");
    public static final Boss_Bar TELEPORT_CANCELLED_BAR_STYLE = new Boss_Bar("teleport-cancelled.bar-style");
    public static final Boss_Bar TELEPORT_CANCELLED_BAR_FLAGS = new Boss_Bar("teleport-cancelled.bar-flags");
    public static final Boss_Bar TELEPORT_CANCELLED_DURATION = new Boss_Bar("teleport-cancelled.duration");

    public Boss_Bar(String path) {
        super(TeleportEffects.getPlugin(), "teleporting-messages.boss-bar." + path);
    }
}
