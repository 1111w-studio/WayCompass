package com.org1111w.waypointcompass.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.List;

public record WaypointData(List<Waypoint> waypoints, int selectedIndex) {
	public static final WaypointData DEFAULT = new WaypointData(List.of(), 0);

	public static final Codec<WaypointData> CODEC = RecordCodecBuilder.create(instance ->
		instance.group(
			Waypoint.CODEC.listOf().fieldOf("waypoints").forGetter(WaypointData::waypoints),
			Codec.INT.optionalFieldOf("selected_index", 0).forGetter(WaypointData::selectedIndex)
		).apply(instance, WaypointData::new)
	);

	public static final StreamCodec<ByteBuf, WaypointData> STREAM_CODEC = StreamCodec.composite(
		Waypoint.STREAM_CODEC.apply(ByteBufCodecs.list()), WaypointData::waypoints,
		ByteBufCodecs.INT, WaypointData::selectedIndex,
		WaypointData::new
	);

	public Waypoint getActiveWaypoint() {
		if (waypoints.isEmpty()) {
			return null;
		}
		int idx = Math.clamp(selectedIndex, 0, waypoints.size() - 1);
		return waypoints.get(idx);
	}

	public WaypointData cycleNext() {
		if (waypoints.isEmpty()) {
			return this;
		}
		int nextIndex = (selectedIndex + 1) % waypoints.size();
		return new WaypointData(waypoints, nextIndex);
	}

	public WaypointData addWaypoint(Waypoint newWaypoint, int maxWaypoints) {
		List<Waypoint> list = new ArrayList<>(waypoints);
		if (list.size() >= maxWaypoints) {
			list.remove(0);
		}
		list.add(newWaypoint);
		return new WaypointData(List.copyOf(list), list.size() - 1);
	}
}
