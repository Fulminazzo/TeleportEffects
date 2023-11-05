package it.fulminazzo.teleporteffects;

import it.angrybear.Bukkit.Objects.BearPlayer;
import it.fulminazzo.teleporteffects.Managers.TeleportPlayersManager;
import it.fulminazzo.teleporteffects.Objects.TeleportPlayer;

public class TeleportEffectsPlugin extends TeleportEffects<TeleportPlayer, BearPlayer<?>> {
    public TeleportEffectsPlugin() {
        super(TeleportPlayersManager.class, TeleportPlayer.class);
    }
}
