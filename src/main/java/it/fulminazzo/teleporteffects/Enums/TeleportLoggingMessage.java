package it.fulminazzo.teleporteffects.Enums;

import it.angrybear.Enums.BearLoggingMessage;

public class TeleportLoggingMessage extends BearLoggingMessage {
    public static final TeleportLoggingMessage SOUND_NOT_FOUND = new TeleportLoggingMessage("Sound %sound% not found!");

    public TeleportLoggingMessage(String message) {
        super(message);
    }
}
