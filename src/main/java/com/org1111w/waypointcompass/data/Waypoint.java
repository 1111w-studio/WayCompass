package com.org1111w.waypointcompass.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record Waypoint(String name, BlockPos pos, String dimension, int color) {

	public static final Codec<Waypoint> CODEC = RecordCodecBuilder.create(instance ->
		instance.group(
			Codec.STRING.fieldOf("name").forGetter(Waypoint::name),
			BlockPos.CODEC.fieldOf("pos").forGetter(Waypoint::pos),
			Codec.STRING.fieldOf("dimension").forGetter(Waypoint::dimension),
			Codec.INT.fieldOf("color").forGetter(Waypoint::color)
		).apply(instance, Waypoint::new)
	);

	public static final StreamCodec<ByteBuf, Waypoint> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.STRING_UTF8, Waypoint::name,
		BlockPos.STREAM_CODEC, Waypoint::pos,
		ByteBufCodecs.STRING_UTF8, Waypoint::dimension,
		ByteBufCodecs.INT, Waypoint::color,
		Waypoint::new
	);

	public double distanceTo(double x, double z) {
		double dx = pos.getX() + 0.5 - x;
		double dz = pos.getZ() + 0.5 - z;
		return Math.sqrt(dx * dx + dz * dz);
	}
}
