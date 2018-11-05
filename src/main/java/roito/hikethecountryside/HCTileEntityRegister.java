package roito.hikethecountryside;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class HCTileEntityRegister
{
	public HCTileEntityRegister(FMLPreInitializationEvent event)
	{

	}

	public void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id)
	{
		GameRegistry.registerTileEntity(tileEntityClass, new ResourceLocation(HikeTheCountryside.MODID, id));
	}
}
