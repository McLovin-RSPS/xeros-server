package io.Odyssey.model.entity.player.packets;

import io.Odyssey.Server;
import io.Odyssey.model.entity.player.PacketType;
import io.Odyssey.model.entity.player.Player;

public class MapRegionFinish implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		Server.itemHandler.reloadItems(c);
		Server.getGlobalObjects().updateRegionObjects(c);
		if (c.getPA().viewingOtherBank) {
			c.getPA().resetOtherBank();
		}

		if (c.skullTimer > 0) {
			c.isSkulled = true;
			c.headIconPk = 0;
			c.getPA().requestUpdates();
		}
	}

}
