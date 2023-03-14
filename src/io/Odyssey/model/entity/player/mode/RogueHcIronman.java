package io.Odyssey.model.entity.player.mode;

public class RogueHcIronman extends IronmanMode {

    public RogueHcIronman(ModeType type) {
        super(type);
    }

    @Override
    public double getDropModifier() {
        return -0.18;
    }

    @Override
    public int getTotalLevelNeededForRaids() {
        return 750;
    }

    @Override
    public int getTotalLevelForTob() {
        return 0;
    }
}
