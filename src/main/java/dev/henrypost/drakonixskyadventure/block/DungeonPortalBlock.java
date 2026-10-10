package dev.henrypost.drakonixskyadventure.block;

import dev.henrypost.drakonixskyadventure.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

/**
 * Unbreakable (bedrock-style strength) spawn-structure block: stepping on it teleports
 * the player to a randomly-offset nearby dungeon structure, rather than to the nearest
 * one, so it doesn't always send players to the same closest dungeon.
 *
 * Simplification: vanilla's structure search (ServerLevel#findNearestMapStructure) only
 * finds the *nearest* match from a given point - there's no public API to pick randomly
 * among several candidates in a radius without walking internal chunk-generator state.
 * This block works around that by searching from a random point within the configured
 * range instead of from the player, which is simple, stable across versions, and still
 * sends players to a different dungeon most of the time.
 */
public class DungeonPortalBlock extends Block {

	private static final Map<UUID, Long> LAST_TELEPORT_GAME_TIME = new WeakHashMap<>();

	public DungeonPortalBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if(level.isClientSide) return;
		if(!(entity instanceof ServerPlayer player)) return;
		if(!(level instanceof ServerLevel serverLevel)) return;

		long now = level.getGameTime();
		Long last = LAST_TELEPORT_GAME_TIME.get(player.getUUID());
		if(last != null && now - last < Config.DUNGEON_PORTAL_COOLDOWN_TICKS.get()) return;

		BlockPos destination = findRandomDungeon(serverLevel, pos);
		if(destination == null) return;

		LAST_TELEPORT_GAME_TIME.put(player.getUUID(), now);

		int surfaceY = serverLevel.getHeight(Heightmap.Types.MOTION_BLOCKING, destination.getX(), destination.getZ());
		player.teleportTo(destination.getX() + 0.5, surfaceY + 1, destination.getZ() + 0.5);
	}

	private BlockPos findRandomDungeon(ServerLevel level, BlockPos origin) {
		ResourceLocation tagId = ResourceLocation.tryParse(Config.DUNGEON_STRUCTURE_TAG.get());
		if(tagId == null) return null;
		TagKey<Structure> structureTag = TagKey.create(Registries.STRUCTURE, tagId);

		RandomSource random = level.getRandom();
		int minRange = Config.DUNGEON_PORTAL_MIN_RANGE.get();
		int maxRange = Math.max(minRange + 1, Config.DUNGEON_PORTAL_MAX_RANGE.get());

		double angle = random.nextDouble() * Math.PI * 2;
		int distance = minRange + random.nextInt(maxRange - minRange);
		BlockPos searchOrigin = origin.offset((int) (Math.cos(angle) * distance), 0, (int) (Math.sin(angle) * distance));

		// findNearestMapStructure's radius is in chunks, not blocks.
		int searchRadiusChunks = Math.max(4, maxRange / 16);
		return level.findNearestMapStructure(structureTag, searchOrigin, searchRadiusChunks, false);
	}

}
