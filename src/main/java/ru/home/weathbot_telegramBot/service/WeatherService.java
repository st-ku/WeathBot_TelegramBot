package ru.home.weathbot_telegramBot.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.home.weathbot_telegramBot.model.weatherModel.FullWeather;

import java.util.Arrays;


@Service
public class WeatherService {

    public String getWeather(String city) {
        return formatWeather(createWeatherObj(city));
    }

    public FullWeather createWeatherObj(String city) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://api.openweathermap.org/data/2.5/weather?q="+ city +"&appid=62905a4863e63f8b340c753a854b5c38&units=metric&lang=ru", FullWeather.class);

    }

    public String formatWeather(FullWeather fullWeather) {
        String result = String.format("Сейчас в %s %s градусов (ощущается как %s) , %s",fullWeather.getName(),fullWeather.getMain().getTemp(),fullWeather.getMain().getFeels_like(), Arrays.stream(fullWeather.getWeather()).iterator().next().getDescription());
        return result ;
    }
}
