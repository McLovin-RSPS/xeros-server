package io.Odyssey.model.cycleevent.impl;

import java.util.concurrent.TimeUnit;

import io.Odyssey.model.cycleevent.Event;
import io.Odyssey.model.entity.player.PlayerHandler;
import io.Odyssey.util.Misc;

public class UpdateQuestTab extends Event<Object> {


	private static final int INTERVAL = Misc.toCycles(5, TimeUnit.SECONDS);

	
	public UpdateQuestTab() {
		super("", new Object(), INTERVAL);
	}	

	@Override
	public void execute() {
		PlayerHandler.nonNullStream().forEach(player -> { 
			player.getQuestTab().updateInformationTab();
		});
	}
} 