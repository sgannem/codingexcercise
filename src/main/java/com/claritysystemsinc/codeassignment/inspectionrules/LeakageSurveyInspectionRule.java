package com.claritysystemsinc.codeassignment.inspectionrules;

import com.claritysystemsinc.codeassignment.utils.ParsingColumn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Stack;

@Component
@Slf4j
@RequiredArgsConstructor
public class LeakageSurveyInspectionRule implements InspectionRule {

    private final ParsingColumn parsingColumn;

    @Override
    public String inspectionRule(String requiredTasks) {
        Stack<String> stack = parsingColumn.getData(requiredTasks);
        log.info("#got stack:"+stack);
        return stack.toString();
    }

    @Override
    public InspectionType getInspectionType() {
        return InspectionType.LEAKAGE_SURVEY;
    }
}
