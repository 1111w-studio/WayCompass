package com.org1111w.waypointcompass.registry;

import com.org1111w.waypointcompass.WaypointCompass;
import com.org1111w.waypointcompass.data.WaypointData;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModComponents {
	public static final DataComponentType<WaypointData> WAYPOINTS = Registry.register(
		BuiltInRegistries.DATA_COMPONENT_TYPE,
		WaypointCompass.id("waypoints"),
		DataComponentType.<WaypointData>builder()
			.persistent(WaypointData.CODEC)
			.networkSynchronized(WaypointData.STREAM_CODEC)
			.build()
	);

	public static void init() {
		WaypointCompass.LOGGER.info("Registered Data Components for {}", WaypointCompass.MOD_ID);
	}
}
