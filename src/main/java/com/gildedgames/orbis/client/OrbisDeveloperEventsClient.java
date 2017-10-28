package com.gildedgames.orbis.client;

import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.client.gui.GuiChoiceMenuHolder;
import com.gildedgames.orbis.client.gui.GuiChoiceMenuPowers;
import com.gildedgames.orbis.client.gui.GuiChoiceMenuSelectionTypes;
import com.gildedgames.orbis.client.gui.GuiRightClickBlueprint;
import com.gildedgames.orbis.client.renderers.AirSelectionRenderer;
import com.gildedgames.orbis.common.network.packets.PacketOrbisDeveloperReach;
import com.gildedgames.orbis.common.network.packets.PacketOrbisOpenGui;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.PlayerSelectionModule;
import com.gildedgames.orbis.common.player.godmode.IShapeSelector;
import com.gildedgames.orbis.common.util.OrbisRaytraceHelp;
import com.gildedgames.orbis.common.util.RaytraceHelp;
import com.gildedgames.orbis.common.world_objects.Blueprint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class OrbisDeveloperEventsClient
{

	private static final Minecraft mc = Minecraft.getMinecraft();

	private static double prevReach;

	private static IShape prevShape;

	private static GuiChoiceMenuHolder choiceMenuHolder;

	@SubscribeEvent
	public static void onGuiOpen(final GuiOpenEvent event)
	{
		if (event.getGui() instanceof GuiInventory)
		{
			final Minecraft mc = Minecraft.getMinecraft();

			final PlayerOrbisModule module = PlayerOrbisModule.get(mc.player);

			if (module.powers().getCurrentPower().hasCustomGui())
			{
				module.powers().getCurrentPower().onOpenGui(mc.player);
				NetworkingAether.sendPacketToServer(new PacketOrbisOpenGui());

				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void onClienTick(final TickEvent.ClientTickEvent event)
	{
		if (mc.world != null && mc.player != null)
		{
			final PlayerOrbisModule module = PlayerOrbisModule.get(mc.player);

			if (module.inDeveloperMode())
			{
				final GuiScreen current = Minecraft.getMinecraft().currentScreen;

				if (Keyboard.isKeyDown(OrbisKeyBindings.keyBindFindPower.getKeyCode()))
				{
					if (current == null)
					{
						final GuiChoiceMenuHolder choiceMenuHolder = new GuiChoiceMenuHolder(new GuiChoiceMenuPowers(module),
								new GuiChoiceMenuSelectionTypes(module));

						Minecraft.getMinecraft().displayGuiScreen(choiceMenuHolder);
					}
				}
				else if (current instanceof GuiChoiceMenuHolder)
				{
					final GuiChoiceMenuHolder menu = (GuiChoiceMenuHolder) current;

					if (menu.getCurrentMenu().getHoveredChoice() != null)
					{
						menu.getCurrentMenu().getHoveredChoice().onSelect(module);
					}

					Minecraft.getMinecraft().displayGuiScreen(null);
				}
			}

			final PlayerAether player = PlayerAether.getPlayer(mc.player);

			if (player.getSelectionModule().getActiveSelection() == null && prevShape != null && prevReach != 0.0D)
			{
				prevShape = null;
				player.getOrbisModule().setDeveloperReach(prevReach);

				NetworkingAether.sendPacketToServer(new PacketOrbisDeveloperReach(prevReach));
			}
		}
	}

	@SubscribeEvent
	public static void onMouseEvent(final MouseEvent event)
	{
		final PlayerAether player = PlayerAether.getPlayer(Minecraft.getMinecraft().player);
		final PlayerOrbisModule module = player.getOrbisModule();

		final IShapeSelector selector = module.powers().getCurrentPower().getShapeSelector();

		if (event.getButton() == 0 || event.getButton() == 1)
		{
			if (module.inDeveloperMode() && selector.isSelectorActive(module, mc.world))
			{
				event.setCanceled(true);

				final BlockPos pos = OrbisRaytraceHelp.raytraceNoSnapping(player.getEntity());

				final PlayerSelectionModule selectionModule = player.getSelectionModule();

				final IShape selectedShape = player.getOrbisModule().getSelectedRegion();

				final EntityPlayer entity = player.getEntity();

				final int x = MathHelper.floor(entity.posX);
				final int y = MathHelper.floor(entity.posY);
				final int z = MathHelper.floor(entity.posZ);

				if (selectedShape instanceof Blueprint && module.powers().getCurrentPower() == module.powers().getBlueprintPower())
				{
					final boolean playerInside = selectedShape.contains(x, y, z) || selectedShape.contains(x, MathHelper.floor(entity.posY + entity.height), z);

					if (player.getEntity().getEntityWorld().isRemote && !playerInside)
					{
						if (System.currentTimeMillis() - GuiRightClickBlueprint.lastCloseTime > 200)
						{
							Minecraft.getMinecraft().displayGuiScreen(new GuiRightClickBlueprint((Blueprint) selectedShape));
						}
					}
				}
				else
				{
					if (!event.isButtonstate() && selectionModule.getActiveSelection() != null
							|| event.isButtonstate() && selectionModule.getActiveSelection() == null)
					{
						selectionModule.setActiveSelectionCorner(pos);
					}
				}
			}
		}

		final IShape activeRegion = player.getSelectionModule().getActiveSelection();

		//Change reach
		if (OrbisKeyBindings.keyBindControl.isKeyDown() || activeRegion != null)
		{
			if (activeRegion != null && prevShape == null)
			{
				prevShape = activeRegion;

				prevReach = player.getOrbisModule().getDeveloperReach();
			}

			if (OrbisKeyBindings.keyBindControl.isKeyDown())
			{
				prevReach = player.getOrbisModule().getDeveloperReach();
			}

			final RayTraceResult blockRaytrace = RaytraceHelp
					.rayTraceNoBlocks(player.getOrbisModule().getReach(), AirSelectionRenderer.PARTIAL_TICKS, player.getEntity());
			double reach = player.getOrbisModule().getReach();

			if (event.getDwheel() > 0)
			{
				player.getOrbisModule().setDeveloperReach(reach + 1);
				NetworkingAether.sendPacketToServer(new PacketOrbisDeveloperReach(reach + 1));

				event.setCanceled(true);
			}
			else if (event.getDwheel() < 0)
			{
				if (blockRaytrace != null)
				{
					final int x = MathHelper.floor(player.getEntity().posX);
					final int y = MathHelper.floor(player.getEntity().posY);
					final int z = MathHelper.floor(player.getEntity().posZ);

					final double deltaX = x - blockRaytrace.hitVec.xCoord;
					final double deltaY = y - blockRaytrace.hitVec.yCoord;
					final double deltaZ = z - blockRaytrace.hitVec.zCoord;

					final float distance = MathHelper.floor((float) Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ));

					player.getOrbisModule().setDeveloperReach(distance);
					NetworkingAether.sendPacketToServer(new PacketOrbisDeveloperReach(distance));

					reach = player.getOrbisModule().getReach();
				}

				player.getOrbisModule().setDeveloperReach(reach - 1);
				NetworkingAether.sendPacketToServer(new PacketOrbisDeveloperReach(reach - 1));

				event.setCanceled(true);
			}
		}
	}

}