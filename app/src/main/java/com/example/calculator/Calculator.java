package com.example.calculator;

import android.util.Log;

import com.example.calculator.string_slicer.StringSlicer;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

    private String question = "";
    private final CalculatorEventListener eventListener;

    public Calculator(CalculatorEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void setInput(String input) {
        if (question.isEmpty()) {
            if (Character.isDigit(input.charAt(0))) setQuestion(input);
        } else {
            if (StringSlicer.endingSlice(question).equals(Constants.DOT)) {
                if (!input.equals(Constants.DOT) && !isArithmeticOperator(input)) {
                    setQuestion(question + input);
                }
            } else if (isArithmeticOperator(StringSlicer.endingSlice(question))) {
                if (Character.isDigit(input.charAt(0))) {
                    setQuestion(question + input);
                }
            } else {
                if (input.equals(Constants.DOT)) {
                    if (!question.contains(Constants.DOT)) {
                        setQuestion(question + input);
                    } else {
                        String endLiteralAfterLastDot  = question.substring(question.lastIndexOf("."));
                        if (containsArithmeticOperator(endLiteralAfterLastDot)) {
                            setQuestion(question + input);
                        }
                    }
                } else {
                    setQuestion(question + input);
                }
            }
        }
    }

    private boolean containsArithmeticOperator(String expression) {
        return (expression.contains(Constants.DIVIDE_SIGN) ||
                expression.contains(Constants.MULTIPLY_SIGN) ||
                expression.contains(Constants.ADD_SIGN) ||
                expression.contains(Constants.SUBTRACT_SIGN));
    }

    public void calculate() {
        if (question.isEmpty() || !containsArithmeticOperator(question) ||
                isArithmeticOperator(StringSlicer.endingSlice(question)) || question.endsWith(Constants.DOT)) {
            eventListener.onResultCalculated("SOON!");
        } else {
            List<String> options = new ArrayList<>();
            String item = "";
            for (int i = 0; i < question.length(); i++) {
                if (Character.isDigit(question.charAt(i)) || String.valueOf(question.charAt(i)).equals(Constants.DOT)) {
                    item += String.valueOf(question.charAt(i));
                } else {
                    options.add(item);
                    item = "";
                    options.add(String.valueOf(question.charAt(i)));
                }
                if (i == question.length() - 1) {
                    options.add(item);
                }
            }

            for (String str: options) {
                Log.d("CalcOptions", "calculate: " + str);
            }

            double result = Double.parseDouble(options.get(0));
            for (int i = 1; i < options.size(); i += 2) {
                double v2 = Double.parseDouble(options.get(i + 1));
                String op = options.get(i);
                switch (op) {
                    case Constants.DIVIDE_SIGN:
                        result /= v2;
                        break;
                    case Constants.MULTIPLY_SIGN:
                        result *= v2;
                        break;
                    case Constants.ADD_SIGN:
                        result += v2;
                        break;
                    case Constants.SUBTRACT_SIGN:
                        result -= v2;
                        break;
                }
            }
            eventListener.onResultCalculated(String.valueOf(result));
        }
    }

    private boolean isArithmeticOperator(String input) {
        switch (input) {
            case Constants.DIVIDE_SIGN:
            case Constants.MULTIPLY_SIGN:
            case Constants.ADD_SIGN:
            case Constants.SUBTRACT_SIGN:
                return true;
        }
        return false;
    }

    private void setQuestion(String question) {
        this.question = question;
        eventListener.onQuestionChanged(this.question);
    }

    public void clearInput() {
        question = "";
        eventListener.onCalculatorInputCleared();
    }

}
