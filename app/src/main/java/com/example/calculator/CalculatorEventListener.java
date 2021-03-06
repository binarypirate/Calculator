package com.example.calculator;

public interface CalculatorEventListener {
    void onCalculatorInputCleared();
    void onQuestionChanged(String question);
    void onResultCalculated(String answer);
}
