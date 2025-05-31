package com.back;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calc {
    private static final Pattern pattern = Pattern.compile("-?\\d+|[+\\-*()]");
    private static Matcher matcher;
    private static boolean hasNextToken;

    public static int run(String exp) {
        matcher = pattern.matcher(exp);

        nextToken();
        if (!hasNextToken) {
            throw new IllegalStateException("수식 미입력");
        }

        return parseAddSub();
    }

    private static int parseAddSub() {
        int result = parseMul();

        while (hasNextToken && matcher.group().matches("[+-]")) {
            String operator = matcher.group();
            nextToken();
            int nextNumber = parseMul();

            result = switch (operator) {
                case "+" -> result + nextNumber;
                case "-" -> result - nextNumber;
                default -> throw new IllegalStateException("유효하지 않은 연산자 : " + operator);
            };
        }
        return result;
    }

    private static int parseMul() {
        int result = parseNumberParentheses();

        while (hasNextToken && matcher.group().equals("*")) {
            String operator = matcher.group();
            nextToken();
            int nextNumber = parseNumberParentheses();

            result = switch (operator) {
                case "*" -> result * nextNumber;
                default -> throw new IllegalStateException("유효하지 않은 연산자 : " + operator);
            };
        }
        return result;
    }

    private static int parseNumberParentheses() {
        int result;
        boolean isNegative = false;

        if (hasNextToken && matcher.group().equals("-")) {
            isNegative = true;
            nextToken();
        }

        if (hasNextToken && matcher.group().matches("-?\\d+")) {
            result = Integer.parseInt(matcher.group());
            nextToken();
        } else if (hasNextToken && matcher.group().equals("(")) {
            nextToken();
            result = parseAddSub();

            if (!hasNextToken || !matcher.group().equals(")")) {
                throw new IllegalStateException("닫는 괄호 ')' 미입력");
            }
            nextToken();
        } else {
            throw new IllegalStateException("유효하지 않은 수식");
        }
        return isNegative ? -result : result;
    }

    private static void nextToken() {
        hasNextToken = matcher.find();
    }
}
