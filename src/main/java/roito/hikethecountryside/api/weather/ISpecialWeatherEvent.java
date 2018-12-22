package roito.hikethecountryside.api.weather;

public interface ISpecialWeatherEvent
{
	int getStartTick();

	int getStartDay();

	int getEndTick();

	int getEndDay();

	int getLastingTicks();

	WeatherType getWeatherType();
}
