package com.example.progettoApiAlfresco.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeBodyMove {

    @NotBlank
    private String targetParentId;

    private String name;
}
