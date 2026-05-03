package com.qaassist.generator.engine.template;

import java.util.List;

import com.qaassist.generator.engine.model.TestCase;
import com.qaassist.generator.engine.model.TestCaseRequest;

public interface TestCaseTemplate {


    List<TestCase> generate(TestCaseRequest testCaseRequest);
}
