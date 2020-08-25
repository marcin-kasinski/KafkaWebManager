package mk;

import mk.dao.Broker;
import mk.dao.Topic;
import mk.service.MainService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@RequestMapping("/listtopics")
	// @ResponseBody
	public List<Topic> listtopics() throws ExecutionException, InterruptedException {


		List<Topic> topics = mainService.listTopics();

		return topics;
	}

	@RequestMapping("/listconsumers")
	// @ResponseBody
	public void listConsumers() throws ExecutionException, InterruptedException {


		//	List<Topic> topics = mainService.listConsumers();
		mainService.listConsumers();

		//return topics;
	}

	@RequestMapping("/listmetrics")
	// @ResponseBody
	public void listMetrics() throws ExecutionException, InterruptedException {

		//	List<Topic> topics = mainService.listConsumers();
		mainService.listMetrics();

		//return topics;
	}

	@RequestMapping("/listacls")
	// @ResponseBody
	public void listAcls() throws ExecutionException, InterruptedException {

		//	List<Topic> topics = mainService.listConsumers();
		mainService.listAcls();

		//return topics;
	}


	@RequestMapping("/describetopic")
	// @ResponseBody
	public Topic describetopic(@RequestParam(value = "topicname") String topicname)  {


		Topic topic = null;
		try {
			topic = mainService.describeTopic(topicname);
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return topic;
	}



}
