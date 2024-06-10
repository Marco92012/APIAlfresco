package com.example.progettoApiAlfresco.domain;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class PathElement {

    private String id;
    private String name;
    private String nodeType;
    private String[] aspectName;
}
