package roito.hikethecountryside.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import roito.hikethecountryside.HikeTheCountryside;

public class HCCreativeTabs extends CreativeTabs
{
	public HCCreativeTabs()
	{
		super(HikeTheCountryside.MODID + "_craft");
	}

	@Override
	public ItemStack createIcon()
	{
		return new ItemStack(HCBlocksItemsRegistry.BLOCK_FLAT_BASKET);
	}
}
