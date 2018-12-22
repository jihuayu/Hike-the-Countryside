package roito.hikethecountryside.api.weather;

public class WeatherEvent implements IWeatherEvent
{
	protected int startTick = -1;

	protected int startDay = -1;

	protected int endTick = -1;

	protected int endDay = -1;

	protected int lastingTicks;

	protected WeatherType weatherType;

	public WeatherEvent(int lastingTicks, WeatherType type)
	{
		this.lastingTicks = lastingTicks;
		this.weatherType = type;
	}

	public WeatherEvent(int startDay, int startTick, int lastingTicks, WeatherType type)
	{
		this.startDay = startDay;
		this.startTick = startTick;
		this.endDay = startDay + (this.startTick + lastingTicks) / 24000;
		this.endTick = (this.startTick + lastingTicks) % 24000;
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
	public void addLastingTicks(int lastingTicks)
	{
		this.lastingTicks = this.lastingTicks + lastingTicks;
	}

	@Override
	public WeatherEvent updateInfo(int startDay, int startTick)
	{
		this.startDay = startDay;
		this.startTick = startTick;
		this.endDay = startDay + (this.startTick + lastingTicks) / 24000;
		this.endTick = (this.startTick + lastingTicks) % 24000;
		return this;
	}

	@Override
	public WeatherType getWeatherType()
	{
		return weatherType;
	}
}
