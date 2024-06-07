package com.example.progettoApiAlfresco.controller.dto;


import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeDTO{

    private String name;

    private String nodeType;

    private String[] aspectName;

    private JsonNode properties;

    private PermissionDTO permission;

    private DefinitionDTO definition;

    private String relativePath;

    private JsonNode association;

    // private Set<AssociationDTO> secondaryChildren;

    // private Set<AssociationDTO> targets;

    private String targetParentId;
}
