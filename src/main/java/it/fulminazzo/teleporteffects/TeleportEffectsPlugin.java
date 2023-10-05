package it.fulminazzo.teleporteffects;

import it.fulminazzo.teleporteffects.Managers.TeleportPlayersManager;
import it.fulminazzo.teleporteffects.Objects.TeleportPlayer;

public class TeleportEffectsPlugin extends TeleportEffects<TeleportPlayer> {
    public TeleportEffectsPlugin() {
        super(TeleportPlayersManager.class, TeleportPlayer.class);
    }
}
