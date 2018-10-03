package uz.oltinolma.consumer.mvc.model;

public class TaxonomyTerm {
    private Integer id;
    private String name;
    private Integer idParent;
    private Integer idStatus;
    private Integer idTaxonomy;
    private String taxonomy_name;
    private boolean hierarchical;

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

    public Integer getIdParent() {
        return idParent;
    }

    public void setIdParent(Integer idParent) {
        this.idParent = idParent;
    }

    public Integer getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }

    public Integer getIdTaxonomy() {
        return idTaxonomy;
    }

    public void setIdTaxonomy(Integer idTaxonomy) {
        this.idTaxonomy = idTaxonomy;
    }

    public String getTaxonomy_name() {
        return taxonomy_name;
    }

    public void setTaxonomy_name(String taxonomy_name) {
        this.taxonomy_name = taxonomy_name;
    }

    public boolean isHierarchical() {
        return hierarchical;
    }

    public void setHierarchical(boolean hierarchical) {
        this.hierarchical = hierarchical;
    }

    @Override
    public String toString() {
        return "TaxonomyTerm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", idParent=" + idParent +
                ", idStatus=" + idStatus +
                ", idTaxonomy=" + idTaxonomy +
                ", taxonomy_name='" + taxonomy_name + '\'' +
                ", hierarchical=" + hierarchical +
                '}';
    }
}
