package com.journaldev.asynchexample;

public class ImageDownloadRes {
    private String url;
    private String s3Path;

    @Override
    public String toString() {
        return "ImageDownloadRes{" +
                "url='" + url + '\'' +
                ", s3Path='" + s3Path + '\'' +
                ", isDownloadSuccess=" + isDownloadSuccess +
                '}';
    }

    private Boolean isDownloadSuccess;

    public ImageDownloadRes(String url, String s3Path, Boolean isDownloadSuccess) {
        this.url = url;
        this.s3Path = s3Path;
        this.isDownloadSuccess = isDownloadSuccess;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getS3Path() {
        return s3Path;
    }

    public void setS3Path(String s3Path) {
        this.s3Path = s3Path;
    }

    public Boolean getDownloadSuccess() {
        return isDownloadSuccess;
    }

    public void setDownloadSuccess(Boolean downloadSuccess) {
        isDownloadSuccess = downloadSuccess;
    }
}
