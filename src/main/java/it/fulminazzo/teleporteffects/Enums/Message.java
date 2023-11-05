package it.fulminazzo.teleporteffects.Enums;

import it.angrybear.Utils.StringUtils;
import it.fulminazzo.teleporteffects.TeleportEffects;

public enum Message {
    PREFIX("prefix"),

    CONSOLE_CANNOT_EXECUTE("console-cannot-execute"),
    TELEPORT_EFFECTS_CHANGED("teleport-effects-changed"),
    ENABlED("enabled"),
    DISABLED("disabled"),

    ALREADY_TELEPORTING("already-teleporting");

    private final String path;

    Message(String path) {
        this.path = path;
    }

    public String getMessage(boolean prefix) {
        String message = StringUtils.parseMessage(TeleportEffects.getPlugin().getLang().getString(path));
        if (prefix) message = PREFIX.getMessage(false) + message;
        return message;
    }
}