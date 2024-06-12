package org.example;

import org.example.exceptions.*;
import org.example.utils.Converter;

import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {

        Converter converter = new Converter();
        String[] actions = {"+", "-", "/", "*"};
        String[] regexActions = {"\\+", "-", "/", "\\*"};
        Scanner scn = new Scanner(System.in);

        while (true) {
            System.out.print("Enter the expression (or type 'exit' to quit): ");
            String exp = scn.nextLine().replaceAll("\\s+", "");;

            if (exp.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                int a, b;
                int actionIndex = -1;
                for (int i = 0; i < actions.length; i++) {
                    if (exp.contains(actions[i])) {
                        actionIndex = i;
                        break;
                    }
                }
                if (actionIndex == -1) {
                    throw new IncorrectExpressionException(exp);
                }

                String[] data = exp.split(regexActions[actionIndex]);

                if (data.length != 2) {
                    throw new IncorrectNumbersCountException(exp);
                }

                if (converter.isRoman(data[0]) == converter.isRoman(data[1])) {
                    boolean isRoman = converter.isRoman(data[0]);
                    if (isRoman) {
                        a = converter.romanToInt(data[0]);
                        b = converter.romanToInt(data[1]);
                    } else {
                        a = Integer.parseInt(data[0]);
                        b = Integer.parseInt(data[1]);
                    }

                    if (actions[actionIndex].equals("/") && b == 0) {
                        throw new DivisionByZeroException(exp);
                    }

                    int result = switch (actions[actionIndex]) {
                        case "+" -> a + b;
                        case "-" -> a - b;
                        case "*" -> a * b;
                        default -> a / b;
                    };

                    if (isRoman) {
                        if (result <= 0) {
                            throw new NegativeRomanNumberException(exp);
                        }
                        System.out.println(converter.intToRoman(result));
                    } else {
                        System.out.println(result);
                    }
                } else {
                    throw new FormatMismatchException(exp);
                }
            } catch (IncorrectExpressionException | FormatMismatchException | DivisionByZeroException | NegativeRomanNumberException | IncorrectNumbersCountException ex) {
                System.out.println(ex.getMessage());
            } catch (Exception ex) {
                System.out.println("An unexpected error occurred: " + ex.getMessage());
            }
        }
    }
}
