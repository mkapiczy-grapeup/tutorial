package pl.grapeup.mika.tutorial.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("ratings")
@RefreshScope
public class RatingController {

    private final EurekaClient client;

    private final RestTemplateBuilder restTemplateBuilder;

    @Value("${custom.endpoint.ratings}")
    private String ratingsEndpoint;

    @Value("${custom.service-name.ratings}")
    private String ratingServiceName;

    public RatingController(EurekaClient client, RestTemplateBuilder restTemplateBuilder) {
        this.client = client;
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @GetMapping
    public String getAllRatings() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        InstanceInfo ratingServiceInstance = client.getNextServerFromEureka(ratingServiceName, false);
        ResponseEntity<String> response = restTemplate.exchange(String.format("%s%s", ratingServiceInstance.getHomePageUrl(),
                ratingsEndpoint), HttpMethod.GET,
                null, String.class);
        return response.getBody();
    }

    @GetMapping(value = "/{roomId}")
    public String getRatingForRoom(@PathVariable Long roomId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        InstanceInfo ratingServiceInstance = client.getNextServerFromEureka(ratingServiceName, false);

        final String getRatingEndpoint = ratingServiceInstance.getHomePageUrl() + ratingsEndpoint + "/{roomId}";
        Map<String, Object> params = new HashMap<>();
        params.put("roomId", roomId);

        ResponseEntity<String> response = restTemplate.exchange(getRatingEndpoint, HttpMethod.GET, null, String.class, params);
        return response.getBody();
    }
}
