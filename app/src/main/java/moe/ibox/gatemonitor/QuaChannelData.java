package moe.ibox.gatemonitor;

public class QuaChannelData {
    private double temperature;
    private double humidity;
    private double co2;
    private double noise;
    private String time;

    public QuaChannelData(double temperature, double humidity, double co2, double noise) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.co2 = co2;
        this.noise = noise;
    }

    public QuaChannelData(double temperature, double humidity, double co2, double noise, String datetime) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.co2 = co2;
        this.noise = noise;
        this.time = datetime;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getCo2() {
        return co2;
    }

    public double getNoise() {
        return noise;
    }

    public String getTime() {
        return time;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setCo2(double co2) {
        this.co2 = co2;
    }

    public void setNoise(double noise) {
        this.noise = noise;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
