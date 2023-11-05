package it.fulminazzo.teleporteffects.Objects;

import it.angrybear.Annotations.PreventSaving;
import it.angrybear.Bukkit.Objects.BearPlayer;
import it.angrybear.Bukkit.Objects.Timer.TimerBossBar;
import it.angrybear.Bukkit.Utils.ActionBarUtils;
import it.angrybear.Objects.ReflObject;
import it.angrybear.Objects.Timer.Timer;
import it.angrybear.Objects.Wrappers.PlayerWrapper;
import it.angrybear.Utils.StringUtils;
import it.angrybear.Utils.TitleUtils;
import it.fulminazzo.teleporteffects.Enums.ConfigOption;
import it.fulminazzo.teleporteffects.Enums.ConfigOptions.*;
import it.fulminazzo.teleporteffects.Enums.Message;
import it.fulminazzo.teleporteffects.TeleportEffects;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.List;

public class TeleportPlayer extends BearPlayer<TeleportEffects<?, ?>> {
    @PreventSaving
    private FromTask fromTask;
    @PreventSaving
    private ToTask toTask;
    @PreventSaving
    private Timer messagesTask;
    @PreventSaving
    private Timer actionBarTask;
    @PreventSaving
    private TimerBossBar bossBar;
    @PreventSaving
    private Location lastLocation;
    private boolean showEffects;

    public TeleportPlayer(TeleportEffects<?, ?> plugin, File playersFolder, OfflinePlayer player) throws Exception {
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
        fromTask.stage1(destinationLocation);
        // Send messages and titles
        messagesTask = new Timer(() -> {});
        messagesTask.setSecondIntermediateAction(time -> {
            sendMessages(time);
            sendTitles(time);
            playSound(time);
        });
        // Send bossBar
        sendBossBar();
        // Send actionBar
        actionBarTask = new Timer(() -> {});
        actionBarTask.setInterval(0.1);
        actionBarTask.setSecondIntermediateAction(time -> sendActionBar(time, false));
        // Send sound
        SoundOption.TELEPORT_START.getSound().playSound(player);
        messagesTask.start(getPlugin(), ConfigOption.DURATION.getInt());
        actionBarTask.start(getPlugin(), ConfigOption.DURATION.getInt());
    }

    private void sendMessages(double t) {
        int time = (int) t;
        if (time != t) return;
        if (isOffline()) return;
        int duration = ConfigOption.DURATION.getInt();
        if (duration < 1) return;
        time = duration - time;
        boolean everySecond = Messages.EVERY_SECOND.getBoolean();
        String message = Messages.TELEPORTING_IN.getMessage();
        if (message == null || message.trim().isEmpty()) return;
        if (!everySecond && time != duration) return;
        getPlayer().sendMessage(message.replace("%time%", String.valueOf(time)));
    }

    private void sendTitles(double t) {
        int time = (int) t;
        if (time != t) return;
        if (isOffline()) return;
        int duration = ConfigOption.DURATION.getInt();
        if (duration < 1) return;
        time = duration - time;
        boolean everySecond = Messages.EVERY_SECOND.getBoolean();
        String title = Titles.TELEPORTING_IN_TITLE.getMessage();
        String subTitle = Titles.TELEPORTING_IN_SUBTITLE.getMessage();
        if ((title == null || title.trim().isEmpty()) &&
                (subTitle == null || subTitle.trim().isEmpty())) return;
        if (!everySecond && time != duration) return;
        if (title == null) title = "";
        if (subTitle == null) subTitle = "";
        TitleUtils.sendGeneralTitle(getPlayer(),
                title.replace("%time%", String.valueOf(time)),
                subTitle.replace("%time%", String.valueOf(time)));
    }

    private void playSound(double t) {
        int time = (int) t;
        if (time != t) return;
        if (isOffline()) return;
        int duration = ConfigOption.DURATION.getInt();
        if (duration < 1) return;
        time = duration - time;
        if (time != duration) SoundOption.TELEPORTING.getSound().playSound(getPlayer());
    }

    private void sendBossBar() {
        if (isOffline()) return;
        int duration = ConfigOption.DURATION.getInt();
        if (duration < 1) return;
        String bossBarTitle = Boss_Bar.TELEPORTING_IN_TITLE.getMessage();
        if (bossBarTitle == null || bossBarTitle.trim().isEmpty()) return;
        String barColor = Boss_Bar.TELEPORTING_IN_BAR_COLOR.getString();
        String barStyle = Boss_Bar.TELEPORTING_IN_BAR_STYLE.getString();
        List<String> barFlags = Boss_Bar.TELEPORTING_IN_BAR_FLAGS.getStringList();
        bossBar = new TimerBossBar(bossBarTitle, barColor, barStyle);
        bossBar.setInterval(0.1);
        bossBar.setSecondIntermediateAction(time ->
                bossBar.setTitle(bossBarTitle.replace("%time%", String.valueOf((int) Math.ceil(duration - time)))));
        bossBar.setTitle(bossBarTitle.replace("%time%", String.valueOf(duration)));
        barFlags.forEach(f -> bossBar.addFlag(f));
        bossBar.addPlayer(getPlayer());
        bossBar.start(getPlugin(), duration);
    }

    public void sendActionBar(double time, boolean cancelled) {
        if (isOffline()) return;
        int duration = ConfigOption.DURATION.getInt();
        if (duration < 1) return;
        int length = Action_Bar.LENGTH.getInt();
        if (length < 1) return;
        if (Action_Bar.REVERSE.getBoolean()) time = duration - time;
        String progressOn = (cancelled ? Action_Bar.PROGRESS_CANCELLED : Action_Bar.PROGRESS_ON).getMessage();
        String progressOff = Action_Bar.PROGRESS_OFF.getMessage();
        if ((progressOn == null || progressOn.trim().isEmpty()) &&
                (progressOff == null || progressOff.trim().isEmpty())) return;
        if (progressOn == null) progressOn = "";
        if (progressOff == null) progressOff = "";
        double precision = 1000;
        int actualLength = (int) (length * (Math.round(time * precision) / precision) / duration);
        String active = StringUtils.repeatChar(progressOn, actualLength);
        String inactive = StringUtils.repeatChar(progressOff, length - actualLength);
        ActionBarUtils.sendActionBar(getPlayer(), active + inactive);
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
        if (!isTeleporting()) return;
        double actionBarCounter = actionBarTask.getCounter();
        stopAllTasks();
        Player player = getPlayer();
        // Send messages
        String message = Messages.TELEPORT_CANCELLED.getMessage();
        if (message != null && !message.trim().isEmpty()) player.sendMessage(message);
        // Send titles
        String title = Titles.TELEPORT_CANCELLED_TITLE.getMessage();
        String subTitle = Titles.TELEPORT_CANCELLED_SUBTITLE.getMessage();
        if ((title != null && !title.trim().isEmpty()) ||
                (subTitle != null && !subTitle.trim().isEmpty())) {
            if (title == null) title = "";
            if (subTitle == null) subTitle = "";
            TitleUtils.sendGeneralTitle(player, title, subTitle);
        }
        // Send bossBar
        String bossBarTitle = Boss_Bar.TELEPORT_CANCELLED_TITLE.getMessage();
        String barColor = Boss_Bar.TELEPORT_CANCELLED_BAR_COLOR.getString();
        String barStyle = Boss_Bar.TELEPORT_CANCELLED_BAR_STYLE.getString();
        List<String> barFlags = Boss_Bar.TELEPORT_CANCELLED_BAR_FLAGS.getStringList();
        if (bossBarTitle != null && !bossBarTitle.trim().isEmpty()) {
            bossBar = new TimerBossBar(bossBarTitle, barColor, barStyle);
            bossBar.setInterval(0.1);
            barFlags.forEach(f -> bossBar.addFlag(f));
            bossBar.addPlayer(player);
            bossBar.start(getPlugin(), Boss_Bar.TELEPORT_CANCELLED_DURATION.getInt());
        }
        // Send actionBar
        sendActionBar(actionBarCounter, true);
        // Send sound
        SoundOption.TELEPORT_CANCELLED.getSound().playSound(player);

        String effect = ConfigOption.TELEPORT_EFFECT.getString();
        setEssentialsLastLocation();
        if (effect == null || effect.trim().isEmpty()) return;
        if (isOffline()) return;
        ReflObject<?> effectReflObject = new ReflObject<>(PotionEffectType.class.getCanonicalName(), false);
        effectReflObject.setShowErrors(false);
        PotionEffectType effectType = effectReflObject.getFieldObject(effect);
        if (effectType != null) player.removePotionEffect(effectType);
    }

    public void stopAllTasks() {
        if (fromTask != null) fromTask.stop();
        if (toTask != null) toTask.stop();
        if (messagesTask != null) messagesTask.stop();
        if (bossBar != null) {
            bossBar.stop();
            bossBar.removeAll();
        }
        if (actionBarTask != null) actionBarTask.stop();
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