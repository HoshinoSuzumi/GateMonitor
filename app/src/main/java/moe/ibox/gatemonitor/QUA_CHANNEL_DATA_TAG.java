package moe.ibox.gatemonitor;

public enum QUA_CHANNEL_DATA_TAG {
    TEMPERATURE("temperature"),
    HUMIDITY("humidity"),
    CO2("co2"),
    NOISE("noise");

    private final String tag;

    QUA_CHANNEL_DATA_TAG(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
