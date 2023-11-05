package it.fulminazzo.teleporteffects.Enums.ConfigOptions;

import it.angrybear.Enums.BearConfigOption;
import it.fulminazzo.teleporteffects.TeleportEffects;

public class Messages extends BearConfigOption {
    public static final Messages EVERY_SECOND = new Messages("every-second");
    public static final Messages TELEPORTING_IN = new Messages("teleporting-in");
    public static final Messages TELEPORT_CANCELLED = new Messages("teleport-cancelled");

    public Messages(String path) {
        super(TeleportEffects.getPlugin(), "teleporting-messages.messages." + path);
    }
}
