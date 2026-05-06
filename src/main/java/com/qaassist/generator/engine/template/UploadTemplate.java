package com.qaassist.generator.engine.template;

import java.util.ArrayList;
import java.util.List;

import com.qaassist.generator.engine.model.TestCase;
import com.qaassist.generator.engine.model.TestCaseRequest;
import com.qaassist.generator.engine.model.TestType;

public class UploadTemplate implements TestCaseTemplate {

    @Override
    public List<TestCase> generate(TestCaseRequest request) {
        List<TestCase> results = new ArrayList<>();

        if (request.getTestTypes().contains(TestType.HAPPY_PATH)) {
            results.add(new TestCase(
                "UPLOAD-HP-001",
                "Upload valid file",
                "User is on the upload page. A supported file is available. File size is within the allowed limit.",
                "1. Click the file upload button.\n" +
                "2. Select a valid supported file.\n" +
                "3. Confirm the file name appears.\n" +
                "4. Click Upload or Submit.",
                "File uploads successfully. Success message appears. Uploaded file appears in the expected file list or confirmation area.",
                TestType.HAPPY_PATH,
                request.getPriority()
            ));
        }

        if (request.getTestTypes().contains(TestType.NEGATIVE)) {
            results.add(new TestCase(
                "UPLOAD-NEG-001",
                "Upload unsupported file type",
                "User is on the upload page. An unsupported file type is available, such as .exe, .bat, or another restricted format.",
                "1. Click the file upload button.\n" +
                "2. Select an unsupported file type.\n" +
                "3. Click Upload or Submit.",
                "System rejects the file. Clear validation message explains the file type is not supported. File is not uploaded.",
                TestType.NEGATIVE,
                request.getPriority()
            ));

            results.add(new TestCase(
                "UPLOAD-NEG-002",
                "Upload missing required file",
                "User is on the upload page. File upload field is required. No file has been selected.",
                "1. Leave the file upload field empty.\n" +
                "2. Click Upload or Submit.",
                "System prevents submission. Required file validation message appears. No upload action occurs.",
                TestType.NEGATIVE,
                request.getPriority()
            ));
        }

        if (request.getTestTypes().contains(TestType.BOUNDARY)) {
            results.add(new TestCase(
                "UPLOAD-BND-001",
                "Upload file at maximum size limit",
                "User is on the upload page. A supported file exists that is exactly equal to the maximum allowed size.",
                "1. Click the file upload button.\n" +
                "2. Select a supported file at the maximum allowed size.\n" +
                "3. Click Upload or Submit.",
                "System accepts the file because it is within the allowed boundary. File uploads successfully.",
                TestType.BOUNDARY,
                request.getPriority()
            ));

            results.add(new TestCase(
                "UPLOAD-BND-002",
                "Upload file exceeding maximum size",
                "User is on the upload page. A supported file exists that is larger than the maximum allowed size.",
                "1. Click the file upload button.\n" +
                "2. Select a supported file that exceeds the maximum size limit.\n" +
                "3. Click Upload or Submit.",
                "System rejects the file. Clear validation message explains the file exceeds the maximum allowed size. File is not uploaded.",
                TestType.BOUNDARY,
                request.getPriority()
            ));
        }

        if (request.getTestTypes().contains(TestType.EDGE)) {
            results.add(new TestCase(
                "UPLOAD-EDG-001",
                "Upload empty file (0 bytes)",
                "User is on the upload page. A supported file exists with a size of 0 bytes.",
                "1. Click the file upload button.\n" +
                "2. Select an empty 0-byte file.\n" +
                "3. Click Upload or Submit.",
                "System rejects the empty file. Clear validation message explains that empty files are not allowed. File is not uploaded.",
                TestType.EDGE,
                request.getPriority()
            ));
        }

        return results;
    }
}
