package pl.grapeup.mika.tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TutorialApplication {
    public static void main(String[] args) {
        SpringApplication.run(TutorialApplication.class, args);
    }

}
