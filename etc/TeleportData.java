package io.Odyssey.content.teleportnew;

import io.Odyssey.model.Items;
import io.Odyssey.model.entity.player.Position;

import java.util.Arrays;
import java.util.List;

public enum TeleportData {
    //Monsters
    ROCK_CRAB(100, 137182, 0, 800, List.of(Items.UNCUT_SAPPHIRE), new Position(2673, 3710, 0)),
    LUMBRIDGE_COWS(5842, 137183,0, 950, List.of(Items.RAW_BEEF, Items.COWHIDE), new Position(3260, 3272, 0)),
    HILL_GIANTS(2098, 137184,0, 1750, List.of(Items.GIANT_KEY, Items.GRIMY_DWARF_WEED, Items.RUNE_AXE), new Position(3260, 3272, 0)),
    //Bosses
    ZULRAH(2042, 137182, 1, 2600, List.of(Items.TANZANITE_MUTAGEN, Items.MAGMA_MUTAGEN, Items.JAR_OF_SWAMP), new Position(2203, 3056, 0)),
    OBOR(7416, 137183, 1, 1750, List.of(Items.GIANT_CHAMPION_SCROLL, Items.CURVED_BONE), new Position(3097, 9833, 0)),
    //Wilderness
    WESTERN_DRAGONS(-1, 137182,2, 0, List.of(Items.DRAGON_MACE, Items.DRAGON_DAGGERPPLUS), new Position(2976, 3591, 0)),
    //Minigames
    DUEL_ARENA(-1, 137182,3, 0, List.of(), new Position(1050, 1050, 0)),
    //Donator
    DONATOR_ZONE(-1, 137182,4, 0, List.of(), new Position(3809, 2844, 0)),

    ;

    private int npcId;
    private int buttonId;
    private int category;
    List<Integer> possibleDrops;
    private Position position;
    private int zoom;

    TeleportData(int npcId, int buttonId, int category, int zoom, List<Integer> possibleDrops, Position position) {
        this.npcId = npcId;
        this.buttonId = buttonId;
        this.category = category;
        this.zoom = zoom;
        this.possibleDrops = possibleDrops;
        this.position = position;
    }

    public int getNpcId() {
        return npcId;
    }
    public int getButtonId() {
        return buttonId;
    }
    public int getCategory() {
        return category;
    }
    public int getZoom() {
        return zoom;
    }
    public List<Integer> getPossibleDrops() {
        return possibleDrops;
    }
    public Position getPosition() {
        return position;
    }

    public static TeleportData getDataForCategory(int category) {
        return Arrays.stream(values()).filter(x -> x.getCategory() == category).findAny().orElse(null);
    }
    public static TeleportData getDataForButton(int buttonId, int category) {
        return Arrays.stream(values()).filter(x -> x.getButtonId() == buttonId && x.getCategory() == category).findAny().orElse(null);
    }
}
