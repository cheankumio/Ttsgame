package com.klapper.ttsgame;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by c1103304 on 2017/7/14.
 */

public class QA {
    static Random random = new Random(3767);
    String q[], a[];

    QA(String block) {
        block = STR.replace(block, "\r", "");
        block = STR.replace(block, " ", "");
        String head = STR.head(block, "\n");
        String tail = STR.tail(block, "\n");
        q = head.split("\\|");
        a = tail.split("\n");
    }

    public String answer(String input) {
        for (int i = 0; i < q.length; i++) {
            int matchAt = input.indexOf(q[i]);
            String tail;
            if (matchAt >= 0)
                tail = input.substring(matchAt + q[i].length());
            else if (q[i].equals("比對失敗"))
                tail = "";
            else
                continue;
            int aIdx = Math.abs(random.nextInt()) % a.length;
            String answer = STR.replace(a[aIdx], "*", tail);
            answer = STR.expand(answer, "=");
            return answer;
        }
        return null;
    }

    public String toString() {
        return "Q:" + Arrays.asList(q) + " A:" + Arrays.asList(a);
    }
}
