package com.qaassist.generator.engine.model;

import java.util.List;
public class TestCaseRequest {
    private String featureName;
    private FeatureType featureType;
    private String description;
    private Priority priority;
    private List<TestType> testTypes;


    public TestCaseRequest(String featureName, FeatureType featureType, String description, Priority priority, List<TestType> testTypes) {
        this.featureName = featureName;
        this.featureType = featureType;
        this.description = description;
        this.priority = priority;
        this.testTypes = testTypes;
    }

    public String getFeatureName() {
        return featureName;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public List<TestType> getTestTypes() {
        return testTypes;
    }
}
