package com.claritysystemsinc.codeassignment.inspectionrules;

import com.claritysystemsinc.codeassignment.utils.ParseRequiredTasks;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Stack;

@Component
@Slf4j
@RequiredArgsConstructor
public class CorrosionSurveyInspectionRule implements InspectionRule {

    private final ParseRequiredTasks parseRequiredTasks;

    @Override
    public String inspectionRule(String requiredTasks) {
        String rules = parseRequiredTasks.getRules(requiredTasks);
        log.info("#Got rules:"+rules);
        return rules;
    }

    @Override
    public InspectionType getInspectionType() {
        return InspectionType.CORROSION_SURVEY;
    }
}
