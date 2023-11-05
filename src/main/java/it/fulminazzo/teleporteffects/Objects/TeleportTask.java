package it.fulminazzo.teleporteffects.Objects;

import it.angrybear.Bukkit.Objects.BearPlayer;
import it.angrybear.Objects.Timer.Timer;
import it.fulminazzo.teleporteffects.Enums.ConfigOption;
import it.fulminazzo.teleporteffects.TeleportEffects;
import org.bukkit.entity.Player;

public abstract class TeleportTask {
    protected final TeleportPlayer teleportPlayer;
    protected Timer task;

    protected TeleportTask(TeleportPlayer teleportPlayer) {
        this.teleportPlayer = teleportPlayer;
    }

    abstract void initializeVariables();

    public boolean isOffline() {
        return teleportPlayer == null || teleportPlayer.isOffline();
    }

    public Player getPlayer() {
        return teleportPlayer == null ? null : teleportPlayer.getPlayer();
    }

    public TeleportEffects<? extends TeleportPlayer, ? extends BearPlayer<?>> getPlugin() {
        return TeleportEffects.getPlugin();
    }

    public int getDuration() {
        return ConfigOption.DURATION.getInt();
    }

    public boolean isRunning() {
        return task != null && !task.isStopped();
    }

    public void stop() {
        if (task != null) task.stop();
    }
}
