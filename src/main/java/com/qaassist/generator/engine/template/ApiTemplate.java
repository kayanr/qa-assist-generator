package com.qaassist.generator.engine.template;

import java.util.ArrayList;
import java.util.List;

import com.qaassist.generator.engine.model.TestCase;
import com.qaassist.generator.engine.model.TestCaseRequest;
import com.qaassist.generator.engine.model.TestType;

public class ApiTemplate implements TestCaseTemplate {

    @Override
    public List<TestCase> generate(TestCaseRequest request) {
        List<TestCase> results = new ArrayList<>();

        if (request.getTestTypes().contains(TestType.HAPPY_PATH)) {
            results.add(new TestCase(
                "API-HP-001",
                "Valid request returns 200 OK",
                "API endpoint is available. User/client has valid authentication if required. Request payload and headers are valid.",
                "1. Send a request to the endpoint with valid headers.\n" +
                "2. Include a valid request body or query parameters.\n" +
                "3. Submit the request.",
                "API returns 200 OK. Response body contains expected data. Response format matches the API contract. No error message is returned.",
                TestType.HAPPY_PATH,
                request.getPriority(),
                "GET", "/api/v1/resource", null
            ));
        }

        if (request.getTestTypes().contains(TestType.NEGATIVE)) {
            results.add(new TestCase(
                "API-NEG-001",
                "Request with missing required field returns 400",
                "API endpoint is available. User/client has valid authentication if required. Request schema has at least one required field.",
                "1. Send a request with one required field missing from the payload.\n" +
                "2. Submit the request.",
                "API returns 400 Bad Request. Response includes a clear validation error. No invalid record is created or updated.",
                TestType.NEGATIVE,
                request.getPriority(),
                "POST", "/api/v1/resource", null
            ));

            results.add(new TestCase(
                "API-NEG-002",
                "Request without authentication returns 401",
                "API endpoint requires authentication. User/client does not include a valid token or session.",
                "1. Send a request without an authorization token/header.\n" +
                "2. Submit the request.",
                "API returns 401 Unauthorized. Response indicates authentication is required. Protected data is not returned.",
                TestType.NEGATIVE,
                request.getPriority(),
                "GET", "/api/v1/resource", null
            ));

            results.add(new TestCase(
                "API-NEG-003",
                "Request with insufficient permissions returns 403",
                "API endpoint requires a specific role or permission. User/client is authenticated but does not have the required access level.",
                "1. Authenticate as a user with insufficient permissions.\n" +
                "2. Send a request to the restricted endpoint.\n" +
                "3. Submit the request.",
                "API returns 403 Forbidden. Request is denied. No restricted data is returned or modified.",
                TestType.NEGATIVE,
                request.getPriority(),
                "GET", "/api/v1/resource", null
            ));

            results.add(new TestCase(
                "API-NEG-004",
                "Request for non-existent resource returns 404",
                "API endpoint is available. User/client has valid authentication if required. Requested resource ID does not exist.",
                "1. Send a request using a non-existent resource ID.\n" +
                "2. Submit the request.",
                "API returns 404 Not Found. Response clearly indicates the resource was not found. No unrelated resource is returned.",
                TestType.NEGATIVE,
                request.getPriority(),
                "GET", "/api/v1/resource/{id}", null
            ));
        }

        if (request.getTestTypes().contains(TestType.BOUNDARY)) {
            results.add(new TestCase(
                "API-BND-001",
                "Request with very large payload",
                "API accepts request bodies and has configured payload limits.",
                "1. Send request with an extremely large JSON body or oversized field value.\n" +
                "2. Submit the request.",
                "API rejects oversized payload with 413 Payload Too Large or 400 Bad Request. System remains stable.",
                TestType.BOUNDARY,
                request.getPriority(),
                "POST", "/api/v1/resource", null
            ));
        }

        if (request.getTestTypes().contains(TestType.EDGE)) {
            results.add(new TestCase(
                "API-EDG-001",
                "Request with malformed JSON returns 400",
                "API endpoint accepts JSON request bodies. User/client has valid authentication if required.",
                "1. Send a request with invalid JSON syntax, such as missing braces or quotes.\n" +
                "2. Submit the request.",
                "API returns 400 Bad Request. Response indicates the request body is malformed or unreadable. System does not crash or expose stack traces.",
                TestType.EDGE,
                request.getPriority(),
                "POST", "/api/v1/resource", null
            ));

            results.add(new TestCase(
                "API-EDG-002",
                "Request with SQL injection payload",
                "API accepts user-controlled input and interacts with a database.",
                "1. Send a request with SQL injection text such as ' OR 1=1 -- in a request field.\n" +
                "2. Submit the request.",
                "API treats input as data, not executable SQL. No unauthorized records are returned or changed. No database errors are exposed.",
                TestType.EDGE,
                request.getPriority(),
                "POST", "/api/v1/resource", null
            ));

            results.add(new TestCase(
                "API-EDG-003",
                "Request with script injection payload",
                "API accepts text fields that may later be displayed in a UI.",
                "1. Send a request with <script>alert('test')</script> in a text field.\n" +
                "2. Submit the request.",
                "API rejects, sanitizes, or safely stores the input. Script does not execute when viewed later.",
                TestType.EDGE,
                request.getPriority(),
                "POST", "/api/v1/resource", null
            ));

            results.add(new TestCase(
                "API-EDG-004",
                "Request with invalid or expired token",
                "API endpoint requires authentication.",
                "1. Send request with expired, malformed, or invalid bearer token.\n" +
                "2. Submit the request.",
                "API returns 401 Unauthorized. No protected data is returned. Token error does not expose sensitive details.",
                TestType.EDGE,
                request.getPriority(),
                "GET", "/api/v1/resource", null
            ));

            results.add(new TestCase(
                "API-EDG-005",
                "Request with another user's resource ID",
                "Authenticated user owns one resource. Another resource belongs to a different user.",
                "1. Authenticate as User A.\n" +
                "2. Send request for User B's resource ID.\n" +
                "3. Submit the request.",
                "API returns 403 Forbidden or 404 Not Found. User A cannot access or modify User B's data.",
                TestType.EDGE,
                request.getPriority(),
                "GET", "/api/v1/resource/{id}", null
            ));

            results.add(new TestCase(
                "API-EDG-006",
                "Request with unexpected extra fields",
                "API accepts JSON request body. Server should only bind allowed fields.",
                "1. Send valid JSON plus extra fields such as \"role\": \"ADMIN\" or \"isAdmin\": true.\n" +
                "2. Submit the request.",
                "API ignores or rejects unauthorized fields. Privilege or protected values are not changed.",
                TestType.EDGE,
                request.getPriority(),
                "POST", "/api/v1/resource", null
            ));
        }

        return results;
    }
}
