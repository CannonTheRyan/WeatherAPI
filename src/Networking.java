import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class Networking{
    private String APIKey;
    private String baseURL;
    private String imageURL;

    public Networking(){
        APIKey = "fd1066e8ac0a40f7b59135706221805";
        baseURL = "https://api.weatherapi.com/v1/current.json?key=" + APIKey + "&q=";
        imageURL = "";
    }

    public Weather getWeather(int zipCode)
    {
        String json = makeAPICall(baseURL + zipCode);
        JSONObject jsonObj = new JSONObject(json);
        JSONObject currentObj = jsonObj.getJSONObject("current");
        JSONObject conditionObj = currentObj.getJSONObject("condition");

        double temperatureF = currentObj.getDouble("temp_f");
        double temperatureC = currentObj.getDouble("temp_c");
        String condition = conditionObj.getString("text");
        imageURL = conditionObj.getString("icon");

        return new Weather(temperatureF, condition, temperatureC, imageURL);
    }

    public String makeAPICall(String url)
    {
        try {
            URI myUri = URI.create(url); // creates a URI object from the url string
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}

