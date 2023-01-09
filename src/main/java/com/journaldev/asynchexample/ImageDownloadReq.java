package com.journaldev.asynchexample;


public class ImageDownloadReq {
    private String url;
    private String s3Path;


    public ImageDownloadReq(String url, String s3Path) {
        this.url = url;
        this.s3Path = s3Path;
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


}
