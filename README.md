# WolfWhineFix

A client-side Fabric mod that keeps you connected when a server sends a malformed sound packet.

Some servers send a `clientbound/minecraft:sound` packet the client cannot decode, which disconnects you with
`Failed to decode packet 'clientbound/minecraft:sound'`. This mod drops only those packets and logs a warning instead.

Commonly seen alongside "Unable to play unknown soundEvent: minecraft:entity.wolf_cute.howl" and "Unable to play unknown soundEvent: minecraft:entity.wolf_grumpy.white".

## Requirements

- Minecraft 26.2+
- Fabric Loader 0.19.5+
- Java 25+

The mod is client-only and does not need Fabric API.

## License

CC0-1.0, see [LICENSE](LICENSE).
