package uz.oltinolma.producer.files.payload;

import java.util.UUID;

public class FileDBModel {

    private UUID id;
    private String original_name;
    private String comment;
    private String relative_path;
    private Integer time;
    private String extension;
    private Long size;
    private String original_absolute_path;
    private String standart_absolute_path;
    private String thumbnail_absolute_path;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRelative_path() {
        return relative_path;
    }

    public void setRelative_path(String relative_path) {
        this.relative_path = relative_path;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getOriginal_absolute_path() {
        return original_absolute_path;
    }

    public void setOriginal_absolute_path(String original_absolute_path) {
        this.original_absolute_path = original_absolute_path;
    }

    public String getStandart_absolute_path() {
        return standart_absolute_path;
    }

    public void setStandart_absolute_path(String standart_absolute_path) {
        this.standart_absolute_path = standart_absolute_path;
    }

    public String getThumbnail_absolute_path() {
        return thumbnail_absolute_path;
    }

    public void setThumbnail_absolute_path(String thumbnail_absolute_path) {
        this.thumbnail_absolute_path = thumbnail_absolute_path;
    }
}
