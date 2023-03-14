package io.Odyssey.model.entity.player.packets.objectoptions.impl;

import io.Odyssey.model.entity.player.Boundary;
import io.Odyssey.model.entity.player.Player;

public class Raids3Objects {

    public static boolean clickObject1(Player player, int objectId, int objX, int objY) {
        if (!Boundary.isIn(player, Boundary.RAIDS3_MAIN)) {
            return false;
        }
        if (objectId == 45131) { // path 1
            if (objX == 3661 && objY == 5278) {
                player.getPA().movePlayer(3804, 5276, 0);
            }
        }
        if (objectId == 45509) { // path 1
            if (objX == 3933 && objY == 5397) {
                player.setTeleportToX(3937);
                player.setTeleportToY(5402);
                player.heightLevel = 1;
            }
        }
        if (objectId == 45397) { // path 1
            if (objX == 3916 && objY == 5279) {
                player.getPA().movePlayer(3805, 5406, 0);
            }
        }

        player.sendMessage("Clicked "+objectId+" at "+objX+", "+objY+"");
        return false;
    }
}
//        if (type == BroadcastType.TELEPORT) {//
//            c.getOutStream().writeDWord(broadcast.getTeleport().getX());//
//            c.getOutStream().writeDWord(broadcast.getTeleport().getY());//
//            c.getOutStream().writeByte(broadcast.getTeleport().getHeight());//



//    public void movePlayerUnconditionally(int x, int y, int h) {
//        c.resetWalkingQueue();
//        c.setTeleportToX(x);
//        c.setTeleportToY(y);
//        c.heightLevel = h;
//        c.teleTimer = 4;
//        requestUpdates();
//        c.lastMove = System.currentTimeMillis();
//    }          c.getOutStream().writeByte(broadcast.getTeleport().getHeight());//