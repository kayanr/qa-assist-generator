package com.qaassist.generator.engine;

import com.qaassist.generator.engine.model.FeatureType;

import java.util.Map;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class KeywordDetector {


    private static final Map<FeatureType, List<String>> KEYWORD_MAP = Map.of(
        FeatureType.UPLOAD,   List.of("upload", "file", "attachment", "import"),
        FeatureType.LOGIN,    List.of("login", "sign in", "password", "authenticate", "credential"),
        FeatureType.SEARCH,   List.of("search", "find", "query", "filter", "lookup"),
        FeatureType.REGISTER, List.of("register", "sign up", "create account", "new user"),
        FeatureType.API,      List.of("api", "endpoint", "request", "response", "rest", "http")
    );

    public FeatureType detect(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }

        String lower = text.toLowerCase();

        for (Map.Entry<FeatureType, List<String>> entry : KEYWORD_MAP.entrySet()) {
            for (String keyword : entry.getValue()) {
                if (lower.contains(keyword)) {
                    return entry.getKey();
                }
            }
        }

        return null;
    }
}
