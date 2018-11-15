package uz.oltinolma.consumer.mvc.config;


public class SearchResult {
    private Object id;
    private float score;
    private String term;
    private String type;
    private String taxonomy;

    public Object getId() {
        return id;
    }

    public SearchResult setId(Object id) {
        this.id = id;
        return this;
    }

    public float getScore() {
        return score;
    }

    public SearchResult setScore(float score) {
        this.score = score;
        return this;
    }

    public String getTerm() {
        return term;
    }

    public SearchResult setTerm(String term) {
        this.term = term;
        return this;
    }

    public String getType() {
        return type;
    }

    public SearchResult setType(String type) {
        this.type = type;
        return this;
    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }

    public SearchResult() {
    }
}
