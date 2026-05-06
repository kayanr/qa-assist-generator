package com.qaassist.generator.engine.template;

import java.util.ArrayList;
import java.util.List;

import com.qaassist.generator.engine.model.TestCase;
import com.qaassist.generator.engine.model.TestCaseRequest;
import com.qaassist.generator.engine.model.TestType;

public class SearchTemplate implements TestCaseTemplate {

    @Override
    public List<TestCase> generate(TestCaseRequest request) {
        List<TestCase> results = new ArrayList<>();

        if (request.getTestTypes().contains(TestType.HAPPY_PATH)) {
            results.add(new TestCase(
                "SEARCH-HP-001",
                "Search with valid keyword",
                "User is on the search page. The system contains data that matches the search keyword.",
                "1. Enter a valid keyword into the search field.\n" +
                "2. Click the Search button or press Enter.",
                "System returns a list of relevant results matching the keyword. Results are displayed correctly with expected data.",
                TestType.HAPPY_PATH,
                request.getPriority()
            ));
        }

        if (request.getTestTypes().contains(TestType.NEGATIVE)) {
            results.add(new TestCase(
                "SEARCH-NEG-001",
                "Search returns no results",
                "User is on the search page. The system does not contain data matching the entered keyword.",
                "1. Enter a keyword that has no matching records.\n" +
                "2. Click the Search button or press Enter.",
                "System displays a \"No results found\" message. No errors occur. UI remains stable.",
                TestType.NEGATIVE,
                request.getPriority()
            ));

            results.add(new TestCase(
                "SEARCH-NEG-002",
                "Search with empty query",
                "User is on the search page. Search field is empty.",
                "1. Leave the search field empty.\n" +
                "2. Click the Search button or press Enter.",
                "System prevents search or displays a validation message indicating the field is required.",
                TestType.NEGATIVE,
                request.getPriority()
            ));

            results.add(new TestCase(
                "SEARCH-NEG-003",
                "Search with whitespace only",
                "User is on the search page. Search field contains only spaces.",
                "1. Enter one or more space characters into the search field.\n" +
                "2. Click the Search button or press Enter.",
                "System treats whitespace-only input the same as an empty query. Search is prevented or a validation message is shown. No backend search is triggered.",
                TestType.NEGATIVE,
                request.getPriority()
            ));
        }

        if (request.getTestTypes().contains(TestType.BOUNDARY)) {
            results.add(new TestCase(
                "SEARCH-BND-001",
                "Search with maximum length query",
                "User is on the search page. The system enforces a maximum character limit for search input.",
                "1. Enter a query string at the maximum allowed character length.\n" +
                "2. Click the Search button or press Enter.",
                "System processes the query successfully without errors. Results are returned if applicable. No truncation or performance degradation occurs.",
                TestType.BOUNDARY,
                request.getPriority()
            ));
        }

        if (request.getTestTypes().contains(TestType.EDGE)) {
            results.add(new TestCase(
                "SEARCH-EDG-001",
                "Search with special characters",
                "User is on the search page. Search field accepts general text input.",
                "1. Enter special characters (e.g. @#$%^&*()) into the search field.\n" +
                "2. Click the Search button or press Enter.",
                "System handles input safely. No crash or UI break occurs. Results are either empty or properly handled.",
                TestType.EDGE,
                request.getPriority()
            ));

            results.add(new TestCase(
                "SEARCH-EDG-002",
                "Search with SQL injection input",
                "User is on the search page. Application interacts with a backend database.",
                "1. Enter input like ' OR 1=1 -- into the search field.\n" +
                "2. Click the Search button or press Enter.",
                "System treats input as plain text. No unauthorized data is returned. Query is sanitized or parameterized. No database error is exposed.",
                TestType.EDGE,
                request.getPriority()
            ));

            results.add(new TestCase(
                "SEARCH-EDG-003",
                "Search with script injection input",
                "User is on the search page. Application renders search results in the UI.",
                "1. Enter <script>alert('test')</script> into the search field.\n" +
                "2. Click the Search button or press Enter.",
                "Script is not executed. Input is escaped or sanitized. No alert or script execution occurs. Application remains secure.",
                TestType.EDGE,
                request.getPriority()
            ));
        }

        return results;
    }
}
