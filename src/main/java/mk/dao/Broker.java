package mk.dao;


public class Broker {

    long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    String idString;
    String host;
    String rack;
    int port;


    public Broker(long in_id,     String in_idString,String in_host, String in_rack, int in_port   )

    {
        id = in_id;
        idString = in_idString;
        host = in_host;
        rack = in_rack;
        port = in_port;

    }

}