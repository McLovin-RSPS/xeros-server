package io.Odyssey.content.bosses.nex.attacks;

import io.Odyssey.model.entity.player.Player;

public class ChokeAttack {
    public ChokeAttack(Player target) {
        ChokeAttack(target);
    }

    void ChokeAttack(Player target) {
        target.forcedChat("*Cough*");
        target.nexCoughDelay = 15;
        target.nexVirusTimer = 100;
        target.hasNexVirus = true;
    }
}
