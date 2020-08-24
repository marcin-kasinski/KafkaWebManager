package mk;

import mk.dao.Broker;
import mk.service.MainService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

//@EnableDiscoveryClient
@RestController
@RequestMapping("/api")
public class MicroserviceController {

	@Autowired
	MainService mainService;


	private static Logger log = LoggerFactory.getLogger(MicroserviceController.class);


	public MicroserviceController() {
		super();

	}


	@RequestMapping("/getxxx")
	// @ResponseBody
	public String getXXX(String email, String name) {
		String xxx = "XXX";
		return xxx;
	}


	@RequestMapping("/listbrokers")
	// @ResponseBody
	public List<Broker> listbrokers() throws ExecutionException, InterruptedException {
		String xxx = "XXX";

		List<Broker> brokers = mainService.listBrokers();

		return brokers;
	}



}
