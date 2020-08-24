package mk.service;

import mk.dao.Broker;
import mk.dao.Topic;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Service
public class MainService {

	private static Logger log = LoggerFactory.getLogger(MainService.class);


	public MainService() {
		super();

	}

	public AdminClient connect() throws ExecutionException, InterruptedException {

		log.info("connect START");


		Properties properties = new Properties();
		properties.put("bootstrap.servers", "kafka-0.k-hs.default.svc.cluster.local:9092");
		properties.put("connections.max.idle.ms", 10000);
		properties.put("request.timeout.ms", 5000);

		AdminClient adminClient = AdminClient.create(properties);

		log.info("connect END");

		return adminClient;
	}


	public List<Broker> listBrokers() throws ExecutionException, InterruptedException {

		log.info("listBrokers START");

		AdminClient adminClient = connect();
		DescribeClusterResult describeClusterResult = adminClient.describeCluster();
		Collection<Node> clusterDetails = describeClusterResult.nodes().get();

		log.info("clusterDetails.size() "+ clusterDetails.size());

		List<Broker> brokers = new ArrayList();


		for (Node node : clusterDetails) {
			log.info("id "+node.id());
			log.info("idString "+node.idString());
			log.info("host "+node.host());
			log.info("rack "+node.rack());
			log.info("port "+node.port());

			brokers.add(new  Broker(node.id(), node.idString(), node.host(), node.rack(), node.port()));


		}

		log.info("listBrokers END");
		return brokers;

	}



	public List<Topic> listTopics() throws ExecutionException, InterruptedException {

		log.info("listTopics START");

		AdminClient adminClient = connect();
		ListTopicsResult listTopicsResult = adminClient.listTopics();
		Collection<TopicListing> topicListings = listTopicsResult.listings().get();


		List<Topic> topics = new ArrayList();


		for (TopicListing topic : topicListings) {
			log.info("name "+topic.name());
			log.info("isInternal "+topic.isInternal());
		}

		log.info("listTopics END");
		return topics;

	}


	public List<Topic> describeTopic() throws ExecutionException, InterruptedException {

		log.info("describeTopic START");

		AdminClient adminClient = connect();
		TopicDescription topicDescription =  adminClient.describeTopics("xxx");
		Collection<TopicListing> topicListings = topicDescription.name();


		List<Topic> topics = new ArrayList();


		for (TopicListing topic : topicListings) {
			log.info("name "+topic.name());
			log.info("isInternal "+topic.isInternal());
		}

		log.info("describeTopic END");
		return topics;

	}







}
