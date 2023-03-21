package com.claritysystemsinc.codeassignment.inspectionrules;

import com.claritysystemsinc.codeassignment.utils.ParseRequiredTasks;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Stack;

@Component
@Slf4j
@RequiredArgsConstructor
public class LeakageSurveyInspectionRule implements InspectionRule {

    private final ParseRequiredTasks parseRequiredTasks;

    @Override
    public String inspectionRule(String requiredTasks) {
        Stack<String> stack = parseRequiredTasks.getData(requiredTasks);
        log.info("#got stack:"+stack);
        return stack.toString();
    }

    @Override
    public InspectionType getInspectionType() {
        return InspectionType.LEAKAGE_SURVEY;
    }
}
