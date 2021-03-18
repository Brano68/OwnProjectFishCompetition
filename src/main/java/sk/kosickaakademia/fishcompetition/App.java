package sk.kosickaakademia.fishcompetition;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import sk.kosickaakademia.fishcompetition.database.Database;

import java.util.Collections;

/**
 * Fish competition!
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = "sk.kosickaakademia.fishcompetition.controller")
public class App 
{
    public static void main( String[] args )
    {
        //System.out.println( "Hello World!" );
        SpringApplication app = new SpringApplication(App.class);
        app.setDefaultProperties(Collections.<String, Object>singletonMap("server.port", "8083"));
        app.run(args);
    }
}
