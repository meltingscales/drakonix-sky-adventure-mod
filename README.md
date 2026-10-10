# Drakonix Sky Adventure Mod

Standalone NeoForge 1.21.1 mod for the
[`MinecraftModpackDrakonixSkyAdventure`](https://github.com/meltingscales/MinecraftModpackDrakonixSkyAdventure)
pack, covering the gameplay pieces no off-the-shelf mod provides. Built jar gets
pulled into the pack via `packwiz url add` once released (same pattern as that
pack's `waylandcraft` dependency).

## What's in it

- **Certus Trader block** (`certus_trader`): right-click with emeralds to buy a
  Certus Quartz Crystal (`ae2:certus_quartz_crystal`). AE2 only lets you *grow*
  Certus Quartz (including via its own Growth Accelerator block, which already
  covers "speed up growth" - not reimplemented here); this is the purchase path.
  Price configurable (`emeraldsPerCertusQuartz`, common config, default 4).

- **Dungeon Portal block** (`dungeon_portal`): unbreakable (bedrock-style strength),
  stepping on it teleports the player to a randomly-offset nearby dungeon structure.
  Meant to be built into a custom spawn structure. Configurable: which structure tag
  to search for (`dungeonStructureTag`, defaults to the vanilla `minecraft:mineshaft`
  tag until a real dungeon mod's tag is confirmed), search range, and a per-player
  teleport cooldown.

  **Known simplification**: vanilla's structure search API
  (`ServerLevel#findNearestMapStructure`) only finds the *nearest* match from a given
  point - there's no public API to pick randomly among several candidates in a radius
  without reaching into internal chunk-generator state. This block works around that
  by searching from a random point within the configured range instead of from the
  player's own position, which stays on stable public API and still lands players on
  a different dungeon most of the time, but isn't a true uniform-random pick among
  all nearby dungeons.

## Not yet done

- Spawn structure template (`.nbt`) with `dungeon_portal` blocks baked in, placed once
  on first world load - needs to be authored in-game with structure blocks first.
- Confirming the right structure tag for whichever dungeon mod the pack settles on
  (currently **When Dungeons Arise**) and pointing `dungeonStructureTag` at it.

## Building

```
just build          # or ./gradlew build
```

Output jar: `build/libs/drakonix-sky-adventure-mod.jar`.

## Dev run

```
just run-server      # or just run-client
```
