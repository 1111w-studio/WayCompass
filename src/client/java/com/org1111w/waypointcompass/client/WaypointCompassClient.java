package com.org1111w.waypointcompass.client;

import com.org1111w.waypointcompass.WaypointCompass;
import com.org1111w.waypointcompass.data.Waypoint;
import com.org1111w.waypointcompass.data.WaypointData;
import com.org1111w.waypointcompass.item.WaypointCompassItem;
import com.org1111w.waypointcompass.registry.ModComponents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class WaypointCompassClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		HudElementRegistry.addLast(
			WaypointCompass.id("waypoint_hud"),
			(extractor, deltaTracker) -> {
				if (!com.org1111w.waypointcompass.config.ModConfig.INSTANCE.showHudOverlay) {
					return;
				}

				Minecraft client = Minecraft.getInstance();
				if (client.player == null || client.level == null) {
					return;
				}

				ItemStack mainHand = client.player.getMainHandItem();
				ItemStack offHand = client.player.getOffhandItem();
				ItemStack compass = (mainHand.getItem() instanceof WaypointCompassItem) ? mainHand :
				                    (offHand.getItem() instanceof WaypointCompassItem) ? offHand : null;

				if (compass != null) {
					WaypointData data = compass.getOrDefault(ModComponents.WAYPOINTS, WaypointData.DEFAULT);
					Waypoint active = data.getActiveWaypoint();

					if (active != null) {
						String currentDim = client.level.dimension().identifier().toString();
						Component text;

						if (!active.dimension().equals(currentDim)) {
							text = Component.literal("🧭 [" + active.name() + "] In another dimension")
								.withStyle(ChatFormatting.GRAY);
						} else {
							double dist = active.distanceTo(client.player.getX(), client.player.getZ());
							text = Component.literal("🧭 ")
								.append(Component.literal("[" + active.name() + "] ").withStyle(ChatFormatting.AQUA))
								.append(Component.literal(String.format("%.0fm", dist)).withStyle(ChatFormatting.GREEN));
						}

						int screenWidth = client.getWindow().getGuiScaledWidth();
						int screenHeight = client.getWindow().getGuiScaledHeight();
						int x = screenWidth / 2;
						int y = screenHeight - 68; // Positioned right above hotbar & health

						extractor.centeredText(client.font, text, x, y, 0xFFFFFF);
					}
				}
			}
		);
	}
}
