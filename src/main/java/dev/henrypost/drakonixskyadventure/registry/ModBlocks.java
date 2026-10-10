package dev.henrypost.drakonixskyadventure.registry;

import dev.henrypost.drakonixskyadventure.DrakonixSkyAdventure;
import dev.henrypost.drakonixskyadventure.block.CertusTraderBlock;
import dev.henrypost.drakonixskyadventure.block.DungeonPortalBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {

	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, DrakonixSkyAdventure.MOD_ID);
	private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DrakonixSkyAdventure.MOD_ID);

	public static final DeferredHolder<Block, CertusTraderBlock> CERTUS_TRADER = BLOCKS.register("certus_trader",
			() -> new CertusTraderBlock(BlockBehaviour.Properties.of()
					.mapColor(MapColor.DIAMOND)
					.sound(SoundType.AMETHYST)
					.strength(3.0F, 6.0F)));
	public static final DeferredItem<BlockItem> CERTUS_TRADER_ITEM = ITEMS.registerItem("certus_trader",
			(properties) -> new BlockItem(CERTUS_TRADER.get(), properties));

	public static final DeferredHolder<Block, DungeonPortalBlock> DUNGEON_PORTAL = BLOCKS.register("dungeon_portal",
			() -> new DungeonPortalBlock(BlockBehaviour.Properties.of()
					.mapColor(MapColor.COLOR_PURPLE)
					.sound(SoundType.GLASS)
					.strength(-1.0F, 3_600_000.0F)
					.noLootTable()
					.lightLevel((state) -> 11)));
	public static final DeferredItem<BlockItem> DUNGEON_PORTAL_ITEM = ITEMS.registerItem("dungeon_portal",
			(properties) -> new BlockItem(DUNGEON_PORTAL.get(), properties));

	public static void register(IEventBus modBus) {
		BLOCKS.register(modBus);
		ITEMS.register(modBus);
	}

}
