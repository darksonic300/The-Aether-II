package com.gildedgames.aether.common.dialog.data.actions;

import com.gildedgames.aether.api.dialog.IDialogAction;
import com.gildedgames.aether.api.dialog.IDialogController;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;

import java.lang.reflect.Type;

public class DialogActionTravelToLastOutpost implements IDialogAction
{
	private DialogActionTravelToLastOutpost()
	{
	}

	@Override
	public void performAction(IDialogController controller)
	{
		if (controller.getDialogPlayer().world.isRemote)
		{
			PlayerAether playerAether = PlayerAether.getPlayer(controller.getDialogPlayer());

			playerAether.getDialogController().closeScene(true);

			return;
		}

		EntityPlayerMP player = (EntityPlayerMP) controller.getDialogPlayer();
		PlayerAether playerAether = PlayerAether.getPlayer(player);

		BlockPos p = playerAether.getProgressModule().getBeforeReturnToBed();

		player.connection.setPlayerLocation(p.getX(), p.getY(), p.getZ(), 0, 0);

		playerAether.getProgressModule().setBeforeReturnToBed(null);
		playerAether.getProgressModule().setHasReturnedToBed(false);
	}

	public static class Deserializer implements JsonDeserializer<DialogActionTravelToLastOutpost>
	{
		@Override
		public DialogActionTravelToLastOutpost deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			return new DialogActionTravelToLastOutpost();
		}
	}
}
