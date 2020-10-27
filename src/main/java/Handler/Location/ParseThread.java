package Handler.Location;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import retrofit2.http.Url;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
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
        f.format("http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s&units=metric", latitude, lontitude, APIkey);
        try {
            URL url = new URL(f.toString());
            System.out.println(url.toString());
            InputStreamReader inpStream = new InputStreamReader(url.openStream());
            JsonObject jsonObject = new Gson().fromJson(inpStream, JsonObject.class);
            stringBuilder.append(jsonObject.get("weather").getAsJsonArray().get(0).getAsJsonObject()).append('\n');
            stringBuilder.append("Your city-").append(jsonObject.get("name"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("ssstring -" +  stringBuilder);
        return stringBuilder.toString();
    }
}
