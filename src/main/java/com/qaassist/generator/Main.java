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

        // All test types selected
        List<TestType> allTestTypes = List.of(TestType.values());

        for (FeatureType featureType : FeatureType.values()) {

            System.out.println("\n========================================");
            System.out.println("Feature Type: " + featureType);
            System.out.println("========================================");

            TestCaseRequest request = new TestCaseRequest(
                    featureType + " Feature",
                    featureType,
                    "Auto-generated test cases for " + featureType,
                    Priority.HIGH,
                    allTestTypes
            );

            List<TestCase> testCases = generatorService.generate(request);

            for (TestCase testCase : testCases) {
                System.out.println(testCase);
            }
        }

        System.out.println("\n===== END OF GENERATION =====");
    }
}
