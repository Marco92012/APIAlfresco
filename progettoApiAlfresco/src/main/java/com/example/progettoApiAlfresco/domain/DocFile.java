package com.example.progettoApiAlfresco.domain;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocFile {

    private String filename;
    private Long size;
    private byte[] content;
    private Map<String, String> metadata = new HashMap<>();
}
