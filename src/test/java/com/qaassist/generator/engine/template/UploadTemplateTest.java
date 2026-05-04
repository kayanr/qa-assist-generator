package com.qaassist.generator.engine.template;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import com.qaassist.generator.engine.model.*;

class UploadTemplateTest {

    @Test
    void happyPathGeneratesOneCase() {
        // Arrange
        UploadTemplate template = new UploadTemplate();
        TestCaseRequest request = new TestCaseRequest(
            "File Upload",
            FeatureType.UPLOAD,
            "test",
            Priority.HIGH,
            List.of(TestType.HAPPY_PATH)
        );

        // Act
        List<TestCase> results = template.generate(request);

        // Assert
        assertEquals(1, results.size());
        assertEquals("UPLOAD-001", results.get(0).getId());
    }

    @Test
    void negativeCasesGeneratesTwoCases() {
        // Arrange
        UploadTemplate template = new UploadTemplate();
        TestCaseRequest request = new TestCaseRequest(
            "File Upload",
            FeatureType.UPLOAD,
            "test",
            Priority.HIGH,
            List.of(TestType.NEGATIVE)
        );

        // Act
        List<TestCase> results = template.generate(request);

        // Assert
        assertEquals(2, results.size());
        assertEquals("UPLOAD-002", results.get(0).getId());
    }
    @Test
    void edgeCaseGeneratesOneCase() {
        // Arrange
        UploadTemplate template = new UploadTemplate();
        TestCaseRequest request = new TestCaseRequest(
            "File Upload",
            FeatureType.UPLOAD,
            "test",
            Priority.HIGH,
            List.of(TestType.EDGE)
        );

        // Act
        List<TestCase> results = template.generate(request);

        // Assert
        assertEquals(1, results.size());
        assertEquals("UPLOAD-006", results.get(0).getId());
    }


}
