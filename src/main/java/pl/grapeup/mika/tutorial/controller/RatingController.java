package pl.grapeup.mika.tutorial.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("ratings")
@RefreshScope
public class RatingController {

    @Autowired
    private EurekaClient client;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Value("${custom.endpoints.ratings}")
    private String ratingsEndpoint;

    @Value("${custom.service-name.ratings}")
    private String ratingServiceName;

    @GetMapping
    public String callService() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        InstanceInfo instanceInfo = client.getNextServerFromEureka(ratingServiceName, false);
        String baseUrl = instanceInfo.getHomePageUrl();
        ResponseEntity<String> response = restTemplate.exchange(baseUrl + ratingsEndpoint, HttpMethod.GET, null, String.class);
        return response.getBody();
    }
}
