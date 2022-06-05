package br.com.karate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication()
public class KarateProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(KarateProjectApplication.class, args);
    }
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
