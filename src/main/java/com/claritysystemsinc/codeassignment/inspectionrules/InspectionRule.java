package com.claritysystemsinc.codeassignment.inspectionrules;

public interface InspectionRule {
    String inspectionRule(String requiredTasks);
    InspectionType getInspectionType();
}
