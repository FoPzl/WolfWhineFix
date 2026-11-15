package me.fopzl.wolfwhinefix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public final class AnimateGuard {
	private static final Logger LOGGER = LoggerFactory.getLogger("wolfwhinefix");

	// Only touched from the render thread (after vanilla's thread hand-off), so no synchronization is needed.
	private static final IntSet WARNED_IDS = new IntOpenHashSet();

	private AnimateGuard() {
	}

	// Vanilla casts the entity to LivingEntity for both swing actions, so a non-living entity always crashes.
	public static boolean isBadSwing(final Entity entity, final int action) {
		return entity != null
			&& (action == ClientboundAnimatePacket.SWING_MAIN_HAND || action == ClientboundAnimatePacket.SWING_OFF_HAND)
			&& !(entity instanceof LivingEntity);
	}

	public static void reportDropped(final Entity entity, final int action) {
		if (!WARNED_IDS.add(entity.getId())) {
			return;
		}

		LOGGER.warn(
			"Dropped a swing animation for non-living entity #{} ({}, action {}); further drops for this entity are not logged",
			entity.getId(),
			EntityType.getKey(entity.getType()),
			action
		);
	}
}
