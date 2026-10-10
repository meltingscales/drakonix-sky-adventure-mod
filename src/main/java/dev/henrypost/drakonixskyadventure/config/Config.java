package dev.henrypost.drakonixskyadventure.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {

	private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

	public static final ModConfigSpec.IntValue EMERALDS_PER_CERTUS_QUARTZ = BUILDER
			.comment("Emeralds the Certus Trader block charges for one Certus Quartz Crystal")
			.defineInRange("emeraldsPerCertusQuartz", 4, 1, 64);

	// Vanilla structure tag searched for the dungeon portal's teleport destination.
	// Point this at a dungeon mod's own tag (e.g. a "when_dungeons_arise" tag) once
	// one is confirmed - defaults to a vanilla tag so the block works out of the box.
	public static final ModConfigSpec.ConfigValue<String> DUNGEON_STRUCTURE_TAG = BUILDER
			.comment("Structure tag (namespace:path) the dungeon portal searches for")
			.define("dungeonStructureTag", "minecraft:mineshaft");

	public static final ModConfigSpec.IntValue DUNGEON_PORTAL_MIN_RANGE = BUILDER
			.comment("Minimum horizontal distance (blocks) the dungeon portal searches from the player")
			.defineInRange("dungeonPortalMinRange", 256, 0, 10_000);

	public static final ModConfigSpec.IntValue DUNGEON_PORTAL_MAX_RANGE = BUILDER
			.comment("Maximum horizontal distance (blocks) the dungeon portal searches from the player")
			.defineInRange("dungeonPortalMaxRange", 2_000, 1, 30_000);

	public static final ModConfigSpec.IntValue DUNGEON_PORTAL_COOLDOWN_TICKS = BUILDER
			.comment("Ticks a player must wait before the dungeon portal can teleport them again")
			.defineInRange("dungeonPortalCooldownTicks", 100, 0, 72_000);

	public static final ModConfigSpec SPEC = BUILDER.build();

}
