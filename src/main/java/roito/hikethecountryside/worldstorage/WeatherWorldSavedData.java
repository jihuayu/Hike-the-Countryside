package roito.hikethecountryside.worldstorage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import roito.hikethecountryside.api.weather.WeatherEvent;
import roito.hikethecountryside.api.weather.WeatherType;

import java.util.ArrayList;
import java.util.List;

public class WeatherWorldSavedData extends WorldSavedData
{
	public List<WeatherEvent> weatherList = new ArrayList<WeatherEvent>(20);

	public WeatherWorldSavedData()
	{
		super("HCWeatherList");
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		weatherList.clear();
		int length = nbt.getInteger("ListLength");
		for (int i = 0; i < length; i++)
		{
			weatherList.add(new WeatherEvent(nbt.getInteger("WeatherListStartTick" + i), nbt.getInteger("WeatherListStartTick" + i), nbt.getInteger("WeatherListStartTick" + i), WeatherType.valueOf(nbt.getString("WeatherListType"))));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setInteger("ListLength", weatherList.size());
		for (int i = 0; i < weatherList.size(); i++)
		{
			compound.setInteger("WeatherListStartTick" + i, weatherList.get(i).getStartTick());
			compound.setInteger("WeatherListStartDay" + i, weatherList.get(i).getStartDay());
			compound.setInteger("WeatherListLastingTicks" + i, weatherList.get(i).getLastingTicks());
			compound.setString("WeatherListType" + i, weatherList.get(i).getWeatherType().toString());
		}
		return compound;
	}

	public boolean addWeatherEvent(WeatherEvent weatherEventIn, int currentDay, int currentTick)
	{
		if (weatherList.size() >= 20)
		{
			return false;
		}

		if (!weatherList.isEmpty())
		{
			WeatherEvent lastEvent = weatherList.get(weatherList.size() - 1);
			if (lastEvent.getWeatherType().equals(weatherEventIn.getWeatherType()))
			{
				weatherList.get(weatherList.size() - 1).addLastingTicks(weatherEventIn.getLastingTicks());
			}
			else
			{
				weatherList.add(weatherEventIn.updateInfo(lastEvent.getEndDay(), lastEvent.getEndTick()));
			}
			this.markDirty();
			return true;
		}
		else
		{
			weatherList.add(weatherEventIn.updateInfo(currentDay, currentTick));
			this.markDirty();
			return true;
		}
	}

	public static WeatherWorldSavedData getData(World world)
	{
		WorldSavedData data = world.getMapStorage().getOrLoadData(WeatherWorldSavedData.class, "HCWeatherList");
		if (data == null)
		{
			data = new WeatherWorldSavedData();
			world.getMapStorage().setData("HCWeatherList", data);
		}
		return (WeatherWorldSavedData) data;
	}
}
