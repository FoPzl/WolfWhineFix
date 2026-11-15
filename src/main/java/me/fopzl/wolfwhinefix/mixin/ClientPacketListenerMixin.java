package me.fopzl.wolfwhinefix.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;

import me.fopzl.wolfwhinefix.AnimateGuard;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.world.entity.Entity;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
	@ModifyExpressionValue(
		method = "handleAnimate",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/multiplayer/ClientLevel;getEntity(I)Lnet/minecraft/world/entity/Entity;"
		)
	)
	private Entity wolfwhinefix$dropBadSwing(final Entity entity, @Local(argsOnly = true) final ClientboundAnimatePacket packet) {
		if (AnimateGuard.isBadSwing(entity, packet.getAction())) {
			AnimateGuard.reportDropped(entity, packet.getAction());
			// Vanilla's `if (entity != null)` check then skips the packet instead of hitting the LivingEntity cast.
			return null;
		}

		return entity;
	}
}
