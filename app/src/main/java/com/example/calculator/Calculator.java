package com.example.calculator;

import android.util.Log;

import androidx.annotation.LongDef;

import com.example.calculator.string_slicer.StringSlicer;

import java.util.Arrays;

public class Calculator {

    private String question = "";
    private String answer = "";
    private final CalculatorEventListener eventListener;

    public Calculator(CalculatorEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void setInput(String input) {
        if (question.isEmpty()) {
            if (Character.isDigit(input.charAt(0))) setQuestion(input);
        } else {
            if (StringSlicer.endingSlice(question).equals(Constants.DOT)) {
                if (!input.equals(Constants.EQUAL) && !input.equals(Constants.DOT) && !isArithmeticOperator(input)) {
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
                        String lastCharInEndingLiteral = StringSlicer.endingSlice(endLiteralAfterLastDot);
                        if (isArithmeticOperator(lastCharInEndingLiteral)) {
                            setQuestion(question + input);
                        }
                    }
                } else {
                    setQuestion(question + input);
                }

            }
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
