package roito.hikethecountryside.common;

import net.minecraftforge.oredict.OreDictionary;

public class HCOreDictionaryRegister
{
    public HCOreDictionaryRegister()
    {
        OreDictionary.registerOre("foodJerky", HCBlocksItemsRegistry.BEEF_JERKY);
        OreDictionary.registerOre("foodJerky", HCBlocksItemsRegistry.CHICKEN_JERKY);
	    OreDictionary.registerOre("foodJerky", HCBlocksItemsRegistry.MUTTON_JERKY);
	    OreDictionary.registerOre("foodJerky", HCBlocksItemsRegistry.PORK_JERKY);
	    OreDictionary.registerOre("foodJerky", HCBlocksItemsRegistry.RABBIT_JERKY);
    }
}
