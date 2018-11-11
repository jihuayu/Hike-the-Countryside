package roito.hikethecountryside.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class HCCreativeTabsRegistry
{
	public static CreativeTabs tabCraft;

	public HCCreativeTabsRegistry()
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
