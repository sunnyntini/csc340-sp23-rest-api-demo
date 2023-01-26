package com.csc340.restapidemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author sunny
 */
@RestController
public class RestApiController {

    /**
     * Hello World API endpoint.
     *
     * @return response string.
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World";
    }

    /**
     * Greeting API endpoint.
     *
     * Writes a greeting in Json format.
     *
     * @return The json repsonse
     */
    @GetMapping("/greeting")
    public Object getGreeting() {

        // create `ObjectMapper` instance
        ObjectMapper mapper = new ObjectMapper();

        // create a JSON object
        ObjectNode greeting = mapper.createObjectNode();
        greeting.put("id", 1);
        greeting.put("sender", "user");
        greeting.put("recipient", "World");
        greeting.put("message", "Hello!");

        // print json
        System.out.println(greeting);
        return greeting;

    }

    /**
     * Intro API endpoint.
     *
     * @param name, the request parameter
     * @return The json response
     */
    @GetMapping("/intro")
    public String getIntro(@RequestParam(value = "name", defaultValue = "Dora") String name) {
        return String.format("Hola, soy %s!", name);
    }

    /**
     * Get a quote from quotable and it available at this endpoint.
     *
     * @return The quote json response
     */
    @GetMapping("/quote")
    public Object getQuote() {
        try {
            String url = "https://api.quotable.io/random";
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            String jSonQuote = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(jSonQuote);

            //Print the whole response to console.
            System.out.println(root);

            //Parse out the most important info from the response.
            String quoteAuthor = root.get("author").asText();
            String quoteContent = root.get("content").asText();
            System.out.println("Author: " + quoteAuthor);
            System.out.println("Quote: " + quoteContent);

            return root;

        } catch (JsonProcessingException ex) {
            Logger.getLogger(RestApiController.class.getName()).log(Level.SEVERE, null, ex);
            return "error in /quote";
        }
    }

    /**
     * Get a list of universities from hipolabs and make them available at this
     * endpoint.
     *
     * @return json array
     */
    @GetMapping("/univ")
    public Object getUniversities() {
        try {
            String url = "http://universities.hipolabs.com/search?name=sports";
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            String jSonQuote = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(jSonQuote);

            //Print the whole response to console.
            System.out.println(root);

            //Print relevant info to the console
            for (JsonNode rt : root) {
                String name = rt.get("name").asText();
                String country = rt.get("country").asText();
                System.out.println(name + ": " + country);
            }

            return root;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(RestApiController.class.getName()).log(Level.SEVERE, null, ex);
            return "error in /univ";
        }

    }

}
