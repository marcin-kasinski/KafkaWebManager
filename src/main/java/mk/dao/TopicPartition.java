package mk.dao;


import org.apache.kafka.common.Node;

import java.util.List;

public class TopicPartition{

    Broker leader;

    public List<Broker> getIsr() {
        return isr;
    }

    public void setIsr(List<Broker> isr) {
        this.isr = isr;
    }

    List<Broker> isr;

    public Broker getLeader() {
        return leader;
    }

    public void setLeader(Broker leader) {
        this.leader = leader;
    }

    public TopicPartition(Broker in_leader, List<Broker> in_isr)

    {

    leader= in_leader;
    isr=in_isr;
    }

}