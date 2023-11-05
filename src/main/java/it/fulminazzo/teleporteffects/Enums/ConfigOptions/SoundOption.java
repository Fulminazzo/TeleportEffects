package it.fulminazzo.teleporteffects.Enums.ConfigOptions;

import it.angrybear.Enums.BearConfigOption;
import it.fulminazzo.teleporteffects.Objects.SoundWrapper;
import it.fulminazzo.teleporteffects.TeleportEffects;

public class SoundOption extends BearConfigOption {
    public static final SoundOption TELEPORT_START = new SoundOption("teleport-start");
    public static final SoundOption TELEPORTING = new SoundOption("teleporting");
    public static final SoundOption TELEPORT_FINISH = new SoundOption("teleport-finish");
    public static final SoundOption TELEPORT_CANCELLED = new SoundOption("teleport-cancelled");

    public SoundOption(String path) {
        super(TeleportEffects.getPlugin(), "teleporting-messages.sound." + path);
    }

    public SoundWrapper getSound() {
        return new SoundWrapper(this);
    }
}
