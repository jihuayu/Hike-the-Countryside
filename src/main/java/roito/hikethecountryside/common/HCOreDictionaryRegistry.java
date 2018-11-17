package roito.hikethecountryside.common;

import net.minecraft.init.Items;
import net.minecraftforge.oredict.OreDictionary;

public class HCOreDictionaryRegistry
{
    public HCOreDictionaryRegistry()
    {
        OreDictionary.registerOre("foodJerky", HCBlocksItemsRegistry.BEEF_JERKY);
        OreDictionary.registerOre("foodJerky", HCBlocksItemsRegistry.CHICKEN_JERKY);
	    OreDictionary.registerOre("foodJerky", HCBlocksItemsRegistry.MUTTON_JERKY);
	    OreDictionary.registerOre("foodJerky", HCBlocksItemsRegistry.PORK_JERKY);
	    OreDictionary.registerOre("foodJerky", HCBlocksItemsRegistry.RABBIT_JERKY);

	    OreDictionary.registerOre("listAllmeatraw", Items.BEEF);
	    OreDictionary.registerOre("listAllmeatraw", Items.CHICKEN);
	    OreDictionary.registerOre("listAllmeatraw", Items.MUTTON);
	    OreDictionary.registerOre("listAllmeatraw", Items.PORKCHOP);
	    OreDictionary.registerOre("listAllmeatraw", Items.RABBIT);
    }
}
