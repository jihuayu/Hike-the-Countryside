package roito.hikethecountryside.common;

import roito.hikethecountryside.HikeTheCountryside;
import roito.hikethecountryside.blocks.*;
import roito.hikethecountryside.items.HCItemFood;
import snownee.kiwi.IModule;
import snownee.kiwi.KiwiModule;

@KiwiModule(modid = HikeTheCountryside.MODID)
public class HCBlocksItemsRegistry implements IModule
{
	public static final BlockFlatBasket BLOCK_FLAT_BASKET = new BlockFlatBasket();

	public static final HCItemFood PORK_JERKY = new HCItemFood("pork_jerky", 10, 1.6F, true, HCCreativeTabsRegistry.tabCraft);
	public static final HCItemFood BEEF_JERKY = new HCItemFood("beef_jerky", 10, 1.6F, true, HCCreativeTabsRegistry.tabCraft);
	public static final HCItemFood CHICKEN_JERKY = new HCItemFood("chicken_jerky", 8, 1.2F, true, HCCreativeTabsRegistry.tabCraft);
	public static final HCItemFood RABBIT_JERKY = new HCItemFood("rabbit_jerky", 7, 1.2F, true, HCCreativeTabsRegistry.tabCraft);
	public static final HCItemFood MUTTON_JERKY = new HCItemFood("mutton_jerky", 8, 1.6F, true, HCCreativeTabsRegistry.tabCraft);
	public static final HCItemFood DRIED_CARROT = new HCItemFood("dried_carrot", 5, 1.2F, false, HCCreativeTabsRegistry.tabCraft);
	public static final HCItemFood DRIED_BEETROOT = new HCItemFood("dried_beetroot", 3, 1.2F, false, HCCreativeTabsRegistry.tabCraft);
}
