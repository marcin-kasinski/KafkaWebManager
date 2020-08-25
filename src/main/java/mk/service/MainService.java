package mk.service;

import mk.dao.Broker;
import mk.dao.Topic;
import mk.dao.TopicPartition;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.*;
import org.apache.kafka.common.metrics.KafkaMetric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class MainService {

	AdminClient adminClient;

	private static Logger log = LoggerFactory.getLogger(MainService.class);


	public MainService() throws ExecutionException, InterruptedException {
		super();
		adminClient = connect();

	}

	public AdminClient connect() throws ExecutionException, InterruptedException {

		log.info("connect START");


		Properties properties = new Properties();
		properties.put("bootstrap.servers", "kafka-0.k-hs.default.svc.cluster.local:9094,kafka-1.k-hs.default.svc.cluster.local:9094,kafka-2.k-hs.default.svc.cluster.local:9094");
		properties.put("connections.max.idle.ms", 10000);
		properties.put("request.timeout.ms", 5000);

		//SSL
		properties.put("security.protocol", "SSL");

		properties.put("ssl.truststore.location", "/tmp/truststore-kafka-0.jks");
		properties.put("ssl.truststore.password", "secret");
		properties.put("ssl.truststore.type", "JKS");

		properties.put("ssl.keystore.location", "/tmp/keystore-kafka-0.jks");
		properties.put("ssl.keystore.password", "secret");
		properties.put("ssl.keystore.type", "JKS");

		AdminClient inadminClient = AdminClient.create(properties);

		log.info("connect END");

		return inadminClient;
	}


	public void listMetrics() throws ExecutionException, InterruptedException {

		log.info("listMetrics START");


		Map<MetricName, ? extends Metric> metrics =adminClient.metrics();


		log.info("metrics.size() "+metrics.size());


		for (Map.Entry<MetricName, ? extends Metric> entry : metrics.entrySet()) {
				//log.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			//log.info("Key = " + entry.getKey().getClass().getName() + ", Value = " + entry.getValue().getClass().getName());

			MetricName metricName = entry.getKey();
			KafkaMetric  kafkaMetric = (KafkaMetric) entry.getValue();
			log.info("Key = " + metricName.name() + ", Value = " + kafkaMetric.metricValue().toString());

		}


			log.info("listMetrics END");

	}

		public List<Broker> listBrokers() throws ExecutionException, InterruptedException {

		log.info("listBrokers START");

		//AdminClient adminClient = connect();

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

	public void listConsumerGroupOffsets(String groupId) throws ExecutionException, InterruptedException {

		log.info("listConsumerGroupOffsets START");

		//AdminClient adminClient = connect();
		ListConsumerGroupOffsetsResult listConsumerGroupOffsetsResult = adminClient.listConsumerGroupOffsets(groupId);

		Map<org.apache.kafka.common.TopicPartition, OffsetAndMetadata> consumerGroupOffsets = listConsumerGroupOffsetsResult.partitionsToOffsetAndMetadata().get();

		for (Map.Entry<org.apache.kafka.common.TopicPartition, OffsetAndMetadata> entry : consumerGroupOffsets.entrySet()) {
			//	log.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			//log.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());

			org.apache.kafka.common.TopicPartition topicPartition = entry.getKey();
			OffsetAndMetadata offsetAndMetadata = entry.getValue();

			log.info("Key = " + topicPartition.topic()+" /"+ topicPartition.partition());
			log.info("value = " + offsetAndMetadata.metadata()+" /"+offsetAndMetadata.offset());

		}


		}

		public void listConsumers() throws ExecutionException, InterruptedException {

		log.info("listConsumers START");

		//AdminClient adminClient = connect();
		ListConsumerGroupsResult listConsumerGroupsResult = adminClient.listConsumerGroups();
		Collection<ConsumerGroupListing> consumerGroupListings = listConsumerGroupsResult.all().get();

		log.info("consumerGroupListings.size() "+ consumerGroupListings.size());

		for (ConsumerGroupListing consumerGroupListing : consumerGroupListings) {

			String groupId=consumerGroupListing.groupId();
			log.info("groupId "+ groupId);

			DescribeConsumerGroupsResult describeConsumerGroupsResult;
			describeConsumerGroupsResult = adminClient.describeConsumerGroups(Collections.singleton(groupId));
			Map<String, ConsumerGroupDescription> consumerGroupDescriptions = describeConsumerGroupsResult.all().get();


			for (Map.Entry<String, ConsumerGroupDescription> entry : consumerGroupDescriptions.entrySet()) {
				//	log.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());
				//log.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());

				ConsumerGroupDescription consumerGroupDescription = entry.getValue();
				log.info("consumerGroupDescription.groupId() = " + consumerGroupDescription.groupId());
				log.info("members = " + consumerGroupDescription.members().toString());
			}//			for (Map.Entry<String, ConsumerGroupDescription> entry : consumerGroupDescriptions.entrySet()) {


			listConsumerGroupOffsets(groupId);



		}//		for (ConsumerGroupListing consumerGroupListing : consumerGroupListings) {




/*
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
*/
	}



	public List<Topic> listTopics() throws ExecutionException, InterruptedException {

		log.info("listTopics START");

		ListTopicsOptions lto = new ListTopicsOptions();
		lto.timeoutMs(10 * 1000);
		ListTopicsResult ltr = adminClient.listTopics(lto);

		log.info("a "+ltr.listings().get().size());

		DescribeTopicsOptions dto = new DescribeTopicsOptions();
		dto.timeoutMs(15 * 1000);
		DescribeTopicsResult dtr = adminClient.describeTopics(ltr.names().get(), dto);

		Map<String, TopicDescription> topicListings =dtr.all().get();
		log.info("topicListings.size() "+topicListings.size());

		List<Topic> topics = new ArrayList();

		// using for-each loop for iteration over Map.entrySet()
		for (Map.Entry<String, TopicDescription> entry : topicListings.entrySet())
		{
		//	log.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		//log.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());

		TopicDescription topicDescription=entry.getValue();

			List<TopicPartitionInfo> partitions=			topicDescription.partitions();

			int size=partitions.size();
			List<TopicPartition> topicPartitions = new ArrayList();

			for (int i=0;i<size;i++) {


				TopicPartitionInfo topicPartitionInfo=partitions.get(i);

				List<Node> isrfromadmin=topicPartitionInfo.isr();

				int isrsize=isrfromadmin.size();
				List<Broker> isr = new ArrayList();

				for (int ii=0;ii<isrsize;ii++) {

					Node isrfromadminrecord= isrfromadmin.get(ii);

					Broker isrrecord = new Broker(isrfromadminrecord.id(), isrfromadminrecord.idString(),isrfromadminrecord.host(), isrfromadminrecord.rack(), isrfromadminrecord.port());
					isr.add(isrrecord);


				}


				Broker leader = new Broker(topicPartitionInfo.leader().id(), topicPartitionInfo.leader().idString(), topicPartitionInfo.leader().host(), topicPartitionInfo.leader().rack(), topicPartitionInfo.leader().port());

				TopicPartition topicPartition = new TopicPartition(leader, isr);
				topicPartitions.add(topicPartition);
			}//for (int i=0;i<size;i++) {


				Topic topic = new Topic(topicDescription.name(), topicPartitions);


 		topics.add(topic);
		}//for

/*
		ListTopicsResult listTopicsResult = adminClient.listTopics();
		Collection<TopicListing> topicListings = listTopicsResult.listings().get();

		log.info("topicListings.size() "+topicListings.size());


		List<Topic> topics = new ArrayList();


		for (TopicListing topic : topicListings) {
			log.info("name "+topic.name());
			log.info("isInternal "+topic.isInternal());
		}
*/
		log.info("listTopics END");
		return topics;

	}



	public Topic describeTopic(String topicName) throws ExecutionException, InterruptedException {

		log.info("describeTopic START");

		DescribeTopicsResult result = adminClient.describeTopics(Arrays.asList(topicName));
		Map<String, KafkaFuture<TopicDescription>>  values = result.values();
		KafkaFuture<TopicDescription> topicDescription = values.get(topicName);
		int partitions = topicDescription.get().partitions().size();

		log.info("topic name "+topicDescription.get().name());

		log.info("partition size "+partitions);

		Topic topic = new Topic("", null);


		log.info("describeTopic END");
		return topic;

	}







}
