package com.qaassist.generator.engine.registry;

import java.util.EnumMap;
import com.qaassist.generator.engine.model.FeatureType;
import com.qaassist.generator.engine.template.*; // wildcard — imports all templates
import org.springframework.stereotype.Component; 

@Component
public class TemplateRegistry {
    private EnumMap<FeatureType, TestCaseTemplate> registry = new EnumMap<>(FeatureType.class);

    public TemplateRegistry() {
        registry.put(FeatureType.UPLOAD, new UploadTemplate());
        registry.put(FeatureType.LOGIN, new LoginTemplate());
        registry.put(FeatureType.SEARCH, new SearchTemplate());
        registry.put(FeatureType.REGISTER, new RegisterTemplate());
        registry.put(FeatureType.FORM, new FormTemplate());
        registry.put(FeatureType.API, new ApiTemplate());
    }

    public TestCaseTemplate getTemplate(FeatureType featureType) {
        return registry.get(featureType);
    }
    
}
