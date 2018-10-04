package uz.oltinolma.producer.files.dao;

public class ResponseFileIO {

    private String fileOriginalName;
    private String fileoriginalUrl;
    private String file800x800Url;
    private String fileThumbnailUrl;
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFileOriginalName() {
        return fileOriginalName;
    }

    public void setFileOriginalName(String fileOriginalName) {
        this.fileOriginalName = fileOriginalName;
    }

    public String getFileoriginalUrl() {
        return fileoriginalUrl;
    }

    public void setFileoriginalUrl(String fileoriginalUrl) {
        this.fileoriginalUrl = fileoriginalUrl;
    }

    public String getFile800x800Url() {
        return file800x800Url;
    }

    public void setFile800x800Url(String file800x800Url) {
        this.file800x800Url = file800x800Url;
    }

    public String getFileThumbnailUrl() {
        return fileThumbnailUrl;
    }

    public void setFileThumbnailUrl(String fileThumbnailUrl) {
        this.fileThumbnailUrl = fileThumbnailUrl;
    }
}
