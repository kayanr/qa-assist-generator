package com.qaassist.generator.engine.model;

public class TestCase {
    private String id;
    private String title;
    private String precondition;
    private String steps;
    private String expectedResult;
    private TestType testType;
    private Priority priority;

    public TestCase(String id, String title, String precondition, String steps, String expectedResult, TestType testType, Priority priority) {
        this.id = id;
        this.title = title;
        this.precondition = precondition;
        this.steps = steps;
        this.expectedResult = expectedResult;
        this.testType = testType;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPrecondition() {
        return precondition;
    }

    public String getSteps() {
        return steps;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public TestType getTestType() {
        return testType;
    }

    public Priority getPriority() {
        return priority;
    }   

}
