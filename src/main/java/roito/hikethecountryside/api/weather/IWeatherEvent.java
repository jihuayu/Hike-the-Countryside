package roito.hikethecountryside.api.weather;

public interface IWeatherEvent
{
	int getStartTick();

	int getStartDay();

	int getEndTick();

	int getEndDay();

	int getLastingTicks();

	void addLastingTicks(int lastingTicks);

	WeatherEvent updateInfo(int startDay, int startTick);

	WeatherType getWeatherType();
}
