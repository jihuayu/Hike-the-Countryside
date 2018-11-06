package roito.hikethecountryside.common;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import roito.hikethecountryside.api.recipe.HCRecipeRegister;
import roito.hikethecountryside.inventory.HCGuiElementRegister;

public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent event)
	{
		new HCCreativeTabsRegister();
		new HCTileEntityRegister();
	}

	public void init(FMLInitializationEvent event)
	{
		new HCOreDictionaryRegister();
		new HCRecipeRegister();
		new HCGuiElementRegister();
	}

	public void postInit(FMLPostInitializationEvent event)
	{

	}
}
