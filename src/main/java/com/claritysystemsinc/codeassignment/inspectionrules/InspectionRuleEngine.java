package com.claritysystemsinc.codeassignment.inspectionrules;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class InspectionRuleEngine {

    private Map<InspectionType, InspectionRule> inspectionRuleMap;

    @Autowired
    public InspectionRuleEngine(Set<InspectionRule> inspectionRule) {
        createInspectionRules(inspectionRule);
    }

    public InspectionRule findInspectionRule(InspectionType inspectionType) {
        return this.inspectionRuleMap.get(inspectionType);
    }

    public void createInspectionRules(Set<InspectionRule> inspectionRules) {
        inspectionRuleMap = new EnumMap<>(InspectionType.class);
        inspectionRules.forEach(inspectionRule -> inspectionRuleMap.put(inspectionRule.getInspectionType(), inspectionRule));
    }
}
