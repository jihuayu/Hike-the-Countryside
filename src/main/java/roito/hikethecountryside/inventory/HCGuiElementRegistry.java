package roito.hikethecountryside.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import roito.hikethecountryside.HikeTheCountryside;
import roito.hikethecountryside.client.gui.GuiContainerFlatBasket;

import javax.annotation.Nullable;

public class HCGuiElementRegistry implements IGuiHandler
{
	public static final int GUI_FLAT_BASKET = 0;

	public HCGuiElementRegistry()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(HikeTheCountryside.instance, this);
	}

	@Nullable
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (ID)
		{
			case GUI_FLAT_BASKET:
				return new ContainerFlatBasket(player, world.getTileEntity(new BlockPos(x, y, z)));
			default:
				return null;
		}
	}

	@Nullable
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (ID)
		{
			case GUI_FLAT_BASKET:
				return new GuiContainerFlatBasket(new ContainerFlatBasket(player, world.getTileEntity(new BlockPos(x, y, z))));
			default:
				return null;
		}
	}
}
