package roito.hikethecountryside.common;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import roito.hikethecountryside.inventory.HCGuiElementRegistry;

public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent event)
	{
		new HCTileEntityRegistry();
	}

	public void init(FMLInitializationEvent event)
	{
		new HCOreDictionaryRegistry();
		new HCRecipeRegistry();
		new HCGuiElementRegistry();
		new HCEventRegistry();
	}

	public void postInit(FMLPostInitializationEvent event)
	{

	}
}
