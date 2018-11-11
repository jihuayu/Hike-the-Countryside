package roito.hikethecountryside.common;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import roito.hikethecountryside.HikeTheCountryside;

public class HCEventRegistry
{
	public HCEventRegistry()
	{
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (event.getModID().equals(HikeTheCountryside.MODID))
		{
			ConfigManager.sync(HikeTheCountryside.MODID, Config.Type.INSTANCE);
		}
	}
}
