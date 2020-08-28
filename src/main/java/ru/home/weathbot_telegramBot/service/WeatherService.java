package ru.home.weathbot_telegramBot.service;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import ru.home.weathbot_telegramBot.appconfig.BotConfig;
import ru.home.weathbot_telegramBot.model.weatherModel.FullWeather;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;


@Service
@Slf4j
@ConfigurationProperties(prefix = "weather")
public class WeatherService {
    private ReplyMessagesService messagesService;
    private BotConfig botConfig;

    public WeatherService(ReplyMessagesService messagesService, BotConfig botConfig) {
        this.messagesService = messagesService;
        this.botConfig = botConfig;
    }

    public String getWeather(String city) {
        return formatWeather(createWeatherObj(city));
    }

    public FullWeather createWeatherObj(String city) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(String.format(botConfig.getApiUrl(),city), FullWeather.class);

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
        return String.format(messagesService.getReplyText("reply.weatherPattern1"),fullWeather.getName(),fullWeather.getMain().getTemp(),fullWeather.getMain().getFeels_like(), Arrays.stream(fullWeather.getWeather()).iterator().next().getDescription(),createEmojiByCode(Arrays.stream(fullWeather.getWeather()).iterator().next().getIcon()), fullWeather.getWind().getSpeed());
    }
    @SneakyThrows
    private String createEmojiByCode(String code)  {
        String json= StreamUtils.copyToString( new ClassPathResource("icons.json").getInputStream(), Charset.defaultCharset()  );
        Map<String, String> map = new Gson().fromJson(json, Map.class);
        return map.get(code);
    }
}
