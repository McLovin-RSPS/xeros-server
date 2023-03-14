package io.Odyssey.content.bosses.nex;

import io.Odyssey.Server;
import io.Odyssey.content.bosses.nex.NexNPC;
import io.Odyssey.content.bosses.bryophyta.BryophytaNPC;
import io.Odyssey.content.commands.owner.Npc;
import io.Odyssey.content.instances.*;
import io.Odyssey.model.Npcs;
import io.Odyssey.model.entity.npc.NPCSpawning;
import io.Odyssey.model.collisionmap.WorldObject;
import io.Odyssey.model.entity.npc.NPC;
import io.Odyssey.model.entity.player.*;
import io.Odyssey.model.world.objects.GlobalObject;

public class Nex extends InstancedArea {

    public static final int KEY = 26_356;

    private static final InstanceConfiguration CONFIGURATION = new InstanceConfigurationBuilder()
            .setCloseOnPlayersEmpty(true)
            .setRespawnNpcs(true)
            .createInstanceConfiguration();

    public Nex() {
        super(CONFIGURATION, Boundary.NEX);
    }

    public void enter(Player player) {
        try {
            player.sendMessage("Your key fits the gate and you enter.");
            player.getItems().deleteItem(KEY, 1);
            player.moveTo(new Position(2910, 5203, getHeight()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDispose() {
        getPlayers().forEach(this::remove);
    }


}