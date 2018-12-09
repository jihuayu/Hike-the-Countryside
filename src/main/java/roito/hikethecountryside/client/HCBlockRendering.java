package roito.hikethecountryside.client;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import roito.hikethecountryside.HikeTheCountryside;
import roito.hikethecountryside.client.renderer.TESRFlatBasket;
import roito.hikethecountryside.client.renderer.TESRStove;
import roito.hikethecountryside.tileentity.TileEntityFlatBasket;
import roito.hikethecountryside.tileentity.TileEntityStove;

@Mod.EventBusSubscriber(modid = HikeTheCountryside.MODID, value = Side.CLIENT)
public final class HCBlockRendering
{
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		OBJLoader.INSTANCE.addDomain(HikeTheCountryside.MODID);

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFlatBasket.class, new TESRFlatBasket());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStove.class, new TESRStove());
	}
}
