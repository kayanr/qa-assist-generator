package com.qaassist.generator.engine.model;

public enum TestType {
    HAPPY_PATH("Happy Path"),
    NEGATIVE("Negative"),
    BOUNDARY("Boundary"),
    EDGE("Edge Case");

    private final String displayName;

    TestType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
