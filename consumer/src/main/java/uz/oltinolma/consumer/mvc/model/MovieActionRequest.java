package uz.oltinolma.consumer.mvc.model;

import uz.oltinolma.consumer.mvc.movie.Movie;
import uz.oltinolma.consumer.mvc.taxonomy.Taxonomy;

import java.util.List;
import java.util.UUID;

public class MovieActionRequest {
    private Movie movie;
    private UUID fileId;
    private List<Taxonomy> taxonomies;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public UUID getFileId() {
        return fileId;
    }

    public void setFileId(UUID fileId) {
        this.fileId = fileId;
    }

    public List<Taxonomy> getTaxonomies() {
        return taxonomies;
    }

    public void setTaxonomies(List<Taxonomy> taxonomies) {
        this.taxonomies = taxonomies;
    }
}
