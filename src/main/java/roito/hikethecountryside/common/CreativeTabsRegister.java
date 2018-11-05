package roito.hikethecountryside.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CreativeTabsRegister
{
	public static CreativeTabs tabCraft;

	public CreativeTabsRegister(FMLPreInitializationEvent event)
	{
		tabCraft = new CreativeTabs("tabCraft")
		{
			@Override
			public ItemStack createIcon()
			{
				return ItemStack.EMPTY;
			}
		};
	}
}
