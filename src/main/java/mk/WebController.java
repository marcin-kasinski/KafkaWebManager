package mk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

//----------------------------------- 2.0.1 -----------------------------------	
//----------------------------------- 2.0.1 -----------------------------------

//----------------------------------- 1.5.10 -----------------------------------
//import org.springframework.cloud.sleuth.Span;
//import org.springframework.cloud.sleuth.Tracer;
//----------------------------------- 1.5.10 -----------------------------------
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
//import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Controller
class WebController {

    /*
	@Value("${app.ajax_url}")
	String ajax_url;

	@Value("${app.event_url}")	
	String event_url;

	@Value("${app.event_kafka_url}")	
	String event_kafka_url;

	@Value("${app.rest_url}")
	String rest_url;
	*/
	@Autowired
	Environment env;
	
    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

	 private static Logger log = LoggerFactory.getLogger(WebController.class);

    @GetMapping("/greeting")
    public String greetingVIEW(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "greeting";
    }

    @GetMapping("/")
    public String mainVIEW(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "status";
    }


    @PostMapping("/greeting")
    public String greetingForm(@ModelAttribute Greeting greeting) {
    	
    	greeting.setContent("Po procesowaniu "+greeting.getContent());
        return "result";
    }

}