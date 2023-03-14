package io.Odyssey.content.commands.all;

import java.util.Optional;

import io.Odyssey.content.commands.Command;
import io.Odyssey.model.entity.player.Player;

/**
 * Open the forums in the default web browser.
 * 
 * @author Emiel
 */
public class Ls extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {
		c.sendMessage("@red@[ls]@blu@ Bank your items and enter the portal to join the tournament! Good Luck!");
    	c.getPA().spellTeleport(3103, 3499, 0, false);
}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Quick teleport to Lone Survivor teleport.");
	}

}
