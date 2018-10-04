package uz.oltinolma.producer.files.dao;

import java.util.HashMap;
import java.util.UUID;

public class FileIO {

    private static final String PREFIX_800x800 = "800x800.";
    private static final String PREFIX_DOT_THUMBNAIL = "thumbnail.";

    private UUID id;
    private String originalName;
    private String path;
    private String thumbnailName;
    private String standardName;
    private UUID visitId;
    private String comment;
    private String host;
    private Long time;
    private String extension;
    private Long size;

    public FileIO() {
    }

    public HashMap<Object, Object> responseBuilder(FileIO fileIO, HashMap<String, Object> map) {
        HashMap<Object, Object> resource = new HashMap<>();
        HashMap<Object, Object> urls = new HashMap<>();
        urls.put("original_absolute_path", fileIO.getHost() + fileIO.getComment());
        if (fileIO.getExtension().matches("image/\\S+")) {
            urls.put("thumbnail_absolute_path", fileIO.getHost() + fileIO.getThumbnailName());
            urls.put("standart_absolute_path", fileIO.getHost() + fileIO.getStandardName());

        }
        resource.putAll(map);
        resource.remove("relative_path");
        resource.remove("original_absolute_path");
        resource.remove("thumbnail_absolute_path");
        resource.remove("standart_absolute_path");
        resource.put("urls", urls);
        HashMap<Object, Object> response = new HashMap<>();
        response.put(fileIO.getId(), resource);
        return response;
    }

    public FileIO(String originalName, String originalPath) {
        this.originalName = originalName;
        this.path = originalPath;
        this.standardName = PREFIX_800x800 + this.originalName;
        this.thumbnailName = PREFIX_DOT_THUMBNAIL + this.originalName;
        this.comment = this.originalName;
    }


    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumbnailName() {
        return thumbnailName;
    }

    public void setThumbnailName(String thumbnailName) {
        this.thumbnailName = thumbnailName;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public UUID getVisitId() {
        return visitId;
    }

    public void setVisitId(UUID visitId) {
        this.visitId = visitId;
    }

    @Override
    public String toString() {
        return "FileIO{" +
                "id=" + id +
                ", originalName='" + originalName + '\'' +
                ", path='" + path + '\'' +
                ", thumbnailName='" + thumbnailName + '\'' +
                ", standardName='" + standardName + '\'' +
                ", visitId=" + visitId +
                ", comment='" + comment + '\'' +
                '}';
    }
}
