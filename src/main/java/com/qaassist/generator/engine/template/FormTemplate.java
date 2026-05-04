package com.qaassist.generator.engine.template;

import java.util.ArrayList;
import java.util.List;

import com.qaassist.generator.engine.model.TestCase;
import com.qaassist.generator.engine.model.TestCaseRequest;
import com.qaassist.generator.engine.model.TestType;

public class FormTemplate implements TestCaseTemplate {

    @Override
    public List<TestCase> generate(TestCaseRequest request) {
        List<TestCase> results = new ArrayList<>();

        if (request.getTestTypes().contains(TestType.HAPPY_PATH)) {
            results.add(new TestCase(
                "FORM-001",
                "Submit form with valid data",
                "User is on the form page. All required fields are visible and enabled. Valid test data is available.",
                "1. Enter valid data in all required fields.\n" +
                "2. Enter valid data in optional fields if applicable.\n" +
                "3. Click Submit.",
                "Form submits successfully. Success message or confirmation is displayed. Data is saved or processed correctly.",
                TestType.HAPPY_PATH,
                request.getPriority()
            ));
        }

        if (request.getTestTypes().contains(TestType.NEGATIVE)) {
            results.add(new TestCase(
                "FORM-002",
                "Submit form with required fields empty",
                "User is on the form page. One or more fields are required.",
                "1. Leave all required fields empty.\n" +
                "2. Click Submit.",
                "Form submission is prevented. Required field validation messages are displayed. No incomplete record is created.",
                TestType.NEGATIVE,
                request.getPriority()
            ));

            results.add(new TestCase(
                "FORM-003",
                "Submit form with invalid email format",
                "User is on the form page. Form contains an email field with format validation.",
                "1. Enter an invalid email such as userexample.com.\n" +
                "2. Complete the remaining required fields with valid data.\n" +
                "3. Click Submit.",
                "System rejects the email value. Validation message explains the email format is invalid. Form is not submitted.",
                TestType.NEGATIVE,
                request.getPriority()
            ));
        }

        if (request.getTestTypes().contains(TestType.BOUNDARY)) {
            results.add(new TestCase(
                "FORM-004",
                "Submit form with maximum length field input",
                "User is on the form page. One or more text fields have a maximum allowed length.",
                "1. Enter text exactly at the maximum allowed length.\n" +
                "2. Complete all other required fields with valid data.\n" +
                "3. Click Submit.",
                "System accepts the value without truncation or error. Form submits successfully if all inputs are valid.",
                TestType.BOUNDARY,
                request.getPriority()
            ));
        }

        if (request.getTestTypes().contains(TestType.EDGE)) {
            results.add(new TestCase(
                "FORM-005",
                "Submit form with special characters in fields",
                "User is on the form page. Text fields accept user-entered input.",
                "1. Enter special characters such as @#$%^&*() in a text field.\n" +
                "2. Complete remaining required fields with valid data.\n" +
                "3. Click Submit.",
                "System handles input safely. It either accepts allowed characters or shows a clear validation message. No crash or broken UI occurs.",
                TestType.EDGE,
                request.getPriority()
            ));

            results.add(new TestCase(
                "FORM-006",
                "Submit form multiple times rapidly",
                "User is on the form page. All required fields contain valid data. Submit button is enabled.",
                "1. Fill out the form with valid data.\n" +
                "2. Click Submit multiple times quickly.\n" +
                "3. Observe system behavior.",
                "System prevents duplicate submissions. Only one record or request is processed. Submit button may become disabled or show loading state.",
                TestType.EDGE,
                request.getPriority()
            ));

            results.add(new TestCase(
                "FORM-007",
                "Submit form with SQL injection input",
                "User is on the form page. Form data is saved or processed by a backend service/database.",
                "1. Enter SQL injection text such as ' OR 1=1 -- into a text field.\n" +
                "2. Complete remaining required fields.\n" +
                "3. Click Submit.",
                "Input is treated as plain text or rejected safely. No unauthorized database action occurs. No database error or stack trace is exposed.",
                TestType.EDGE,
                request.getPriority()
            ));

            results.add(new TestCase(
                "FORM-008",
                "Submit form with script injection input",
                "User is on the form page. Submitted data may later be displayed in the UI.",
                "1. Enter <script>alert('test')</script> into a text field.\n" +
                "2. Complete remaining required fields.\n" +
                "3. Click Submit.",
                "Script is not executed. Input is escaped, sanitized, or rejected. Application remains stable and secure.",
                TestType.EDGE,
                request.getPriority()
            ));
        }

        return results;
    }
}
