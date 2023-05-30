package rs.ac.singidunum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import rs.ac.singidunum.model.FlightModel;
import rs.ac.singidunum.model.PageModel;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Getter
public class FlightService {
    private static FlightService instance;
    private HttpClient client;
    private ObjectMapper mapper;

    public static FlightService getInstance() {
        if (instance == null) {
            instance = new FlightService();
        }
        return instance;
    }

    private FlightService() {
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(3))
                .build();

        // Register json mapper
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private String get(String path) {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create("https://flight.pequla.com/api" + path))
                    .header("Accept", "application/json")
                    .GET()
                    .build();
            HttpResponse<String> rsp = client.send(req, HttpResponse.BodyHandlers.ofString());
            if (rsp.statusCode() != 200) {
                throw new RuntimeException("Request failed, response code: " + rsp.statusCode());
            }
            return rsp.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public PageModel<FlightModel> getFlights(int page) throws JsonProcessingException {
        if (page < 0) {
            throw new RuntimeException("Stranica ne sme biti negativna");
        }
        String json = get("/flight");
        return mapper.readValue(json, new TypeReference<>() {
        });
    }

    public FlightModel getFlightById(int id) throws JsonProcessingException {
        String json = get("/flight/" + id);
        return mapper.readValue(json, FlightModel.class);
    }
}
