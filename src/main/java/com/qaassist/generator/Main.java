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
        long startTime = System.currentTimeMillis();

        ConsolePrinter.printBanner();
        TemplateRegistry registry = new TemplateRegistry();
        TestCaseGeneratorService generatorService = new TestCaseGeneratorService(registry);

        List<TestType> allTestTypes = List.of(TestType.values());

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

            ConsolePrinter.printFeatureHeader(featureType.name(), testCases.size(), "HIGH");

            for (TestCase testCase : testCases) {
                ConsolePrinter.printTestCase(testCase);
            }
        }

        long endTime = System.currentTimeMillis();
        long durationMs = endTime - startTime;

        ConsolePrinter.printSummary(totalFeatureTypes, totalTestCasesGenerated, durationMs);
    }
}
