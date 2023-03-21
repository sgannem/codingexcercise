package com.claritysystemsinc.codeassignment.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Stack;

@Service
@Slf4j
public class ParseRequiredTasks {
    public Stack<String> getData(String text) {
        Stack<String> s = new Stack<>();
        int cnt1 = 0;
        int cnt2 = 0;
        for (char c : text.toCharArray()) {
            switch (c) {
                case ',':
                case '&':
                    s.push(text.substring(cnt1, cnt2));
                    s.push("&");
                    cnt2++;
                    cnt1 = cnt2;
                    break;
                case '|':
                    s.push(text.substring(cnt1, cnt2));
                    s.push("|");
                    cnt2++;
                    cnt1 = cnt2;
                    break;
                default:
                    cnt2++;
                    break;
            }
        }
        if (cnt1 < cnt2) {
            s.push(text.substring(cnt1, cnt2));
        }
        log.info("stack data:"+s);
        return s;
    }
}
