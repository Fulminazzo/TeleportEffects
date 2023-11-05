package it.fulminazzo.teleporteffects.Enums.ConfigOptions;

import it.angrybear.Enums.BearConfigOption;
import it.fulminazzo.teleporteffects.TeleportEffects;

public class Titles extends BearConfigOption {
    public static final Titles EVERY_SECOND = new Titles("every-second");
    public static final Titles TELEPORTING_IN_TITLE = new Titles("teleporting-in.title");
    public static final Titles TELEPORTING_IN_SUBTITLE = new Titles("teleporting-in.subtitle");
    public static final Titles TELEPORT_CANCELLED_TITLE = new Titles("teleport-cancelled.title");
    public static final Titles TELEPORT_CANCELLED_SUBTITLE = new Titles("teleport-cancelled.subtitle");

    public Titles(String path) {
        super(TeleportEffects.getPlugin(), "teleporting-messages.titles." + path);
    }
}
