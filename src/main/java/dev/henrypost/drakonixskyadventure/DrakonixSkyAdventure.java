package dev.henrypost.drakonixskyadventure;

import dev.henrypost.drakonixskyadventure.config.Config;
import dev.henrypost.drakonixskyadventure.registry.ModBlocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(DrakonixSkyAdventure.MOD_ID)
public class DrakonixSkyAdventure {

	public static final String MOD_ID = "drakonixskyadventure";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public DrakonixSkyAdventure(IEventBus modBus, ModContainer container) {
		ModBlocks.register(modBus);
		container.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
	}

}
