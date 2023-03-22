package com.claritysystemsinc.codeassignment.utils;

import com.claritysystemsinc.codeassignment.common.entity.CombinationBean;
import com.claritysystemsinc.codeassignment.common.entity.MissingBean;
import com.claritysystemsinc.codeassignment.common.entity.MissingSomeBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

/**
 * This class Parses Required Tasks and generates Rules.
 */
@Service
@Slf4j
public class ParseRequiredTasks {

    /**
     *
     *This method generate rules for given required tasks.
     */
    public String getRules(String requiredTasks) {
        ArrayList<String> missingListBean = new ArrayList<>();
        ArrayList<String> missingSomeListBeans = new ArrayList<>();
        String rules = "";
        String result = "";
        Deque<Character> stack = new ArrayDeque<>();
        for(char ch: requiredTasks.toCharArray()) {
            // If the scanned character is an letter or digit, add it to result.
            if (Character.isLetterOrDigit(ch)) {
                result += ch;
            }  // If the scanned character is an '(',
            // push it to the stack.
            else if (ch == '(') {
                stack.push(ch);
            } // If the scanned character is an ')',
            // pop and output from the stack
            // until an '(' is encountered.
            else if (ch == ')') {
                while(!stack.isEmpty() && stack.peek()!='(') {
                    char c = stack.pop();
                    if(c=='&' || c == ',') {
                        missingListBean.add(result);
                        result = "";
                    } else if( c == '|') {
                        missingSomeListBeans.add(result);
                        result = "";
                    }
                }
                stack.pop();
            } else if( ch == '&' || ch == ',') {
                if(!stack.isEmpty()) {
                    if(stack.peek() == ch) {
                        missingListBean.add(result);
                        result = "";
                    } else if(stack.peek()=='(') {
                        missingListBean.add(result);
                        result = "";
                        stack.push(ch);
                    } else {
                        missingSomeListBeans.add(result);
                        result = "";
                        stack.pop();
                        stack.push(ch);
                    }
                } else {
                    stack.push(ch);
                    missingListBean.add(result);
                    result = "";
                }
            } else if( ch == '|') {
                if(!stack.isEmpty()) {
                    if(stack.peek() == ch) {
                        missingSomeListBeans.add(result);
                        result = "";
                    } else if(stack.peek()=='(') {
                        missingSomeListBeans.add(result);
                        result = "";
                        stack.push(ch);
                    } else {
                        missingListBean.add(result);
                        result = "";
                        stack.pop();
                        stack.push(ch);
                    }
                } else {
                    stack.push(ch);
                    missingSomeListBeans.add(result);
                    result = "";
                }
            }
        }

        if(!result.isEmpty()) {
            if(stack.isEmpty()) {
                missingSomeListBeans.add(result);
            } else {
                while (!stack.isEmpty()) {
                    if (stack.peek() == '&' || stack.peek() == ',') {
                        missingListBean.add(result);
                    } else if (stack.peek() == '|') {
                        missingSomeListBeans.add(result);
                    }
                    stack.pop();
                }
            }
        }

        if(missingListBean.size()>0 && missingSomeListBeans.size()>0) {
            rules = getCombinedRulesString(missingListBean, missingSomeListBeans);
        } else if(missingListBean.size()>0) {
            rules = getMissingRulesString(missingListBean);
        } else if(missingSomeListBeans.size()>0) {
            rules = getMissingSomeRulesString(missingSomeListBeans);
        }
        return rules;
    }

    private String getMissingSomeRulesString(ArrayList<String> missingSomeListBeans) {
        String rules;
        MissingSomeBean missingSomeBean = new MissingSomeBean();
        ArrayList<Object> arrayList1 = new ArrayList<>();
        arrayList1.add("1");
        missingSomeListBeans.add("dummy");
        arrayList1.add(missingSomeListBeans);
        missingSomeBean.setMissingSome(arrayList1);
        rules = convertToJsonString(missingSomeBean);
        return rules;
    }

    private String getMissingRulesString(ArrayList<String> missingListBean) {
        String rules;
        MissingBean missingBean = new MissingBean();
        missingBean.setMissing(missingListBean);
        rules = convertToJsonString(missingBean);
        return rules;
    }

    private String getCombinedRulesString(ArrayList<String> missingListBean, ArrayList<String> missingSomeListBeans) {
        String rules;
        MissingBean missingBean = new MissingBean();
        missingBean.setMissing(missingListBean);
        MissingSomeBean missingSomeBean = new MissingSomeBean();
        ArrayList<Object> arrayList1 = new ArrayList<>();
        arrayList1.add("1");
        missingSomeListBeans.add("dummy");
        arrayList1.add(missingSomeListBeans);
        missingSomeBean.setMissingSome(arrayList1);
        CombinationBean combinationBean = new CombinationBean();
        ArrayList<Object> arrayList3 = new ArrayList<>();
        arrayList3.add(missingSomeBean);
        arrayList3.add(missingBean);
        arrayList3.add("OK");
        combinationBean.setMyIf(arrayList3);
        rules = convertToJsonString(combinationBean);
        return rules;
    }

    private String convertToJsonString(Object obj) {
        String str = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            str = objectMapper.writeValueAsString(obj);
            log.info("#Got convertToJsonString:"+str);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
}
