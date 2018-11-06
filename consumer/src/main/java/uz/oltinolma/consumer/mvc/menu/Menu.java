package uz.oltinolma.consumer.mvc.menu;

public class Menu {
    private Integer id;
    private String name;
    private String notes;
    private Integer idParent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getIdParent() {
        return idParent;
    }

    public void setIdParent(Integer idParent) {
        this.idParent = idParent;
    }
}
