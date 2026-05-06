package com.qaassist.generator.engine.template;

import java.util.ArrayList;
import java.util.List;

import com.qaassist.generator.engine.model.TestCase;
import com.qaassist.generator.engine.model.TestCaseRequest;
import com.qaassist.generator.engine.model.TestType;

public class RegisterTemplate implements TestCaseTemplate {

    @Override
    public List<TestCase> generate(TestCaseRequest request) {
        List<TestCase> results = new ArrayList<>();

        if (request.getTestTypes().contains(TestType.HAPPY_PATH)) {
            results.add(new TestCase(
                "REGISTER-HP-001",
                "Register with valid data",
                "User is on the registration page. No existing account uses the provided email. All required fields are available.",
                "1. Enter valid username.\n" +
                "2. Enter valid email.\n" +
                "3. Enter strong password meeting requirements.\n" +
                "4. Confirm password if applicable.\n" +
                "5. Click Register or Submit.",
                "Account is created successfully. User is redirected to login/dashboard or sees a success message. Data is stored correctly.",
                TestType.HAPPY_PATH,
                request.getPriority()
            ));
        }

        if (request.getTestTypes().contains(TestType.NEGATIVE)) {
            results.add(new TestCase(
                "REGISTER-NEG-001",
                "Register with duplicate email",
                "User is on the registration page. An account already exists with the entered email.",
                "1. Enter valid username.\n" +
                "2. Enter an email that already exists.\n" +
                "3. Enter valid password.\n" +
                "4. Click Register.",
                "System rejects registration. Error message indicates email already in use. No duplicate account is created.",
                TestType.NEGATIVE,
                request.getPriority()
            ));

            results.add(new TestCase(
                "REGISTER-NEG-002",
                "Register with weak password",
                "User is on the registration page. Password policy is enforced (e.g., min length, uppercase, number, special char).",
                "1. Enter valid username.\n" +
                "2. Enter valid email.\n" +
                "3. Enter weak password (e.g., \"12345\").\n" +
                "4. Click Register.",
                "System rejects submission. Clear validation message explains password requirements. No account is created.",
                TestType.NEGATIVE,
                request.getPriority()
            ));

            results.add(new TestCase(
                "REGISTER-NEG-003",
                "Register with missing required fields",
                "User is on the registration page. Required fields include username, email, and password.",
                "1. Leave one or more required fields empty.\n" +
                "2. Click Register.",
                "System prevents submission. Required field validation messages appear. No backend request should be processed if client validation exists.",
                TestType.NEGATIVE,
                request.getPriority()
            ));

            results.add(new TestCase(
                "REGISTER-NEG-004",
                "Register with invalid email format",
                "User is on the registration page. Email field requires valid format.",
                "1. Enter invalid email (e.g., useremail.com).\n" +
                "2. Enter valid username and password.\n" +
                "3. Click Register.",
                "System rejects input. Validation message indicates invalid email format. No account is created.",
                TestType.NEGATIVE,
                request.getPriority()
            ));
        }

        if (request.getTestTypes().contains(TestType.BOUNDARY)) {
            results.add(new TestCase(
                "REGISTER-BND-001",
                "Register with maximum length username",
                "User is on the registration page. System enforces a maximum username length.",
                "1. Enter username exactly at max allowed length.\n" +
                "2. Enter valid email.\n" +
                "3. Enter valid password.\n" +
                "4. Click Register.",
                "System accepts input. Account is created successfully. No truncation or validation error occurs.",
                TestType.BOUNDARY,
                request.getPriority()
            ));
        }

        if (request.getTestTypes().contains(TestType.EDGE)) {
            results.add(new TestCase(
                "REGISTER-EDG-001",
                "Register with special characters in name",
                "User is on the registration page. Username field accepts text input.",
                "1. Enter username with special characters (e.g., user@123!).\n" +
                "2. Enter valid email.\n" +
                "3. Enter valid password.\n" +
                "4. Click Register.",
                "System either accepts valid allowed characters or rejects invalid ones with a clear validation message. No crash or unexpected behavior occurs.",
                TestType.EDGE,
                request.getPriority()
            ));

            results.add(new TestCase(
                "REGISTER-EDG-002",
                "Register with SQL injection input",
                "User is on the registration page. System stores user data in a database.",
                "1. Enter ' OR 1=1 -- in username or email field.\n" +
                "2. Enter valid password.\n" +
                "3. Click Register.",
                "Input is sanitized/escaped. No database manipulation occurs. Registration fails safely or treats input as plain text. No system error is exposed.",
                TestType.EDGE,
                request.getPriority()
            ));

            results.add(new TestCase(
                "REGISTER-EDG-003",
                "Register with script injection input",
                "User is on the registration page. System displays user data in UI or responses.",
                "1. Enter <script>alert('test')</script> in username field.\n" +
                "2. Enter valid email and password.\n" +
                "3. Click Register.",
                "Script is not executed. Input is sanitized/escaped. No XSS vulnerability occurs. Application remains secure.",
                TestType.EDGE,
                request.getPriority()
            ));
        }

        return results;
    }
}
