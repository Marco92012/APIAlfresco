package com.example.progettoApiAlfresco.domain;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class ContentInfo {

    private String mimeType;

    private String mimeTypeName;

    private int sizeInBytes;

    private String encoding;
}
