package ru.home.weathbot_telegramBot.model.weatherModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class FullWeather {
    private String visibility;

    private String timezone;

    private Main main;

    private Clouds clouds;

    private Sys sys;

    private String dt;

    private Coord coord;

    private Weather[] weather;

    private String name;

    private String cod;

    private String id;

    private String base;

    private Wind wind;


    @Data
    public static class Sys {
        private String country;

        private String sunrise;

        private String sunset;

        private String id;

        private String type;

        public String getCountry() {
            return country;
        }


        public String getSunrise() {
            return sunrise;
        }


        public String getSunset() {
            return sunset;
        }


        public String getId() {
            return id;
        }


        public String getType() {
            return type;
        }


    }

    @Data
    public static class Clouds {
        private String all;

    }
    @Data
    public static class Wind {
        private String deg;

        private String speed;

    }
    @Data
    public static class Main {
        private String temp;

        private String temp_min;

        private String humidity;

        private String pressure;

        private String feels_like;

        private String temp_max;

    }
    @Data
    public static class Weather {
        private String icon;

        private String description;

        private String main;

        private String id;

    }
    @Data
    public static class Coord {
        private String lon;

        private String lat;

    }

}

