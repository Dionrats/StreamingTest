package nl.quintor.StreamingTest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/streams")
public class StreamController  {

    @Value("${service.name}")
    private String name;

    @GetMapping
    public String Stream() {
        return "Hello " + name + "!";
    }
}
