package it.fulminazzo.teleporteffects.Commands;

import it.angrybear.Bukkit.Objects.BearPlayer;
import it.fulminazzo.teleporteffects.Enums.Message;
import it.fulminazzo.teleporteffects.Objects.TeleportPlayer;
import it.fulminazzo.teleporteffects.TeleportEffects;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ToggleTeleportEffects implements TabExecutor {
    private final TeleportEffects<? extends TeleportPlayer, ? extends BearPlayer<?>> plugin;

    public ToggleTeleportEffects(TeleportEffects<? extends TeleportPlayer, ? extends BearPlayer<?>> plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player))
            sender.sendMessage(Message.CONSOLE_CANNOT_EXECUTE.getMessage(true));
        else {
            TeleportPlayer teleportPlayer = plugin.getPlayersManager().getPlayer((Player) sender);
            if (teleportPlayer.hasDisabledEffects()) teleportPlayer.enableEffects();
            else teleportPlayer.disableEffects();
            sender.sendMessage(Message.TELEPORT_EFFECTS_CHANGED.getMessage(true)
                    .replace("%status%", teleportPlayer.hasDisabledEffects() ?
                            Message.DISABLED.getMessage(false) :
                            Message.ENABlED.getMessage(false)
                    ));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList<>();
    }
}