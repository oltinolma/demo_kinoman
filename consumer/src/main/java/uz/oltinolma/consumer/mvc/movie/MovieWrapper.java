package uz.oltinolma.consumer.mvc.movie;


public class MovieWrapper {
    private String file;
    private Movie movie;
    private Object taxonomy;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Object getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(Object taxonomy) {
        this.taxonomy = taxonomy;
    }
}

