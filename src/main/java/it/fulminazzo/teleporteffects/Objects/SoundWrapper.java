package it.fulminazzo.teleporteffects.Objects;

import it.angrybear.Objects.Configurations.Configuration;
import it.fulminazzo.teleporteffects.Enums.ConfigOptions.SoundOption;
import it.fulminazzo.teleporteffects.Enums.TeleportLoggingMessage;
import it.fulminazzo.teleporteffects.TeleportEffects;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class SoundWrapper {
    private final String name;
    private final float volume;
    private final float pitch;

    public SoundWrapper(SoundOption soundOption) {
        Configuration section = soundOption.getConfiguration();
        this.name = section.getString("name");
        this.volume = (float) section.getDouble("volume");
        this.pitch = (float) section.getDouble("pitch");
    }

    public void playSound(Player player) {
        if (player == null) return;
        if (name == null || name.trim().isEmpty()) return;
        Sound sound = Arrays.stream(Sound.values()).filter(s -> s.name().equalsIgnoreCase(name)).findFirst().orElse(null);
        if (sound == null) TeleportEffects.logWarning(TeleportLoggingMessage.SOUND_NOT_FOUND, "%sound%", name);
        else player.playSound(player.getEyeLocation(), sound, volume, pitch);
    }
}