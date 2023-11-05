package it.fulminazzo.teleporteffects.Enums.ConfigOptions;

import it.angrybear.Enums.BearConfigOption;
import it.fulminazzo.teleporteffects.TeleportEffects;

public class Action_Bar extends BearConfigOption {
    public static final Action_Bar LENGTH = new Action_Bar("length");
    public static final Action_Bar REVERSE = new Action_Bar("reverse");
    public static final Action_Bar PROGRESS_ON = new Action_Bar("progress-on");
    public static final Action_Bar PROGRESS_OFF = new Action_Bar("progress-off");
    public static final Action_Bar PROGRESS_CANCELLED = new Action_Bar("progress-cancelled");

    public Action_Bar(String path) {
        super(TeleportEffects.getPlugin(), "teleporting-messages.action-bar." + path);
    }
}
