package com.qaassist.generator.engine;

import java.util.List;

import com.qaassist.generator.engine.model.TestCase;
import com.qaassist.generator.engine.model.TestCaseRequest;
import com.qaassist.generator.engine.registry.TemplateRegistry;
import org.springframework.stereotype.Service;

@Service
public class TestCaseGeneratorService {
    private TemplateRegistry registry;

    public TestCaseGeneratorService(TemplateRegistry registry) {
        this.registry = registry;
    }

    public List<TestCase> generate(TestCaseRequest request) {
        return registry.getTemplate(request.getFeatureType()).generate(request);
    }
}
