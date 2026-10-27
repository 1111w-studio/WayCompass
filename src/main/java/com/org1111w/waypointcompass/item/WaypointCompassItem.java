package com.org1111w.waypointcompass.item;

import com.org1111w.waypointcompass.data.Waypoint;
import com.org1111w.waypointcompass.data.WaypointData;
import com.org1111w.waypointcompass.registry.ModComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class WaypointCompassItem extends Item {
	public static final int MAX_WAYPOINTS = 10;

	public WaypointCompassItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		if (!level.isClientSide()) {
			WaypointData data = stack.getOrDefault(ModComponents.WAYPOINTS, WaypointData.DEFAULT);

			if (player.isShiftKeyDown()) {
				// Shift + Right-Click: Cycle to the next waypoint
				if (data.waypoints().isEmpty()) {
					player.sendOverlayMessage(
						Component.literal("No waypoints saved yet! Right-click to save this location.")
							.withStyle(ChatFormatting.YELLOW)
					);
				} else {
					WaypointData cycled = data.cycleNext();
					stack.set(ModComponents.WAYPOINTS, cycled);

					Waypoint active = cycled.getActiveWaypoint();
					level.playSound(null, player.blockPosition(), SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.PLAYERS, 0.8F, 1.2F);
					player.sendOverlayMessage(
						Component.literal("🧭 Active Waypoint: ")
							.withStyle(ChatFormatting.AQUA)
							.append(Component.literal("[" + active.name() + "]").withStyle(ChatFormatting.GREEN))
							.append(Component.literal(" (" + active.pos().getX() + ", " + active.pos().getY() + ", " + active.pos().getZ() + ")").withStyle(ChatFormatting.GRAY))
					);
				}
			} else {
				// Normal Right-Click: Save current location as a new waypoint
				BlockPos currentPos = player.blockPosition();
				String dimension = level.dimension().identifier().toString();
				int nextNum = data.waypoints().size() + 1;
				String name = "Waypoint #" + nextNum;

				Waypoint newWaypoint = new Waypoint(name, currentPos, dimension, 0x55FF55);
				WaypointData updated = data.addWaypoint(newWaypoint, com.org1111w.waypointcompass.config.ModConfig.INSTANCE.maxWaypoints);
				stack.set(ModComponents.WAYPOINTS, updated);

				level.playSound(null, currentPos, SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 1.0F, 1.1F);
				player.sendOverlayMessage(
					Component.literal("📍 Saved ")
						.withStyle(ChatFormatting.GOLD)
						.append(Component.literal("[" + name + "]").withStyle(ChatFormatting.GREEN))
						.append(Component.literal(" at " + currentPos.getX() + ", " + currentPos.getY() + ", " + currentPos.getZ()).withStyle(ChatFormatting.WHITE))
				);
			}
		}

		return InteractionResult.SUCCESS;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltipConsumer, TooltipFlag flag) {
		WaypointData data = stack.getOrDefault(ModComponents.WAYPOINTS, WaypointData.DEFAULT);
		Waypoint active = data.getActiveWaypoint();

		if (active != null) {
			tooltipConsumer.accept(
				Component.literal("Tracking: ")
					.withStyle(ChatFormatting.GRAY)
					.append(Component.literal(active.name()).withStyle(ChatFormatting.AQUA))
			);
			tooltipConsumer.accept(
				Component.literal("Pos: " + active.pos().getX() + ", " + active.pos().getY() + ", " + active.pos().getZ())
					.withStyle(ChatFormatting.DARK_GRAY)
			);
			tooltipConsumer.accept(
				Component.literal("Waypoints: " + (data.selectedIndex() + 1) + "/" + data.waypoints().size())
					.withStyle(ChatFormatting.DARK_GREEN)
			);
		} else {
			tooltipConsumer.accept(Component.literal("No active waypoint").withStyle(ChatFormatting.DARK_GRAY));
		}

		tooltipConsumer.accept(Component.literal(""));
		tooltipConsumer.accept(Component.literal("• Right-Click: Save location").withStyle(ChatFormatting.YELLOW));
		tooltipConsumer.accept(Component.literal("• Shift + Right-Click: Cycle waypoints").withStyle(ChatFormatting.GOLD));
	}
}
