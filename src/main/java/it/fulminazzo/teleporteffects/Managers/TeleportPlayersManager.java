package it.fulminazzo.teleporteffects.Managers;


import it.angrybear.Bukkit.BearPlugin;
import it.angrybear.Managers.BearPlayersManager;
import it.fulminazzo.teleporteffects.Objects.TeleportPlayer;

import java.util.UUID;

public class TeleportPlayersManager<Player extends TeleportPlayer> extends BearPlayersManager<Player> {
    public TeleportPlayersManager(BearPlugin<?, ?> plugin, Class<Player> customPlayerClass) {
        super(plugin, customPlayerClass);
    }

    @Override
    public <Player1> Player getPlayer(Player1 player1) {
        return super.getPlayer(player1);
    }

    @Override
    public Player getPlayer(String name) {
        return super.getPlayer(name);
    }

    @Override
    public Player getPlayer(UUID uuid) {
        return super.getPlayer(uuid);
    }
}
