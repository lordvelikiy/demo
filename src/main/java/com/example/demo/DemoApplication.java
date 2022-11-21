package com.example.demo;

import com.example.demo.model.Page;
import com.example.demo.workFiles.PageWrapper;
import com.example.demo.workFiles.CreatorOfSiteMap;
import com.example.demo.model.PageRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.*;
import java.util.concurrent.ForkJoinPool;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
    @Autowired
    PageRepo pageRepo;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Override
    public void run(String... args)  {
        String url = "http://www.playback.ru/";

        Page root = PageWrapper.firstPage(url);


        Set<Page> set = new ForkJoinPool().invoke(new CreatorOfSiteMap(root));


        pageRepo.saveAll(set);
    }
}
