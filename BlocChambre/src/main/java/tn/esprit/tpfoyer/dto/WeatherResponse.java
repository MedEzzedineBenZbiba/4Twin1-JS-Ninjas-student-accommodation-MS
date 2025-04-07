package tn.esprit.tpfoyer.dto;

import java.util.List;

public class WeatherResponse {
    private Main main;
    private List<Weather> weather;
    private String name;

    public Main getMain() { return main; }
    public void setMain(Main main) { this.main = main; }

    public List<Weather> getWeather() { return weather; }
    public void setWeather(List<Weather> weather) { this.weather = weather; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public static class Main {
        private double temp;
        private int humidity;

        public double getTemp() { return temp; }
        public void setTemp(double temp) { this.temp = temp; }

        public int getHumidity() { return humidity; }
        public void setHumidity(int humidity) { this.humidity = humidity; }
    }

    public static class Weather {
        private String main;
        private String description;

        public String getMain() { return main; }
        public void setMain(String main) { this.main = main; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}
