package uz.oltinolma.consumer.mvc.taxonomy;

public class Taxonomy {

    private Integer id;
    private String name;
    private boolean structure;
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

    public boolean isStructure() {
        return structure;
    }

    public void setStructure(boolean structure) {
        this.structure = structure;
    }

    public Integer getIdParent() {
        return idParent;
    }

    public void setIdParent(Integer idParent) {
        this.idParent = idParent;
    }

    @Override
    public String toString() {
        return "Taxonomy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", structure=" + structure +
                ", idParent=" + idParent +
                '}';
    }
}
