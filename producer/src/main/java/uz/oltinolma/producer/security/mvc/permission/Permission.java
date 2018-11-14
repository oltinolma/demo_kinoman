package uz.oltinolma.producer.security.mvc.permission;

public class Permission {
    private Integer id ;
    private String name;
    private String notes;

    public Integer getId() {
        return id;
    }

    public Permission setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Permission setName(String name) {
        this.name = name;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public Permission setNotes(String notes) {
        this.notes = notes;
        return this;
    }
}
