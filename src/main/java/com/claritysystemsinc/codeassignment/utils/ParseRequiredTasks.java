package com.claritysystemsinc.codeassignment.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Stack;

@Service
@Slf4j
public class ParseRequiredTasks {

//    public static void main(String[] args) {
//        String s = "016G&060G";
//        String val = new ParseRequiredTasks().getRules(s);
//        System.out.println(val);
//        ObjectMapper objectMapper = new ObjectMapper();
//        MissingBean missingBean = new MissingBean();
//        ArrayList<String> arrayList = new ArrayList<>();
//        arrayList.add("016G");
//        arrayList.add("060G");
//        missingBean.setMissing(arrayList);
//        try {
//            String value = objectMapper.writeValueAsString(missingBean);
//            System.out.println(value);
//            MissingSomeBean missingSomeBean = new MissingSomeBean();
//            ArrayList<Object> arrayList1 = new ArrayList<>();
//            ArrayList<String> arrayList2 = new ArrayList<>();
//            arrayList2.add("016G");
//            arrayList2.add("060G");
//            arrayList2.add("dummy");
//            arrayList1.add("1");
//            arrayList1.add(arrayList2);
//            missingSomeBean.setMissingSome(arrayList1);
//            value = objectMapper.writeValueAsString(missingSomeBean);
//            System.out.println(value);
//            CombinationBean combinationBean = new CombinationBean();
//            ArrayList<Object> arrayList3 = new ArrayList<>();
//            arrayList3.add(missingBean);
//            arrayList3.add(missingSomeBean);
//            arrayList3.add("OK");
//            combinationBean.setMyif(arrayList3);
//            value = objectMapper.writeValueAsString(combinationBean);
//            System.out.println(value);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }
    public String getRules(String requiredTasks) {
        ArrayList<String> missingListBean = new ArrayList<>();
        ArrayList<String> missingSomeListBeans = new ArrayList<>();
//        MissingBean missingBean = new MissingBean();
//        missingBean.setMissing(new ArrayList<>());
//        MissingSomeBean missingSomeBean = new MissingSomeBean();
//        ArrayList<Object> arrayList1 = new ArrayList<>();
//        ArrayList<String> arrayList2 = new ArrayList<>();
//        arrayList1.add(arrayList2);
//        missingSomeBean.setMissingSome(arrayList1);
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
//                    result+=stack.peek();
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
            MissingBean missingBean = new MissingBean();
            missingBean.setMissing(missingListBean);
//            ArrayList<Object> arrayList3 = new ArrayList<>();
//            arrayList3.add(missingBean);
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
        } else if(missingListBean.size()>0) {
            MissingBean missingBean = new MissingBean();
            missingBean.setMissing(missingListBean);
            rules = convertToJsonString(missingBean);
        } else if(missingSomeListBeans.size()>0) {
            MissingSomeBean missingSomeBean = new MissingSomeBean();
            ArrayList<Object> arrayList1 = new ArrayList<>();
            arrayList1.add("1");
            missingSomeListBeans.add("dummy");
            arrayList1.add(missingSomeListBeans);
            missingSomeBean.setMissingSome(arrayList1);
            rules = convertToJsonString(missingSomeBean);
        }

//        Stack<String> s = new Stack<>();
//        int cnt1 = 0;
//        int cnt2 = 0;
//        for (char c : text.toCharArray()) {
//            switch (c) {
//                case ',':
//                case '&':
//                    s.push(text.substring(cnt1, cnt2));
//                    s.push("&");
//                    cnt2++;
//                    cnt1 = cnt2;
//                    break;
//                case '|':
//                    s.push(text.substring(cnt1, cnt2));
//                    s.push("|");
//                    cnt2++;
//                    cnt1 = cnt2;
//                    break;
//                case '(':
//                default:
//                    cnt2++;
//                    break;
//            }
//        }
//        if (cnt1 < cnt2) {
//            s.push(text.substring(cnt1, cnt2));
//        }
//        log.info("stack data:"+s);
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
