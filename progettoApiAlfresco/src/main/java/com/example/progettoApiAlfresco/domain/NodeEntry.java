package com.example.progettoApiAlfresco.domain;


import org.apache.james.mime4j.dom.datetime.DateTime;

import com.jayway.jsonpath.internal.filter.ValueNodes.JsonNode;

import lombok.Getter;
import lombok.Setter;


@Getter 
@Setter
public class NodeEntry {

    private String id;

    private String name;

    private String nodeType;

    private boolean isFolder;	

    private boolean isFile  ;	
    
    private boolean isLocked;	

    private DateTime modifiedAt;

    private UserInfo modifiedByUser;

    private DateTime createdAt;

    private UserInfo createdByUser;

    private String parentId;

    private boolean isLink;

    private boolean isFavorite;

    private ContentInfo content;

    private String[] aspectNames;

    private JsonNode properties;

    private String[] allowableOperations;

    private PathInfo path;

    private PermissionsInfo permissions;

    private Definition definition;
}
