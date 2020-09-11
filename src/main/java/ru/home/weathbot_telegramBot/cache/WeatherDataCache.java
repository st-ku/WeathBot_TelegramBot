package ru.home.weathbot_telegramBot.cache;

import ru.home.weathbot_telegramBot.model.weatherModel.FullWeather;

import java.util.ArrayList;

public class WeatherDataCache {
    private ArrayList<FullWeather> fullWeatherList = new ArrayList<>();
    void putToWeatherList(FullWeather fullWeather) {
        this.fullWeatherList.add(fullWeather);
    }
    FullWeather getFromWeatherList(String city) {
        for (FullWeather fullWeather:fullWeatherList
             ) {
            if (fullWeather.getName().equals(city)) {
                return fullWeather;
            }
        }
    return new FullWeather();
    }

}
