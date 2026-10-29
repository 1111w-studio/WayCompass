package com.org1111w.waypointcompass.registry;

import com.org1111w.waypointcompass.WaypointCompass;
import com.org1111w.waypointcompass.item.WaypointCompassItem;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

public class ModItems {
	public static final ResourceKey<Item> WAYPOINT_COMPASS_KEY = ResourceKey.create(
		Registries.ITEM,
		WaypointCompass.id("waypoint_compass")
	);

	public static final Item WAYPOINT_COMPASS = Registry.register(
		BuiltInRegistries.ITEM,
		WAYPOINT_COMPASS_KEY,
		new WaypointCompassItem(new Item.Properties().setId(WAYPOINT_COMPASS_KEY).stacksTo(1))
	);

	public static void init() {
		WaypointCompass.LOGGER.info("Registered Items for {}", WaypointCompass.MOD_ID);

		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(output -> {
			output.accept(WAYPOINT_COMPASS);
		});
	}
}
