package it.fulminazzo.teleporteffects.Objects;

import it.angrybear.Annotations.PreventSaving;
import it.angrybear.Bukkit.BearPlugin;
import it.angrybear.Bukkit.Objects.BearPlayer;
import it.angrybear.Objects.ReflObject;
import it.angrybear.Objects.Wrappers.PlayerWrapper;
import it.fulminazzo.teleporteffects.Enums.ConfigOption;
import it.fulminazzo.teleporteffects.Enums.Message;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

import java.io.File;

public class TeleportPlayer extends BearPlayer {
    @PreventSaving
    private FromTask fromTask;
    @PreventSaving
    private ToTask toTask;
    @PreventSaving
    private Location lastLocation;
    private boolean showEffects;

    public TeleportPlayer(BearPlugin<?, ?> plugin, File playersFolder, OfflinePlayer player) throws Exception {
        super(plugin, playersFolder, player);
    }

    @Override
    protected void createNew(PlayerWrapper playerWrapper) {
        fromTask = new FromTask(this);
        toTask = new ToTask(this);
        showEffects = true;
    }

    public void teleportPlayer(Location destinationLocation) {
        if (isOffline()) return;
        Player player = getPlayer();
        if (isTeleporting()) {
            player.sendMessage(Message.ALREADY_TELEPORTING.getMessage(true));
            return;
        }
        stopTask();
        player.sendMessage(Message.TELEPORTING_IN.getMessage(true)
                .replace("%time%", String.valueOf(ConfigOption.DURATION.getInt())));
        fromTask.stage1(destinationLocation);
    }

    public FromTask getFromTask() {
        return fromTask;
    }

    public ToTask getToTask() {
        return toTask;
    }

    public boolean isTeleporting() {
        return (fromTask != null && fromTask.isRunning()) || (toTask != null && toTask.isRunning()) && !isOffline();
    }

    public boolean hasDisabledEffects() {
        return !showEffects;
    }

    public void enableEffects() {
        showEffects = true;
    }

    public void disableEffects() {
        showEffects = false;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public void stopTask() {
        if (isTeleporting()) {
            Player player = getPlayer();
            player.sendMessage(Message.TELEPORT_CANCELLED.getMessage(true));
            String effect = ConfigOption.TELEPORT_EFFECT.getString();
            setEssentialsLastLocation();
            if (effect == null || effect.trim().isEmpty()) return;
            if (isOffline()) return;
            ReflObject<?> effectReflObject = new ReflObject<>(PotionEffectType.class.getCanonicalName(), false);
            effectReflObject.setShowErrors(false);
            PotionEffectType effectType = effectReflObject.getFieldObject(effect);
            if (effectType != null) player.removePotionEffect(effectType);
        }
        if (fromTask != null) fromTask.stop();
        if (toTask != null) toTask.stop();
    }

    public void setLastLocation(Location location) {
        this.lastLocation = location;
    }

    public void setEssentialsLastLocation() {
        if (lastLocation == null) return;
        Plugin essentials = Bukkit.getPluginManager().getPlugin("Essentials");
        if (essentials == null || !essentials.isEnabled()) return;
        new ReflObject<>(essentials).callMethod("getUser", uuid).callMethod("setLastLocation", lastLocation);
    }
}