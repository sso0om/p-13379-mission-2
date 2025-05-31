package com.back;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calc {
    public static int run(String exp) {
        Pattern pattern = Pattern.compile("-?\\d+|[+\\-*]");
        Matcher matcher = pattern.matcher(exp);

        int result;
        if (matcher.find()) {
            result = Integer.parseInt(matcher.group());
        } else {
            throw new IllegalStateException("수식 미입력");
        }

        while (matcher.find()) {
            String operator = matcher.group();
            matcher.find();
            int nextNumber = Integer.parseInt(matcher.group());

            result = switch (operator) {
                case "+" -> result + nextNumber;
                case "-" -> result - nextNumber;
                case "*" -> result * nextNumber;
                default -> throw new IllegalStateException("유효하지 않은 연산자 : " + operator);
            };
        }
        return result;
    }
}
