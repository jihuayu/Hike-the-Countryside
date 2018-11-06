package roito.hikethecountryside.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class HCCreativeTabsRegister
{
	public static CreativeTabs tabCraft;

	public HCCreativeTabsRegister()
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
