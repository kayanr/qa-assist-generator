package com.qaassist.generator.engine.model;

public class TestCase {
    private String id;
    private String title;
    private String precondition;
    private String steps;
    private String expectedResult;
    private TestType testType;
    private Priority priority;
    private String httpMethod;
    private String endpoint;
    private String selector;

    // Standard constructor — httpMethod, endpoint, selector default to null
    public TestCase(String id, String title, String precondition, String steps, String expectedResult, TestType testType, Priority priority) {
        this.id = id;
        this.title = title;
        this.precondition = precondition;
        this.steps = steps;
        this.expectedResult = expectedResult;
        this.testType = testType;
        this.priority = priority;
        this.httpMethod = null;
        this.endpoint = null;
        this.selector = null;
    }

    // Full constructor — used by ApiTemplate (httpMethod + endpoint) and UI templates (selector)
    public TestCase(String id, String title, String precondition, String steps, String expectedResult, TestType testType, Priority priority, String httpMethod, String endpoint, String selector) {
        this.id = id;
        this.title = title;
        this.precondition = precondition;
        this.steps = steps;
        this.expectedResult = expectedResult;
        this.testType = testType;
        this.priority = priority;
        this.httpMethod = httpMethod;
        this.endpoint = endpoint;
        this.selector = selector;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getPrecondition() { return precondition; }
    public String getSteps() { return steps; }
    public String getExpectedResult() { return expectedResult; }
    public TestType getTestType() { return testType; }
    public Priority getPriority() { return priority; }
    public String getHttpMethod() { return httpMethod; }
    public String getEndpoint() { return endpoint; }
    public String getSelector() { return selector; }

    @Override
    public String toString() {
        return "ID: " + id + "\n" +
               "Title: " + title + "\n" +
               "Type: " + testType + "\n" +
               "Priority: " + priority + "\n" +
               "Precondition: " + precondition + "\n" +
               "Steps: " + steps + "\n" +
               "Expected: " + expectedResult + "\n";
    }
}
