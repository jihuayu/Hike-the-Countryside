package roito.hikethecountryside.api.weather;

public class SpecialWeatherEvent implements ISpecialWeatherEvent
{
	protected int startTick;

	protected int startDay;

	protected int endTick;

	protected int endDay;

	protected int lastingTicks;

	protected WeatherType weatherType;

	public SpecialWeatherEvent(int startDay, int startTick, int lastingTicks, WeatherType type)
	{
		this.startDay = startDay;
		this.startTick = startTick;
		this.endDay = startDay + (startTick + lastingTicks) / 24000;
		this.endTick = (startTick + lastingTicks) % 24000;
		this.lastingTicks = lastingTicks;
		this.weatherType = type;
	}

	@Override
	public int getStartTick()
	{
		return startTick;
	}

	@Override
	public int getStartDay()
	{
		return startDay;
	}

	@Override
	public int getEndTick()
	{
		return endTick;
	}

	@Override
	public int getEndDay()
	{
		return endDay;
	}

	@Override
	public int getLastingTicks()
	{
		return lastingTicks;
	}

	@Override
	public WeatherType getWeatherType()
	{
		return weatherType;
	}
}
