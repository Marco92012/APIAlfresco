package com.example.progettoApiAlfresco.domain;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class PermissionsInfo {

    private boolean isInheritanceEnabled;
    private PermissionElement inherited;
    private PermissionElement locallySet;
    private String[] settable;

}
