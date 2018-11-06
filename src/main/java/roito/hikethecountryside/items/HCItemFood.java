package roito.hikethecountryside.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import snownee.kiwi.item.IModItem;

public class HCItemFood extends ItemFood implements IModItem
{
	private final String name;

	public HCItemFood(String name, int amount, float saturation, boolean isWolfFood, CreativeTabs tab)
	{
		super(amount, saturation, isWolfFood);
		this.name = name;
		this.setCreativeTab(tab);
	}

	public String getName()
	{
		return this.name;
	}

	public void register(String modid)
	{
		this.setRegistryName(modid, this.getName());
		this.setTranslationKey(modid + "." + this.getName());
	}

	public Item cast()
	{
		return this;
	}
}
