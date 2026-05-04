package com.qaassist.generator;

import java.util.Arrays;
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
            long startTime = System.currentTimeMillis();

        System.out.println("================================================");
        System.out.println("   QA ASSIST GENERATOR — ENGINE DEMO");
        System.out.println("   Version 0.1.0");
        System.out.println("================================================");

        TemplateRegistry registry = new TemplateRegistry();
        TestCaseGeneratorService generatorService = new TestCaseGeneratorService(registry);

        List<TestType> allTestTypes = Arrays.asList(TestType.values());

        int totalFeatureTypes = 0;
        int totalTestCasesGenerated = 0;

        for (FeatureType featureType : FeatureType.values()) {
            TestCaseRequest request = new TestCaseRequest(
                    featureType + " Feature",
                    featureType,
                    "Auto-generated test cases for " + featureType,
                    Priority.HIGH,
                    allTestTypes
            );

            List<TestCase> testCases = generatorService.generate(request);

            totalFeatureTypes++;
            totalTestCasesGenerated += testCases.size();

            System.out.println("\n========================================");
            System.out.println("Feature Type: " + featureType + " (" + testCases.size() + " test cases)");
            System.out.println("========================================");

            for (TestCase testCase : testCases) {
                System.out.println(testCase);
            }
        }

        long endTime = System.currentTimeMillis();
        long durationMs = endTime - startTime;

        System.out.println("\n===== END OF GENERATION =====");
        System.out.println("Total feature types: " + totalFeatureTypes);
        System.out.println("Total test cases generated: " + totalTestCasesGenerated);
        System.out.println("Generated " + totalTestCasesGenerated + " test cases in " + durationMs + "ms");
    }
}
