package roito.hikethecountryside.common;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import roito.hikethecountryside.HikeTheCountryside;
import roito.hikethecountryside.tileentity.TileEntityFlatBasket;

public class HCTileEntityRegister
{
	public HCTileEntityRegister()
	{
		registerTileEntity(TileEntityFlatBasket.class, "flat_basket");
	}

	public void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id)
	{
		GameRegistry.registerTileEntity(tileEntityClass, new ResourceLocation(HikeTheCountryside.MODID, id));
	}
}
