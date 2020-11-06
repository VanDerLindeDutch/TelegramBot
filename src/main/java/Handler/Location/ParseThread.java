package Handler.Location;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Formatter;
import java.util.concurrent.Callable;

public class ParseThread implements Callable<String> {
    private static final String APIkey = "b34852d915ba576896a684069a12483d";
    private final float lontitude;
    private final float latitude;

    public ParseThread(float lontitude, float latitude) {
        this.lontitude = lontitude;
        this.latitude = latitude;
    }

    @Override
    public String call() {

        StringBuilder stringBuilder = new StringBuilder();
        Formatter f = new Formatter();
        f.format("https://api.openweathermap.org/data/2.5/onecall?lat=%f&lon=%f&exclude=minutely,hourly,alerts&appid=%s&units=metric", latitude, lontitude, APIkey);
        try {
            URL url = new URL(f.toString());
            System.out.println(url.toString());
            InputStreamReader inpStream = new InputStreamReader(url.openStream());
            JsonObject jsonObject = new Gson().fromJson(inpStream, JsonObject.class);
            JsonObject daily = jsonObject.get("daily").getAsJsonArray().get(1).getAsJsonObject();
            String weatherDesc = daily.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").toString();
            JsonObject temp = daily.get("temp").getAsJsonObject();
            stringBuilder.append("Weather description-")
                    .append(dropQuotes(weatherDesc))
                    .append('\n')
                    .append("Info:\n")
                    .append("Temperature in the daytime:")
                    .append(temp.get("day").getAsInt())
                    .append("\n This temperature feels like:")
                    .append(daily.get("feels_like").getAsJsonObject().get("day").getAsInt())
                    .append("\nHumidity:").append(daily.get("humidity").getAsInt())
                    .append("\nPressure:").append(daily.get("pressure").getAsInt());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("weather-" + stringBuilder);
        return stringBuilder.toString();
    }

    private static String dropQuotes(String string) {
        string = string.substring(1, string.length() - 1);
        return string;
    }

}
