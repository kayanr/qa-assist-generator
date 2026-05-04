package com.qaassist.generator;

import java.util.List;

import com.qaassist.generator.engine.TestCaseGeneratorService;
import com.qaassist.generator.engine.model.FeatureType;
import com.qaassist.generator.engine.model.Priority;
import com.qaassist.generator.engine.model.TestCase;
import com.qaassist.generator.engine.model.TestCaseRequest;
import com.qaassist.generator.engine.model.TestType;
import com.qaassist.generator.engine.registry.TemplateRegistry;

public class Main {
    public static void main(String[] args) {
        System.out.println("QA Assist Test Case Generator");

        TemplateRegistry registry = new TemplateRegistry();
        TestCaseGeneratorService generatorService = new TestCaseGeneratorService(registry);

        TestCaseRequest request = new TestCaseRequest(
            "Product Search",
            FeatureType.SEARCH,
            "Testing search functionality with various negative input scenarios",
            Priority.HIGH,
            List.of(TestType.NEGATIVE)
        );

        List<TestCase> testCases = generatorService.generate(request);

        for (TestCase testCase : testCases) {
            System.out.println(testCase);
        }
    }
}
