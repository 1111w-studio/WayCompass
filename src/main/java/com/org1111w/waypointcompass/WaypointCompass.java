package com.org1111w.waypointcompass;

import com.org1111w.waypointcompass.config.ModConfig;
import com.org1111w.waypointcompass.data.Waypoint;
import com.org1111w.waypointcompass.data.WaypointData;
import com.org1111w.waypointcompass.item.WaypointCompassItem;
import com.org1111w.waypointcompass.registry.ModComponents;
import com.org1111w.waypointcompass.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaypointCompass implements ModInitializer {
	public static final String MOD_ID = "waypoint_compass";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing {} for Minecraft 26.3 (Java 25)", MOD_ID);
		ModConfig.load();
		ModComponents.init();
		ModItems.init();

		// Death Location Auto-Save Listener
		ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
			if (!alive && ModConfig.INSTANCE.saveDeathLocation) {
				BlockPos deathPos = oldPlayer.blockPosition();
				String dimension = oldPlayer.level().dimension().identifier().toString();
				Waypoint deathWaypoint = new Waypoint("☠️ Last Death", deathPos, dimension, 0xFF5555);

				boolean added = false;
				for (int i = 0; i < newPlayer.getInventory().getContainerSize(); i++) {
					ItemStack stack = newPlayer.getInventory().getItem(i);
					if (stack.getItem() instanceof WaypointCompassItem) {
						WaypointData data = stack.getOrDefault(ModComponents.WAYPOINTS, WaypointData.DEFAULT);
						stack.set(ModComponents.WAYPOINTS, data.addWaypoint(deathWaypoint, ModConfig.INSTANCE.maxWaypoints));
						added = true;
					}
				}

				if (added) {
					newPlayer.sendSystemMessage(
						Component.literal("☠️ Death location saved to your Waypoint Compass! (")
							.withStyle(ChatFormatting.RED)
							.append(Component.literal(deathPos.getX() + ", " + deathPos.getY() + ", " + deathPos.getZ()).withStyle(ChatFormatting.GOLD))
							.append(Component.literal(")"))
					);
				}
			}
		});
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
