package com.qaassist.generator.engine.model;

public enum FeatureType {
    UPLOAD("File Upload"),
    LOGIN("User Login"),
    SEARCH("Search"),
    REGISTER("User Registration"),
    API("API Endpoint"),
    FORM("Form Submission");

    private final String displayName;

    FeatureType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
