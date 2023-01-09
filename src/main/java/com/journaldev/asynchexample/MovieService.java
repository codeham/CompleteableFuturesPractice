package com.journaldev.asynchexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class MovieService {

    private static final Logger LOG = LoggerFactory.getLogger(MovieService.class);

    private final RestTemplate restTemplate;

    public MovieService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async
    public CompletableFuture<Void> lookForMovie(String movieId) {
        LOG.info("Looking up Movie ID: {}", movieId);
        try{
            Thread.sleep(1000L);
        }catch(InterruptedException e){
            System.out.println(e);
        }

        return CompletableFuture.completedFuture(null);
    }

    public void uploadMovieToDatabase() throws InterruptedException{
        Thread.sleep(3000);
//        LOG.info("Uploaded Movie to Database");
    }

    public void uploadMovieToS3() throws InterruptedException{
        Thread.sleep(2000L);
        LOG.info("Uploaded Movie to S3");
    }

    public void checkVirusScanner() throws InterruptedException{
        Thread.sleep(2000L);
//        LOG.info("Uploaded Movie to S3");
    }
}
