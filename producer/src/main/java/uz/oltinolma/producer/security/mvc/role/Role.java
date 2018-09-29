package uz.oltinolma.producer.security.mvc.role;


public class Role {
    protected Long id;
    protected String name;
    private int id_status;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", id_status=" + id_status +
                '}';
    }

    public Role() {
    }

    public Role(Long id, String name, Integer count_permissions) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Role setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Role setName(String name) {
        this.name = name;
        return this;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }

}
