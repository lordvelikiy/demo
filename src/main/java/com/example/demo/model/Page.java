package com.example.demo.model;


import lombok.Data;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;

@Data
@Entity
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String path;
    @Column(nullable = false)
    private int responseCodes;
    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String content;

    public Page() {
    }


    public Page(String path, String content, int code) {
        this.path = path;
        this.responseCodes = code;
        this.content = content;

    }
}
