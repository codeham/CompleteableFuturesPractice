package com.journaldev.asynchexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
public class DownloadService {

    private static final Logger LOG = LoggerFactory.getLogger(DownloadService.class);

    @Async
    public CompletableFuture<byte[]> downloadFromUrlAsync(String url) throws InterruptedException{
        Thread.sleep(1000);
        byte[] byteArr = url.getBytes();
        LOG.info("Processed url download");
        return CompletableFuture.completedFuture(byteArr);
    }

    public byte[] downloadFromUrl(String url) throws InterruptedException{
        Thread.sleep(1000);
        byte[] byteArr = url.getBytes();
        LOG.info("Processed url download");
        return byteArr;
    }

    public Boolean virusScanByteArray(byte[] byteArr){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Random random = new Random();
        Boolean result = random.nextBoolean();
        LOG.info("Processed virus scanner : " + result);

        return result;
    }

    public void uploadToS3(byte[] byteArr) throws InterruptedException{
        try {
            Thread.sleep(1200);
            LOG.info("Processed S3 upload for byte array: " + byteArr.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pushToQueue(ImageDownloadRes imageDownloadRes){
        try {
            Thread.sleep(3000);
            LOG.info("Push to queue SUCCESS -> " + imageDownloadRes.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void pushToQueueAsync(ImageDownloadRes imageDownloadRes){
        Double random = Math.random();
        LOG.info("Push to queue SUCCESS -> " + imageDownloadRes.toString());

//        try {
//            Thread.sleep(500);
//            LOG.info("Push to queue SUCCESS -> " + imageDownloadRes.toString());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
