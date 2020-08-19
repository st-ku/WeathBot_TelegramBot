package ru.home.weathbot_telegramBot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import ru.home.weathbot_telegramBot.model.weatherModel.FullWeather;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class WeatherService {
    private ReplyMessagesService messagesService;
  //  @Value("classpath:resources/icons.json")
  //  Resource json;

    public WeatherService(ReplyMessagesService messagesService) {
        this.messagesService = messagesService;
    }

    public String getWeather(String city) {
        return formatWeather(createWeatherObj(city));
    }

    public FullWeather createWeatherObj(String city) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://api.openweathermap.org/data/2.5/weather?q="+ city +"&appid=62905a4863e63f8b340c753a854b5c38&units=metric&lang=ru", FullWeather.class);

    }
    public boolean checkIfCityExists(String city) {
       try {
           createWeatherObj(city);
       }
       catch (Exception e) {
           return false;
       }
       return true;
    }
    @SneakyThrows
    public String formatWeather(FullWeather fullWeather) {
        return String.format(messagesService.getReplyText("reply.weatherPattern1"),fullWeather.getName(),fullWeather.getMain().getTemp(),fullWeather.getMain().getFeels_like(),createEmojiByCode(Arrays.stream(fullWeather.getWeather()).iterator().next().getIcon()), Arrays.stream(fullWeather.getWeather()).iterator().next().getDescription(), fullWeather.getWind().getSpeed());
    }
    @SneakyThrows
    private String createEmojiByCode(String code)  {
        String json= StreamUtils.copyToString( new ClassPathResource("icons.json").getInputStream(), Charset.defaultCharset()  );
        Map<String, String> map = new Gson().fromJson(json, Map.class);
        return map.get(code);
    }
}
