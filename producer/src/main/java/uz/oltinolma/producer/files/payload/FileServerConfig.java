package uz.oltinolma.producer.files.payload;

public class FileServerConfig {
    private Integer id;
    private String host;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "FileServerConfig{" +
                "id=" + id +
                ", host='" + host + '\'' +
                '}';
    }
}
