package me.fopzl.wolfwhinefix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.DecoderException;

public final class SoundDecodeGuard {
	private static final Logger LOGGER = LoggerFactory.getLogger("wolfwhinefix");

	// Text vanilla puts in the DecoderException message for this packet type.
	private static final String SOUND_PACKET_ID = "clientbound/minecraft:sound";
	private static final int DUMP_LIMIT = 128;

	private SoundDecodeGuard() {
	}

	public static boolean isSoundPacketFailure(final DecoderException e) {
		String message = e.getMessage();
		return message != null && message.contains(SOUND_PACKET_ID);
	}

	public static void reportDropped(final DecoderException e, final ByteBuf input, final int start, final int length) {
		Throwable cause = e.getCause() != null ? e.getCause() : e;
		LOGGER.warn(
			"Dropped a malformed sound packet ({} bytes): {}: {} | bytes (first {}): {}",
			length,
			cause.getClass().getSimpleName(),
			cause.getMessage(),
			Math.min(length, DUMP_LIMIT),
			ByteBufUtil.hexDump(input, start, Math.min(length, DUMP_LIMIT))
		);
	}
}
