package com.example.progettoApiAlfresco.domain;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class Constraint {

    private String id;
    private String type;
    private String title;
    private String description;
    private JsonNode parameters;

}
