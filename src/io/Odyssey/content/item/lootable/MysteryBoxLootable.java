package io.Odyssey.content.item.lootable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import io.Odyssey.model.cycleevent.CycleEvent;
import io.Odyssey.model.cycleevent.CycleEventContainer;
import io.Odyssey.model.cycleevent.CycleEventHandler;
import io.Odyssey.model.definitions.ItemDef;
import io.Odyssey.model.definitions.ShopDef;
import io.Odyssey.model.entity.player.Player;
import io.Odyssey.model.entity.player.PlayerHandler;
import io.Odyssey.model.entity.player.Right;
import io.Odyssey.model.items.GameItem;
import io.Odyssey.util.Misc;

public abstract class MysteryBoxLootable implements Lootable {

    public abstract int getItemId();

    /**
     * The player object that will be triggering this event
     */
    private final Player player;

    /**
     * Constructs a new myster box to handle item receiving for this player and this player alone
     *
     * @param player the player
     */
    public MysteryBoxLootable(Player player) {
        this.player = player;
    }

    /**
     * Can the player open the mystery box
     */
    public boolean canMysteryBox = true;

    /**
     * The prize received
     */
    private int mysteryPrize;

    private int mysteryAmount;

    private int spinNum;

    /**
     * The chance to obtain the item
     */
    private int random;
    private boolean active;
    private final int INTERFACE_ID = 47000;
    private final int ITEM_FRAME = 47101;

    public boolean isActive() {
        return active;
    }

    public void spin() {
        //reload
        openInterface();

        // Server side checks for spin
        if (!canMysteryBox) {
            player.sendMessage("Please finish your current spin.");
            return;
        }
        if (!player.getItems().playerHasItem(getItemId())) {
            player.sendMessage("You require a mystery box to do this.");
            return;
        }

        if (!player.getRights().getPrimary().equals(Right.OWNER))
            player.getItems().deleteItem(getItemId(), 1);

        for (int i = 0; i < 66; i++) {
            player.getPA().sendFrame34a(ITEM_FRAME, -1, i, 1);
        }
        spinNum = 0;
        player.sendMessage(":spin:");
        process();
    }

    public void process() {
        mysteryPrize = -1;
        mysteryAmount = -1;
        canMysteryBox = false;
        active = true;

        // roll prize
        setMysteryPrize();
        player.getPA().sendString(47019, "@whi@Spinning...");

        if (spinNum == 0) {
            for (int i = 0; i < 66; i++) {
                MysteryBoxRarity notPrizeRarity = MysteryBoxRarity.values()[new Random().nextInt(MysteryBoxRarity.values().length)];
                GameItem NotPrize = Misc.getRandomItem(getLoot().get(notPrizeRarity.getLootRarity()));
                final int NOT_PRIZE_ID = NotPrize.getId();
                sendItem(i, 55, mysteryPrize, NOT_PRIZE_ID);
            }
        } else {
            for (int i = spinNum * 50 + 16; i < spinNum * 50 + 66; i++) {
                MysteryBoxRarity notPrizeRarity = MysteryBoxRarity.values()[new Random().nextInt(MysteryBoxRarity.values().length)];
                final int NOT_PRIZE_ID = Misc.getRandomItem(getLoot().get(notPrizeRarity.getLootRarity())).getId();
                sendItem(i, (spinNum + 1) * 50 + 5, mysteryPrize, NOT_PRIZE_ID);
            }
        }

        spinNum++;

        CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
            @Override
            public void execute(CycleEventContainer container) {

                if (mysteryPrize == -1) {
                    canMysteryBox = true;
                    player.getNormalMysteryBox().canMysteryBox();
                    player.getUltraMysteryBox().canMysteryBox();
                    player.getSuperMysteryBox().canMysteryBox();
                    player.getBlackAodLootChest().canMysteryBox();
                    player.getFoeMysteryBox().canMysteryBox();
                    player.getYoutubeMysteryBox().canMysteryBox();
                    container.stop();
                    return;
                }

                player.getItems().addItemUnderAnyCircumstance(mysteryPrize, mysteryAmount);
                String name = ItemDef.forId(mysteryPrize).getName();
                if (random > 85) {
                    String itemName = ItemDef.forId(getItemId()).getName();
                    PlayerHandler.executeGlobalMessage("[<col=A10081>" + itemName + "</col>] <col=bla>"
                            + player.getDisplayName()
                            + "</col> received a <col=A10081>" + name + "</col>!");
                }
                active = false;
                player.inDonatorBox = true;

                // display the winning item on the interface
                player.getPA().sendFrame34a(47400, mysteryPrize, 0, mysteryAmount);
                player.getPA().sendString(47019, "@whi@" + name);

                // Reward message
                player.sendMessage("You've won a <col=A10081>" + name + "</col> from the <col=A10081>" + ItemDef.forId(getItemId()).getName() + "</col>.");

                mysteryPrize = -1;
            }
        }, 20);
    }

    public void setMysteryPrize() {
        random = Misc.random(100);
        List<GameItem> itemList = random < 50 ? getLoot().get(MysteryBoxRarity.COMMON.getLootRarity()) : random >= 50
                && random <= 85 ? getLoot().get(MysteryBoxRarity.UNCOMMON.getLootRarity())
                : getLoot().get(MysteryBoxRarity.RARE.getLootRarity());
        GameItem item = Misc.getRandomItem(itemList);
        mysteryPrize = item.getId();
        mysteryAmount = item.getAmount();

    }

    public void sendItem(int slot, int prizeSlot, int prize, int other_prizes) {
        if (slot == prizeSlot) {
            player.getPA().sendFrame34a(ITEM_FRAME, prize, slot, 1);
        } else {
            player.getPA().sendFrame34a(ITEM_FRAME, other_prizes, slot, 1);
        }
    }

    public void openInterface() {
        player.boxCurrentlyUsing = getItemId();
        player.sendMessage(":resetBox:");

        // reset items on spinner
        for (int i = 0; i < 66; i++) {
            player.getPA().mysteryBoxItemOnInterface(-1, 1, ITEM_FRAME, i);
        }

        // reset rewards box
        for (int i = 0; i < 28; i++) {
            player.getPA().sendFrame34a(47301, -1, i, 1);
        }


        // shows possible rare loot
        List<GameItem> items = getLoot().get(LootRarity.RARE);
        int idx = 0;
        for (GameItem i : items) {
            player.getPA().sendFrame34a(47301, i.getId(), idx, 1);
            idx++;
        }

        spinNum = 0;

        player.getPA().sendString(ItemDef.forId(getItemId()).getName(), 47002);
        player.getPA().sendString("Rare Rewards: @whi@" + ItemDef.forId(getItemId()).getName(), 47012);
        player.getPA().sendFrame126("", 47019);

        // reset players last win
        player.getPA().sendFrame34a(47400, -1, 0, 1);

        player.getPA().showInterface(INTERFACE_ID);
    }

    public void canMysteryBox() {
        canMysteryBox = true;
    }

    public void quickOpen() {

        if (player.getUltraInterface().isActive() || player.getBlackAodInterface().isActive() || player.getSuperBoxInterface().isActive() || player.getNormalBoxInterface().isActive() || player.getFoeInterface().isActive()) {
            player.sendMessage("@red@[WARNING] @blu@Please do not interrupt or you @red@WILL@blu@ lose items! @red@NO REFUNDS");

            return;
        }
        if (!(player.getSuperMysteryBox().canMysteryBox) || !(player.getBlackAodLootChest().canMysteryBox) || !(player.getNormalMysteryBox().canMysteryBox) ||
                !(player.getUltraMysteryBox().canMysteryBox) || !(player.getFoeMysteryBox().canMysteryBox) ||
                !(player.getYoutubeMysteryBox().canMysteryBox) || !(player.getBlackAodLootChest().canMysteryBox)
        ) {
            player.getPA().showInterface(47000);
            player.sendMessage("@red@[WARNING] @blu@Please do not interrupt or you @red@WILL@blu@ lose items! @red@NO REFUNDS");
            return;
        }
        if (player.getItems().playerHasItem(getItemId(), 1)) {
            player.getItems().deleteItem(getItemId(), 1);
            setMysteryPrize();
            roll(player);
        } else {
            player.sendMessage("@blu@You have used your last mystery box.");
        }
    }

    @Override
    public void roll(Player player) {

    }
}
