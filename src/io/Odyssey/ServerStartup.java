package io.Odyssey;

import io.Odyssey.annotate.Init;
import io.Odyssey.annotate.PostInit;
import io.Odyssey.content.boosts.Boosts;
import io.Odyssey.content.bosses.godwars.GodwarsEquipment;
import io.Odyssey.content.bosses.godwars.GodwarsNPCs;
import io.Odyssey.content.bosses.nightmare.NightmareStatusNPC;
import io.Odyssey.content.bosses.sarachnis.SarachnisNpc;
import io.Odyssey.content.collection_log.CollectionLog;
import io.Odyssey.content.combat.stats.TrackedMonster;
import io.Odyssey.content.commands.CommandManager;
import io.Odyssey.content.dailyrewards.DailyRewardContainer;
import io.Odyssey.content.dailyrewards.DailyRewardsRecords;
import io.Odyssey.content.donationrewards.DonationReward;
import io.Odyssey.content.event.eventcalendar.EventCalendar;
import io.Odyssey.content.event.eventcalendar.EventCalendarWinnerSelect;
import io.Odyssey.content.events.monsterhunt.MonsterHunt;
import io.Odyssey.content.fireofexchange.FireOfExchangeBurnPrice;
import io.Odyssey.content.polls.PollTab;
import io.Odyssey.content.preset.PresetManager;
import io.Odyssey.content.referral.ReferralCode;
import io.Odyssey.content.skills.runecrafting.ouriana.ZamorakGuardian;
import io.Odyssey.content.tournaments.TourneyManager;
import io.Odyssey.content.tradingpost.Listing;
import io.Odyssey.content.trails.TreasureTrailsRewards;
import io.Odyssey.content.vote_panel.VotePanelManager;
import io.Odyssey.content.wogw.Wogw;
import io.Odyssey.content.worldevent.WorldEventContainer;
import io.Odyssey.model.Npcs;
import io.Odyssey.model.collisionmap.ObjectDef;
import io.Odyssey.model.collisionmap.Region;
import io.Odyssey.model.collisionmap.doors.DoorDefinition;
import io.Odyssey.model.cycleevent.impl.BonusApplianceEvent;
import io.Odyssey.model.cycleevent.impl.DidYouKnowEvent;
import io.Odyssey.model.cycleevent.impl.LeaderboardUpdateEvent;
import io.Odyssey.model.cycleevent.impl.UpdateQuestTab;
import io.Odyssey.model.definitions.AnimationLength;
import io.Odyssey.model.definitions.ItemDef;
import io.Odyssey.model.definitions.ItemStats;
import io.Odyssey.model.definitions.NpcDef;
import io.Odyssey.model.definitions.NpcStats;
import io.Odyssey.model.definitions.ShopDef;
import io.Odyssey.model.entity.npc.NPCRelationship;
import io.Odyssey.model.entity.npc.NpcSpawnLoader;
import io.Odyssey.model.entity.npc.stats.NpcCombatDefinition;
import io.Odyssey.model.entity.player.PlayerFactory;
import io.Odyssey.model.entity.player.save.PlayerSave;
import io.Odyssey.model.lobby.LobbyManager;
import io.Odyssey.model.world.ShopHandler;
import io.Odyssey.punishments.PunishmentCycleEvent;
import io.Odyssey.model.entity.player.save.backup.PlayerSaveBackup;
import io.Odyssey.util.Reflection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Stuff to do on startup.
 * @author Michael Sasse (https://github.com/mikeysasse/)
 */
public class ServerStartup {

    private static final Logger logger = LoggerFactory.getLogger(ServerStartup.class);

    static void load() throws Exception {
        Reflection.getMethodsAnnotatedWith(Init.class).forEach(method -> {
            try {
                method.invoke(null);
            } catch (Exception e) {
                logger.error("Error loading @Init annotated method[{}] inside class[{}]", method, method.getClass(), e);
                e.printStackTrace();
                System.exit(1);
            }
        });

        DonationReward.load();
        PlayerSave.loadPlayerSaveEntries();
        EventCalendarWinnerSelect.getInstance().init();
        TrackedMonster.init();
        Boosts.init();
        ItemDef.load();
        ShopDef.load();
        ShopHandler.load();
        NpcStats.load();
        ItemStats.load();
        NpcDef.load();
        // Npc Combat Definition must be above npc load
        NpcCombatDefinition.load();
        Server.npcHandler.init();
        NPCRelationship.setup();
        EventCalendar.verifyCalendar();
        Server.getPunishments().initialize();
        Server.getEventHandler().submit(new DidYouKnowEvent());
        Server.getEventHandler().submit(new BonusApplianceEvent());
        Server.getEventHandler().submit(new PunishmentCycleEvent(Server.getPunishments(), 50));
        Server.getEventHandler().submit(new UpdateQuestTab());
        Server.getEventHandler().submit(new LeaderboardUpdateEvent());
        Listing.init();
        Wogw.init();
        PollTab.init();
        DoorDefinition.load();
        GodwarsEquipment.load();
        GodwarsNPCs.load();
        LobbyManager.initializeLobbies();
        VotePanelManager.init();
        TourneyManager.initialiseSingleton();
        TourneyManager.getSingleton().init();
        Server.getDropManager().read();
        TreasureTrailsRewards.load();
        AnimationLength.startup();
        PresetManager.getSingleton().init();
        ObjectDef.loadConfig();
        CollectionLog.init();
        Region.load();
        Server.getGlobalObjects().loadGlobalObjectFile();

        // Keep this below region load and object loading
        NpcSpawnLoader.load();
        MonsterHunt.spawnNPC();
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
        CommandManager.initializeCommands();
        NightmareStatusNPC.init();
        if (Server.isDebug()) {
            PlayerFactory.createTestPlayers();
        }
        ReferralCode.load();
        DailyRewardContainer.load();
        DailyRewardsRecords.load();
        WorldEventContainer.getInstance().initialise();
        FireOfExchangeBurnPrice.init();
        Server.getLogging().schedule();

        ZamorakGuardian.spawn();
        new SarachnisNpc(Npcs.SARACHNIS, SarachnisNpc.SPAWN_POSITION);

        if (Server.isPublic()) {
            PlayerSaveBackup.start(Configuration.PLAYER_SAVE_TIMER_MILLIS, Configuration.PLAYER_SAVE_BACKUP_EVERY_X_SAVE_TICKS);
        }

        Reflection.getMethodsAnnotatedWith(PostInit.class).forEach(method -> {
            try {
                method.invoke(null);
            } catch (Exception e) {
                logger.error("Error loading @PostInit annotated method[{}] inside class[{}]", method, method.getClass(), e);
                e.printStackTrace();
                System.exit(1);
            }
        });
    }

}
