package com.claritysystemsinc.codeassignment.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InspectionBean {

    private String inspection;
    private String requiredTasks;
    private String rules;

}
