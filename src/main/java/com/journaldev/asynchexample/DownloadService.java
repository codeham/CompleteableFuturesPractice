package com.journaldev.asynchexample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;
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
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            LOG.error("Thread was interrupted !");
            throw new InterruptedException();
        }

        byte[] byteArr = url.getBytes();
        LOG.info("Processed url download");
        return byteArr;
    }

    public Boolean virusScanByteArray(byte[] byteArr){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Random random = new Random();
        Boolean result = random.nextBoolean();
        LOG.info("Processed virus scanner : " + result);

        return result;
    }

    public void throwFileNotFoundException() throws FileNotFoundException {
        throw new FileNotFoundException("File not found exception !");
    }

    public void uploadToS3(byte[] byteArr) throws ArithmeticException{

        Random random = new Random();
        Boolean result = random.nextBoolean();

        if(result == true){
            try{
                int badOperation = 12/0;
            }catch(ArithmeticException e){
                LOG.error("Error division by Zero : " + e);
                throw e;
            }
        }

//        try {
//            Thread.sleep(1000);
//            LOG.info("Processed S3 upload for byte array: " + byteArr.toString());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            throw new InterruptedException("Thread was interrupted");
//        }
    }

    public void pushToQueue(ImageDownloadRes imageDownloadRes){
        try {
            Thread.sleep(1000);
            LOG.info("Push to queue SUCCESS -> " + imageDownloadRes.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void pushToQueueAsync(ImageDownloadRes imageDownloadRes){
        Double random = Math.random();
        LOG.info("Push to queue SUCCESS -> " + imageDownloadRes.toString());
    }

    public void throwThrowable()  throws ArithmeticException{
        Random random = new Random();
        Boolean result = random.nextBoolean();

        int divisionResult;
        try{
            divisionResult = 12/0;
        }catch(ArithmeticException e){
            LOG.error("Error division by Zero : " + e);
            throw new ArithmeticException("Division by Zero");
        }
    }
}
