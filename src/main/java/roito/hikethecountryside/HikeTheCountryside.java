package roito.hikethecountryside;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import roito.hikethecountryside.common.CommonProxy;
import roito.hikethecountryside.common.HCCreativeTabs;

@Mod(modid = HikeTheCountryside.MODID,
		name = HikeTheCountryside.NAME,
		version = HikeTheCountryside.VERSION,
		acceptedMinecraftVersions = "[1.12.2,1.13)",
		dependencies = "required-after:forge@[14.23.5.2768,);" +
				"required-after:kiwi@[0.5,0.7);" +
				"after:jei@[4.12.0.215,);")

public class HikeTheCountryside
{
	public static final String MODID = "hikethecountryside";
	public static final String NAME = "Hike the Countryside";
	public static final String VERSION = "@version@";

	public static final CreativeTabs TAB_CRAFT = new HCCreativeTabs();

	@Mod.Instance(HikeTheCountryside.MODID)
	public static HikeTheCountryside instance;

	@SidedProxy(clientSide = "roito.hikethecountryside.client.ClientProxy",
			serverSide = "roito.hikethecountryside.common.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}
}
