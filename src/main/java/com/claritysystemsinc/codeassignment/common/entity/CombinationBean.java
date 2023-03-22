package com.claritysystemsinc.codeassignment.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CombinationBean {
        @JsonProperty("if")
        public ArrayList<Object> myIf;
    }