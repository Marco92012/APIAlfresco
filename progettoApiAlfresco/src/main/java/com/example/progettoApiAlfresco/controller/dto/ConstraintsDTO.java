package com.example.progettoApiAlfresco.controller.dto;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConstraintsDTO {

    private String id;

    private String type;

    private String title;

    private String description;

    private JsonNode parameters;
}
