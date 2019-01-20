package roito.hikethecountryside.event;

import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import roito.hikethecountryside.api.weather.WeatherEvent;
import roito.hikethecountryside.api.weather.WeatherType;
import roito.hikethecountryside.worldstorage.WeatherWorldSavedData;

public class WorldWeatherHandler
{
	@SubscribeEvent
	public void onWorldUpdate(TickEvent.WorldTickEvent event) throws InterruptedException
	{
		if (!event.world.isRemote && event.world.provider.getDimensionType() == DimensionType.OVERWORLD)
		{
			WeatherWorldSavedData data;
			data = WeatherWorldSavedData.getData(event.world);
			int currentTick = (int)event.world.getWorldTime();
			int currentDay = (int)(event.world.provider.getWorldTime() / 24000L % 2147483647L);

			while (!data.weatherList.isEmpty())
			{
				if (WorldWeatherHandler.isInTime(data.weatherList.get(0), currentDay, currentTick))
				{
					break;
				}
				else
				{
					data.weatherList.remove(0);
				}
			}

			while (data.weatherList.size() < 10)
			{
				WeatherEvent weatherEvent = produceNewWeatherEvent(event.world);
				if (weatherEvent.getWeatherType().equals(WeatherType.RAINY))
				{
					data.addWeatherEvent(new WeatherEvent(3000, WeatherType.OVERCAST), currentDay, currentTick);
				}
				data.addWeatherEvent(produceNewWeatherEvent(event.world), currentDay, currentTick);
			}

			WorldInfo worldinfo = event.world.getWorldInfo();
			int i = data.weatherList.get(0).getLastingTicks();

			switch(data.weatherList.get(0).getWeatherType())
			{
				case SUNNY:
					if (event.world.isRaining())
					{
						worldinfo.setCleanWeatherTime(i);
						worldinfo.setRainTime(0);
						worldinfo.setThunderTime(0);
						worldinfo.setRaining(false);
						worldinfo.setThundering(false);
					}
					return;
				case RAINY:
					if (!event.world.isRaining())
					{
						worldinfo.setCleanWeatherTime(0);
						worldinfo.setRainTime(i);
						worldinfo.setThunderTime(i);
						worldinfo.setRaining(true);
						worldinfo.setThundering(false);
					}
					if (!Biome.getBiome(4).enableRain)
					{
						for(int j = 0; j < Biome.MUTATION_TO_BASE_ID_MAP.size(); j++)
						{
							if(Biome.getBiome(j) != null && Biome.getBiome(j).getRainfall() != 0.0F && Biome.getBiome(j).getBiomeName() != "sky" && Biome.getBiome(j).getBiomeName() != "void")
							{
								Biome.getBiome(j).enableRain = true;
							}
						}
					}
					return;
				case THUNDERSTORM:
					if (!event.world.isThundering())
					{
						worldinfo.setCleanWeatherTime(0);
						worldinfo.setRainTime(i);
						worldinfo.setThunderTime(i);
						worldinfo.setRaining(true);
						worldinfo.setThundering(true);
					}
					return;
				case OVERCAST:
					if (!event.world.isRaining())
					{
						worldinfo.setCleanWeatherTime(0);
						worldinfo.setRainTime(i);
						worldinfo.setThunderTime(i);
						worldinfo.setRaining(true);
						worldinfo.setThundering(false);
					}
					if (Biome.getBiome(4).enableRain)
					{
						for(int j = 0; j < Biome.MUTATION_TO_BASE_ID_MAP.size(); j++)
						{
							if(Biome.getBiome(j) != null)
							{
								Biome.getBiome(j).enableRain = false;
							}
						}
					}

			}
		}
	}

	public WeatherEvent produceNewWeatherEvent(World world)
	{
		Float chance = world.rand.nextFloat();
		WeatherType type = chance < 0.02F ? WeatherType.THUNDERSTORM : chance < 0.2F ? WeatherType.RAINY : chance < 0.5F ? WeatherType.OVERCAST : WeatherType.SUNNY;
		return new WeatherEvent(6000 * (world.rand.nextInt(4) + 1), type);
	}

	public static boolean isInTime(WeatherEvent weatherEvent, int currentDay, int currentTick)
	{
		if (weatherEvent.getStartDay() <= currentDay && currentDay <= weatherEvent.getEndDay())
		{
			return true;
		}
		boolean flag = false;
		if (weatherEvent.getStartDay() == currentDay)
		{
			flag = currentTick <= weatherEvent.getStartTick();
		}
		if (weatherEvent.getEndDay() == currentDay)
		{
			flag = currentTick > weatherEvent.getEndTick();
		}
		return flag;
	}
}
