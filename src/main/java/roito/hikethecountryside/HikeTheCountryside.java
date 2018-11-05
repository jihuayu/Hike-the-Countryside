package roito.hikethecountryside;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import roito.hikethecountryside.common.CommonProxy;

@Mod(modid = HikeTheCountryside.MODID, name = HikeTheCountryside.NAME, version = HikeTheCountryside.VERSION)
public class HikeTheCountryside
{
	public static final String MODID = "hikethecountryside";
	public static final String NAME = "Hike the Countryside";
	public static final String VERSION = "0.0.1";

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
