package com.claritysystemsinc.codeassignment.inspectionrules;

public enum InspectionType {

    PP_GAS("PP Gas"),
    PP_LIQUID("PP liquid"),
    LEAKAGE_SURVEY("Leakage Survey"),
    CURRENT_PROTECTION_SURVEY("Current Protection Survey"),
    DEVICE_READINGS("Device Readings"),
    CORROSION_SURVEY("Corrosion Survey"),
    SAFETY_INSPECTIONS("Safety Inspections"),

    UNKNOWN("unknown");

    private String value;

    InspectionType(String value) {
        this.value = value;
    }

    public static InspectionType getInspectionType(String value) {
        for(InspectionType inspectionType: values()) {
            if(inspectionType.value.equalsIgnoreCase(value)) {
                return inspectionType;
            }
        }
        return UNKNOWN;
    }
}
