package org.example;

import org.example.exceptions.*;
import org.example.utils.Converter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorGUI extends JFrame {
    private final JTextField expressionField;
    private final JTextArea resultArea;
    private final Converter converter;

    public CalculatorGUI() {
        converter = new Converter();
        setTitle("Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        expressionField = new JTextField();
        resultArea = new JTextArea();
        resultArea.setEditable(false);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new CalculateButtonListener());

        add(expressionField, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
        add(calculateButton, BorderLayout.SOUTH);
    }

    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String exp = expressionField.getText().replaceAll("\\s+", "");;
            String[] actions = {"+", "-", "/", "*"};
            String[] regexActions = {"\\+", "-", "/", "\\*"};

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
                        resultArea.append(converter.intToRoman(result) + "\n");
                    } else {
                        resultArea.append(result + "\n");
                    }
                } else {
                    throw new FormatMismatchException(exp);
                }
            } catch (IncorrectExpressionException | FormatMismatchException | DivisionByZeroException | NegativeRomanNumberException | IncorrectNumbersCountException ex) {
                resultArea.append(ex.getMessage() + "\n");
            } catch (Exception ex) {
                resultArea.append("An unexpected error occurred: " + ex.getMessage() + "\n");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorGUI calculatorGUI = new CalculatorGUI();
            calculatorGUI.setVisible(true);
        });
    }
}
