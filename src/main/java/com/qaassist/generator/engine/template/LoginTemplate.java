package com.qaassist.generator.engine.template;

import java.util.ArrayList;
import java.util.List;

import com.qaassist.generator.engine.model.TestCase;
import com.qaassist.generator.engine.model.TestCaseRequest;
import com.qaassist.generator.engine.model.TestType;

public class LoginTemplate implements TestCaseTemplate {

    @Override
    public List<TestCase> generate(TestCaseRequest request) {
        List<TestCase> results = new ArrayList<>();

        if (request.getTestTypes().contains(TestType.HAPPY_PATH)) {
            results.add(new TestCase(
                "LOGIN-HP-001",
                "Login with valid credentials",
                "User is on the login page. A registered user account exists with valid email and password.",
                "1. Enter a valid registered email.\n" +
                "2. Enter the correct password.\n" +
                "3. Click the Login button.",
                "User is successfully authenticated. User is redirected to the dashboard or home page. No error messages are displayed.",
                TestType.HAPPY_PATH,
                request.getPriority()
            ));
        }

        if (request.getTestTypes().contains(TestType.NEGATIVE)) {
            results.add(new TestCase(
                "LOGIN-NEG-001",
                "Login with wrong password",
                "User is on the login page. A registered account exists.",
                "1. Enter a valid registered email.\n" +
                "2. Enter an incorrect password.\n" +
                "3. Click the Login button.",
                "System denies access. Error message such as \"Invalid credentials\" is displayed. User remains on login page.",
                TestType.NEGATIVE,
                request.getPriority()
            ));

            results.add(new TestCase(
                "LOGIN-NEG-002",
                "Login with empty fields",
                "User is on the login page. Email and password fields are required.",
                "1. Leave email field empty.\n" +
                "2. Leave password field empty.\n" +
                "3. Click the Login button.",
                "System prevents submission. Validation messages indicate required fields. No login attempt is made.",
                TestType.NEGATIVE,
                request.getPriority()
            ));

            results.add(new TestCase(
                "LOGIN-NEG-003",
                "Login with unregistered email",
                "User is on the login page. No account exists for the entered email.",
                "1. Enter an unregistered email address.\n" +
                "2. Enter any password.\n" +
                "3. Click the Login button.",
                "System denies access. Error message indicates account not found or invalid credentials. User remains on login page.",
                TestType.NEGATIVE,
                request.getPriority()
            ));

            results.add(new TestCase(
                "LOGIN-NEG-004",
                "Trigger account lock by exceeding failed attempt limit",
                "User is on the login page. A valid registered account exists. The system enforces an account lockout policy after N failed attempts.",
                "1. Enter the valid registered email.\n" +
                "2. Enter an incorrect password.\n" +
                "3. Repeat steps 1-2 until the lockout threshold is reached (e.g. 5 attempts).\n" +
                "4. Observe system behaviour at and after the threshold.",
                "System locks the account at the defined threshold. Subsequent login attempts are blocked regardless of password correctness. Lockout message is displayed. Account lock is logged.",
                TestType.NEGATIVE,
                request.getPriority()
            ));
        }

        if (request.getTestTypes().contains(TestType.BOUNDARY)) {
            results.add(new TestCase(
                "LOGIN-BND-001",
                "Login with maximum length password",
                "User is on the login page. A valid account exists with a password at the maximum allowed length.",
                "1. Enter valid registered email.\n" +
                "2. Enter a password exactly at the maximum allowed length.\n" +
                "3. Click the Login button.",
                "System accepts the input. User is successfully logged in. No truncation or validation error occurs.",
                TestType.BOUNDARY,
                request.getPriority()
            ));
        }

        if (request.getTestTypes().contains(TestType.EDGE)) {
            results.add(new TestCase(
                "LOGIN-EDG-001",
                "Login with locked account",
                "User is on the login page. The account has been locked due to multiple failed login attempts or admin action.",
                "1. Enter the email of a locked account.\n" +
                "2. Enter the correct password.\n" +
                "3. Click the Login button.",
                "System denies access. Message indicates account is locked. User is advised to contact support or reset password. No login occurs.",
                TestType.EDGE,
                request.getPriority()
            ));
        }

        return results;
    }
}
