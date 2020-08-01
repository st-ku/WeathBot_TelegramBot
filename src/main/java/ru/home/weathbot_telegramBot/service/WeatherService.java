package ru.home.weathbot_telegramBot.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.home.weathbot_telegramBot.model.weatherModel.FullWeather;

/**
 * Генерирует предсказание
 *
 * @author Sergei Viacheslaev
 */
@Service
public class WeatherService {
    private ReplyMessagesService messagesService;

    public WeatherService(ReplyMessagesService messagesService) {
        this.messagesService = messagesService;
    }

    public String getWeather(String city) {

        RestTemplate restTemplate = new RestTemplate();
        FullWeather fullWeather = restTemplate.getForObject("http://api.openweathermap.org/data/2.5/weather?q="+ city +"&appid=62905a4863e63f8b340c753a854b5c38&units=metric&lang=ru", FullWeather.class);


       // return messagesService.getReplyText(replyMessagePropertie);
        return fullWeather.toString() ;
    }
}
