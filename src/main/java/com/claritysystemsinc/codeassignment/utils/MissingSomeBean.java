package com.claritysystemsinc.codeassignment.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class MissingSomeBean {
    @JsonProperty("missing_some")
    public ArrayList<Object> missingSome;

}
