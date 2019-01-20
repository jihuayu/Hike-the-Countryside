package roito.hikethecountryside.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import roito.hikethecountryside.HikeTheCountryside;
import roito.hikethecountryside.event.WorldWeatherHandler;
import roito.hikethecountryside.worldstorage.WeatherWorldSavedData;
import snownee.kiwi.item.ItemMod;

public class ItemWeatherForecast extends ItemMod
{
	public ItemWeatherForecast()
	{
		super("weather_forecast");
		this.setCreativeTab(HikeTheCountryside.TAB_CRAFT);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote)
		{
			int currentTick = (int)worldIn.getWorldTime();
			int currentDay = (int)(worldIn.provider.getWorldTime() / 24000L % 2147483647L);
			WeatherWorldSavedData data;
			data = WeatherWorldSavedData.getData(worldIn);
			if (currentTick <= 3000)
			{
				for (int i = 0; i < data.weatherList.size(); i++)
				{
					if (WorldWeatherHandler.isInTime(data.weatherList.get(i), currentDay, 3000))
					{
						playerIn.sendMessage(new TextComponentString("Morning: " + data.weatherList.get(i).getWeatherType().toString()));
						break;
					}
				}
			}

			if (currentTick <= 9000)
			{
				for (int i = 0; i < data.weatherList.size(); i++)
				{
					if (WorldWeatherHandler.isInTime(data.weatherList.get(i), currentDay, 9000))
					{
						playerIn.sendMessage(new TextComponentString("Afternoon: " + data.weatherList.get(i).getWeatherType().toString()));
						break;
					}
				}
			}

			if (currentTick <= 15000)
			{
				for (int i = 0; i < data.weatherList.size(); i++)
				{
					if (WorldWeatherHandler.isInTime(data.weatherList.get(i), currentDay, 15000))
					{
						playerIn.sendMessage(new TextComponentString("Night: " + data.weatherList.get(i).getWeatherType().toString()));
						break;
					}
				}
			}
		}

		return EnumActionResult.SUCCESS;
	}
}
