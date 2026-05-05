package com.qaassist.generator.web;

import com.qaassist.generator.engine.KeywordDetector;
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

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.qaassist.generator.engine.KeywordDetector;


@Controller
public class TestCaseController {

    private final TestCaseGeneratorService generatorService;
    private final KeywordDetector keywordDetector;

    public TestCaseController(TestCaseGeneratorService generatorService,
                            KeywordDetector keywordDetector) {
        this.generatorService = generatorService;
        this.keywordDetector = keywordDetector;
    }


    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("featureTypes", FeatureType.values());
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("testTypes", TestType.values());
        return "index";
    }

    @PostMapping("/detect")
    public String detect(
            @RequestParam("text") String text,
            Model model) {

        model.addAttribute("featureTypes", FeatureType.values());
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("testTypes", TestType.values());

        FeatureType detected = keywordDetector.detect(text);

        if (detected != null) {
            model.addAttribute("detectedType", detected);
        } else {
            model.addAttribute("detectionFailed", true);
        }

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

@PostMapping("/download-excel")
public void downloadExcel(
        @RequestParam("featureName") String featureName,
        @RequestParam("featureType") FeatureType featureType,
        @RequestParam(name = "description", required = false, defaultValue = "") String description,
        @RequestParam("priority") Priority priority,
        @RequestParam("testTypes") List<TestType> testTypes,
        HttpServletResponse response) throws IOException {

    TestCaseRequest request = new TestCaseRequest(
            featureName, featureType, description, priority, testTypes);

    List<TestCase> testCases = generatorService.generate(request);

    try (XSSFWorkbook workbook = new XSSFWorkbook()) {
        XSSFSheet sheet = workbook.createSheet("Test Cases");

        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(boldFont);

        String[] headers = {"ID", "Title", "Test Type", "Priority", "Precondition", "Steps", "Expected Result"};
        XSSFRow headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
            headerRow.getCell(i).setCellStyle(headerStyle);
        }

        int rowNum = 1;
        for (TestCase tc : testCases) {
            XSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(tc.getId());
            row.createCell(1).setCellValue(tc.getTitle());
            row.createCell(2).setCellValue(tc.getTestType().name());
            row.createCell(3).setCellValue(tc.getPriority().name());
            row.createCell(4).setCellValue(tc.getPrecondition());
            row.createCell(5).setCellValue(tc.getSteps());
            row.createCell(6).setCellValue(tc.getExpectedResult());
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition",
                "attachment; filename=\"" + featureName.replaceAll("\\s+", "_") + "_test_cases.xlsx\"");

        workbook.write(response.getOutputStream());
    }



}

}
