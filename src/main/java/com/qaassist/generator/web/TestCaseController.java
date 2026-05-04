package com.qaassist.generator.web;

import com.qaassist.generator.engine.TestCaseGeneratorService;
import com.qaassist.generator.engine.model.FeatureType;
import com.qaassist.generator.engine.model.Priority;
import com.qaassist.generator.engine.model.TestCaseRequest;
import com.qaassist.generator.engine.model.TestType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TestCaseController {

    private final TestCaseGeneratorService generatorService;

    public TestCaseController(TestCaseGeneratorService generatorService) {
        this.generatorService = generatorService;
    }

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("featureTypes", FeatureType.values());
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("testTypes", TestType.values());
        return "index";
    }

    @PostMapping("/generate")
    public String generate(
            @RequestParam String featureName,
            @RequestParam FeatureType featureType,
            @RequestParam(required = false, defaultValue = "") String description,
            @RequestParam Priority priority,
            @RequestParam List<TestType> testTypes,
            Model model) {

        TestCaseRequest request = new TestCaseRequest(
                featureName, featureType, description, priority, testTypes);

        model.addAttribute("testCases", generatorService.generate(request));
        model.addAttribute("featureName", featureName);
        model.addAttribute("featureType", featureType);
        model.addAttribute("priority", priority);
        return "results";
    }
}
