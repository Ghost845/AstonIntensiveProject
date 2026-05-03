package main.java.org.example;

@RestController
public class ConfigServerApplication {

    @RequestMapping("/fallback")
    public String fallback() {
        return "Service temporarily unavailable";
    }
}