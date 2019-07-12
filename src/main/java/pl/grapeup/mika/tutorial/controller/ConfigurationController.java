package pl.grapeup.mika.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.grapeup.mika.tutorial.Configuration;

@RestController
@RequestMapping("config")
@RefreshScope
public class ConfigurationController {

    @Autowired
    private Configuration properties;

    @Value("${some.other.property}")
    private String someOtherProperty;

    @GetMapping
    public String printConfig() {
        return properties.getProperty() + " || " + someOtherProperty;
    }
}
