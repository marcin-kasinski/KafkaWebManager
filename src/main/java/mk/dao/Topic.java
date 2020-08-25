package mk.dao;


import java.util.List;

public class Topic{

    String name;
    boolean isInternal;
    List<TopicPartition> topicPartitions;

    public List<TopicPartition> getTopicPartitions() {
        return topicPartitions;
    }

    public void setTopicPartitions(List<TopicPartition> topicPartitions) {
        this.topicPartitions = topicPartitions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInternal() {
        return isInternal;
    }

    public void setInternal(boolean internal) {
        isInternal = internal;
    }

    public Topic(String in_name,List<TopicPartition> in_topicPartitions)

    {

        name=in_name;
        topicPartitions=in_topicPartitions;


    }

}