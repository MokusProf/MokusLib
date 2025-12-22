package net.mokus.mokuslib;

import net.fabricmc.api.ModInitializer;

import net.mokus.mokuslib.api.MokusLibAPI;
import net.mokus.mokuslib.components.MokusLibItemComponents;
import net.mokus.mokuslib.cooldown.CooldownManagerImpl;
import net.mokus.mokuslib.item.MokusLibItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MokusLib implements ModInitializer {
	public static final String MOD_ID = "mokuslib";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		MokusLibItem.registerModItems();
		MokusLibItemComponents.init();
		MokusLibAPI.register(new CooldownManagerImpl());
	}
}