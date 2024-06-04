package cwb.client.data;

/**
 * Created by PC_chen on 2019/3/23.
 */

public class Connection {
    public String ip;
    public String port;

    public Connection(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
