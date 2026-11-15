# WolfWhineFix

A client-side Fabric mod that keeps you connected when a server sends certain broken packets.

## Fixes

### Malformed sound packets

Some servers send a `clientbound/minecraft:sound` packet the client cannot decode, which disconnects you with
`Failed to decode packet 'clientbound/minecraft:sound'`. This mod drops only those packets and logs a warning instead.

Commonly seen alongside "Unable to play unknown soundEvent: minecraft:entity.wolf_cute.howl" and "Unable to play unknown soundEvent: minecraft:entity.wolf_grumpy.white".

### Swing animations for non-living entities

On some servers (seen with MythicMobs mobs disguised as blocks, when an older client connects through ViaVersion/ViaBackwards),
touching a disguised mob disconnects you with
`Failed to handle packet net.minecraft.network.protocol.game.ClientboundAnimatePacket` caused by
`ClassCastException: class net.minecraft.world.entity.item.FallingBlockEntity cannot be cast to class net.minecraft.world.entity.LivingEntity`.

The server tells the client to play an arm-swing animation for an entity the client sees as a non-living one (such as a
falling block), and vanilla always crashes in that case. This mod skips just that swing animation and logs one warning per
affected entity. All other animations are handled normally.

## Requirements

- Minecraft 26.2+
- Fabric Loader 0.19.5+
- Java 25+

The mod is client-only and does not need Fabric API.

## License

CC0-1.0, see [LICENSE](LICENSE).
