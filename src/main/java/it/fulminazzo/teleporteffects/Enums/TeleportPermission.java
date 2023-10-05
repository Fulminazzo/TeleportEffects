package it.fulminazzo.teleporteffects.Enums;

import it.angrybear.Enums.BearPermission;
import it.fulminazzo.teleporteffects.TeleportEffects;

public class TeleportPermission extends BearPermission {
    public static final TeleportPermission BYPASS = new TeleportPermission("bypass");

    public TeleportPermission(String permission) {
        super(permission);
    }

    @Override
    public String getPermission() {
        return getPermission(TeleportEffects.getPlugin());
    }
}
