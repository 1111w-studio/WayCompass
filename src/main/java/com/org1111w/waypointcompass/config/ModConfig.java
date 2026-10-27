package com.org1111w.waypointcompass.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.org1111w.waypointcompass.WaypointCompass;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModConfig {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "waypoint_compass.json");

	public static ModConfig INSTANCE = new ModConfig();

	public int maxWaypoints = 10;
	public boolean saveDeathLocation = true;
	public boolean showHudOverlay = true;

	public static void load() {
		if (CONFIG_FILE.exists()) {
			try (FileReader reader = new FileReader(CONFIG_FILE)) {
				INSTANCE = GSON.fromJson(reader, ModConfig.class);
				if (INSTANCE == null) {
					INSTANCE = new ModConfig();
				}
				WaypointCompass.LOGGER.info("Loaded configuration for {}", WaypointCompass.MOD_ID);
				return;
			} catch (IOException e) {
				WaypointCompass.LOGGER.error("Failed to load config, using defaults", e);
			}
		}
		save();
	}

	public static void save() {
		try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
			GSON.toJson(INSTANCE, writer);
		} catch (IOException e) {
			WaypointCompass.LOGGER.error("Failed to save config", e);
		}
	}
}
