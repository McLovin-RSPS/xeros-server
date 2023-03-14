package io.Odyssey.content.commands.all;

import java.util.Optional;

import io.Odyssey.Server;
import io.Odyssey.content.commands.Command;
import io.Odyssey.model.entity.player.Player;

/**
 * Teleport the player to home.
 * 
 * @author Emiel
 */
public class Home extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {
		if (Server.getMultiplayerSessionListener().inAnySession(c)) {
			return;
		}
		if (c.getPosition().inClanWars() || c.getPosition().inClanWarsSafe()) {
			c.sendMessage("@cr10@You can not teleport from here, speak to the doomsayer to leave.");
			return;
		}
		if (c.getPosition().inWild()) {
			c.sendMessage("You can't use this command in the wilderness.");
			return;
		}
		c.getPA().spellTeleport(3093, 3491, 0, false);
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Teleports you to home area");
	}

}
