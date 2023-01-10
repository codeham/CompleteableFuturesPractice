package com.journaldev.asynchexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class ApplicationRunner implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationRunner.class);

    private final MovieService movieService;

    private final DownloadService downloadService;

    public ApplicationRunner(MovieService movieService, DownloadService downloadService) {
        this.movieService = movieService;
        this.downloadService = downloadService;
    }

    public void triggerCompleteFutureTwo() throws Exception{
        List<CompletableFuture<Void>> completableFutures = new ArrayList<>();
        List<ImageDownloadReq> downloadReqs = new ArrayList<>();

        for(int i = 0; i < 20; i++){
            ImageDownloadReq imageDownloadReq = new ImageDownloadReq("https://..com/img.jpg_" + i, "/s3path/path");
            downloadReqs.add(imageDownloadReq);
        }

        for(ImageDownloadReq downloadReq: downloadReqs){
            CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    return downloadService.downloadFromUrl(downloadReq.getUrl());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).thenApply(result -> {
                Boolean resultScan = downloadService.virusScanByteArray(result);
                if(resultScan){
                    return result;
                }else{
                    ImageDownloadRes imageDownloadRes =
                            new ImageDownloadRes(downloadReq.getUrl(), downloadReq.getS3Path(), false);
                    downloadService.pushToQueueAsync(imageDownloadRes);
                }
                return null;
            }).thenAccept((result) -> {
                downloadService.uploadToS3(result);
            }).exceptionally(e -> {
                e.printStackTrace();
                return null;
            }).thenRunAsync(() -> {
                ImageDownloadRes imageDownloadRes =
                        new ImageDownloadRes(downloadReq.getUrl(), downloadReq.getS3Path(), true);
                downloadService.pushToQueueAsync(imageDownloadRes);
            }).thenRun(() -> {
                try {
                    downloadService.throwFileNotFoundException();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            })
            .thenRun(() -> {
                downloadService.throwThrowable();
            }).thenRun(() -> {
                LOG.info("Ran after exception was thrown !");
            });

            completableFutures.add(completableFuture);
        }

        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()])).join();
        LOG.info("Comp. Futures Have Finished");
    }

    public void triggerCompleteFuture() throws Exception{

        List<CompletableFuture<Void>> completableFutures = new ArrayList<>();
        List<ImageDownloadReq> downloadReqs = new ArrayList<>();

        for(int i = 0; i < 20; i++){
            ImageDownloadReq imageDownloadReq = new ImageDownloadReq("https://..com/img.jpg_" + i, "/s3path/path");
            downloadReqs.add(imageDownloadReq);
        }

        for(ImageDownloadReq downloadReq: downloadReqs){
            CompletableFuture<Void> future = downloadService.downloadFromUrlAsync(downloadReq.getUrl())
                    .exceptionally(e -> {
                        System.out.println(e.getMessage());
                        return null;
                    })
                    .thenApply(result -> {
                        Boolean resultScan = downloadService.virusScanByteArray(result);
                        if(resultScan){
                            return result;
                        }
                        return null;
                    }).thenAccept((result) -> {
                        try{
                            downloadService.uploadToS3(result);
                        }catch(Exception e){}
                    }).thenRunAsync(() -> {
                        ImageDownloadRes imageDownloadRes =
                                new ImageDownloadRes(downloadReq.getUrl(), downloadReq.getS3Path(), true);
                        downloadService.pushToQueue(imageDownloadRes);
                    }).exceptionally(e -> {
                        System.out.println(e.getMessage());
                        return null;
                    });

            completableFutures.add(future);
        }


        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()])).join();
        LOG.info("Comp. Futures Have Finished");


    }


    @Override
    public void run(String... args) throws Exception {

        triggerCompleteFutureTwo();


        // Start the clock
//        long start = System.currentTimeMillis();

//        CompletableFuture<Void> completableFuture = CompletableFuture
//                .runAsync(() -> {
//                    try{
//                        movieService.uploadMovieToDatabase();
//                    }catch(Exception e){
//                        LOG.error("Error uploading movie to DB : ", e);
//                    }
//                }
//        ).thenRunAsync(() -> {
//            try{
//                movieService.lookForMovie("1");
//                LOG.info("Hello from the thenRun() supplier");
//            }catch(Exception e){}
//        }).thenRunAsync(() -> {
//            try{
//                movieService.lookForMovie("1");
//                LOG.info("Hello from the thenRun()-2 supplier");
//            }catch(Exception e){}
//        }).thenRun(() -> {
//            try{
//                movieService.checkVirusScanner();
//                LOG.info("Hello from the virus scanner");
//            }catch(Exception e){}
//
//        }).thenRun(() -> {
//            try{
//                movieService.checkVirusScanner();
//                LOG.info("Hello from the virus scanner -2");
//            }catch(Exception e){}
//
//        });

//        completableFuture.get();
        // Print results, including elapsed time
//        LOG.info("Elapsed time: " + (System.currentTimeMillis() - start));

//        List<CompletableFuture<Void>> completableFutures = new ArrayList<>();
//
//        for(int i = 0; i < 10; i++){
//            Integer age = -1;
//            CompletableFuture<String> maturityFuture = CompletableFuture.supplyAsync(() -> {
//                if(age < 0) {
//                    throw new IllegalArgumentException("Age can not be negative");
//                }
//                if(age > 18) {
//                    return "Adult";
//                } else {
//                    return "Child";
//                }
//            }).exceptionally(ex -> {
//                System.out.println("Oops! We have an exception - " + ex.getMessage());
//                return "Unknown!";
//            });
//
//            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {
//                try{
//                    movieService.checkVirusScanner();
//                    LOG.info("Hello from the virus scanner - Sync");
//                    return "Virus Scanner Synch String";
//                }catch(Exception e){}
//                return null;
//            }).thenApply((value) -> {
//                try{
//                    movieService.uploadMovieToDatabase();
//                    LOG.info("Hello from the upload movie to database - Sync - " + value);
//                }catch(Exception e){}
//                return null;
//            }).thenApplyAsync((value) -> {
//                try{
//                    movieService.lookForMovie("1");
//                    LOG.info("Hello from the lookup movie - Apply ASync - ");
//                }catch(Exception e){}
//                return null;
//            }).thenAcceptAsync(value -> {
//                try{
//                    movieService.lookForMovie("1");
//                    LOG.info("Hello from the lookup movie - Accept ASync - ");
//                }catch(Exception e){}
//
//            }).thenRunAsync(() -> {
//                try{
//                    movieService.lookForMovie("2");
//                }catch(Exception e){}
//            }).thenRun(() -> {
//                throw new IllegalArgumentException("Age can not be negative");
//            });
//
//            completableFutures.add(voidCompletableFuture);
//        }
//
//        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()])).join();
//        LOG.info("Comp. Futures Have Finished");

    }

}
