package com.qaassist.generator.web;

import com.qaassist.generator.engine.TestCaseGeneratorService;
import com.qaassist.generator.engine.model.FeatureType;
import com.qaassist.generator.engine.model.Priority;
import com.qaassist.generator.engine.model.TestCase;
import com.qaassist.generator.engine.model.TestCaseRequest;
import com.qaassist.generator.engine.model.TestType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            @RequestParam("featureName") String featureName,
            @RequestParam("featureType") FeatureType featureType,
            @RequestParam(name = "description", required = false, defaultValue = "") String description,
            @RequestParam("priority") Priority priority,
            @RequestParam("testTypes") List<TestType> testTypes,
            Model model) {

        TestCaseRequest request = new TestCaseRequest(
                featureName, featureType, description, priority, testTypes);

        model.addAttribute("testCases", generatorService.generate(request));
        model.addAttribute("featureName", featureName);
        model.addAttribute("featureType", featureType);
        model.addAttribute("priority", priority);
        model.addAttribute("description", description);
        model.addAttribute("selectedTestTypes", testTypes);

        return "results";
    }

    @PostMapping("/download-csv")
    public void downloadCsv(
            @RequestParam("featureName") String featureName,
            @RequestParam("featureType") FeatureType featureType,
            @RequestParam(name = "description", required = false, defaultValue = "") String description,
            @RequestParam("priority") Priority priority,
            @RequestParam("testTypes") List<TestType> testTypes,
            HttpServletResponse response) throws IOException {

        TestCaseRequest request = new TestCaseRequest(
            featureName, featureType, description, priority, testTypes);

    List<TestCase> testCases = generatorService.generate(request);

    response.setContentType("text/csv");
    response.setHeader("Content-Disposition",
            "attachment; filename=\"" + featureName.replaceAll("\\s+", "_") + "_test_cases.csv\"");

    var writer = response.getWriter();
    writer.println("ID,Title,Test Type,Priority,Precondition,Steps,Expected Result");

    for (TestCase tc : testCases) {
        writer.printf("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"%n",
                tc.getId(),
                tc.getTitle(),
                tc.getTestType(),
                tc.getPriority(),
                tc.getPrecondition(),
                tc.getSteps().replace("\"", "\"\""),
                tc.getExpectedResult().replace("\"", "\"\""));
    }
}

}
