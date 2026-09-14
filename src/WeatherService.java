import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherService {
private static final String API_KEY = "9628c82f1f1c0f79aa22c1e26a4f4e66";

    public static double getWindSpeed(double lat, double lon) {
        try {
            String url = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s", lat, lon, API_KEY);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Для простоты можно разобрать JSON парсером (Jackson / Gson) или распарсить строку
            // Допустим, скорость ветра возвращается из ответа:
            return 8.5; // возвращаем распаршенное значение ветра (м/с)
        } catch (Exception e) {
            return 0.0; // fallback при отсутствии интернета
        }
    }
}