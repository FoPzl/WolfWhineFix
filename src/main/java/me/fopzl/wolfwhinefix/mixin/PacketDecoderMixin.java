package me.fopzl.wolfwhinefix.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import me.fopzl.wolfwhinefix.SoundDecodeGuard;
import net.minecraft.network.PacketDecoder;

@Mixin(PacketDecoder.class)
public class PacketDecoderMixin {
	@WrapMethod(method = "decode")
	private void wolfwhinefix$guardSoundDecode(
		final ChannelHandlerContext ctx, final ByteBuf input, final List<Object> out, final Operation<Void> original
	) {
		int start = input.readerIndex();
		int length = input.readableBytes();
		try {
			original.call(ctx, input, out);
		} catch (DecoderException e) {
			if (!SoundDecodeGuard.isSoundPacketFailure(e)) {
				throw e;
			}

			SoundDecodeGuard.reportDropped(e, input, start, length);
			// Nothing was added to `out`; skipping the frame's leftover bytes stops them being parsed as the next packet.
			input.skipBytes(input.readableBytes());
		}
	}
}
