package it.fulminazzo.teleporteffects;

import it.angrybear.Bukkit.BearPlugin;
import it.angrybear.Bukkit.Objects.BearPlayer;
import it.angrybear.Bukkit.Utils.BukkitUtils;
import it.fulminazzo.teleporteffects.Commands.ToggleTeleportEffects;
import it.fulminazzo.teleporteffects.Enums.TeleportLoggingMessage;
import it.fulminazzo.teleporteffects.Listeners.EssentialsListener;
import it.fulminazzo.teleporteffects.Listeners.PlayerListener;
import it.fulminazzo.teleporteffects.Managers.TeleportPlayersManager;
import it.fulminazzo.teleporteffects.Objects.TeleportPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;

@SuppressWarnings("unchecked")
public class TeleportEffects<Player extends TeleportPlayer, OfflinePlayer extends BearPlayer> extends BearPlugin<Player, OfflinePlayer> {
    private static TeleportEffects<? extends TeleportPlayer, ? extends BearPlayer> plugin;
    private final Class<? extends TeleportPlayersManager<Player>> playersManager;
    private final Class<Player> playersClass;

    public TeleportEffects(Class<?> playersManager, Class<Player> playersClass) {
        this.playersManager = (Class<? extends TeleportPlayersManager<Player>>) playersManager;
        this.playersClass = playersClass;
    }

    @Override
    public void onEnable() {
        plugin = this;
        setPlayerManagerClass(playersManager, playersClass);
        super.onEnable();
        if (isEnabled()) {
            String commandName = "toggleteleporteffects";
            PluginCommand command = getCommand(commandName);
            if (command == null) logWarning(TeleportLoggingMessage.COMMAND_NOT_FOUND, "%command%", commandName);
            else command.setExecutor(new ToggleTeleportEffects(this));
        }
    }

    @Override
    public void loadListeners() throws Exception {
        super.loadListeners();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        if (BukkitUtils.isPluginEnabled("Essentials"))
            Bukkit.getPluginManager().registerEvents(new EssentialsListener(this), this);
    }

    @Override
    public void unloadAll() throws Exception {
        super.unloadAll();
        unloadPermissions(this);
    }

    public static TeleportEffects<? extends TeleportPlayer, ? extends BearPlayer> getPlugin() {
        return plugin;
    }
}
