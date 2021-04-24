package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.strictmode.ImplicitDirectBootViolation;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements CalculatorEventListener, KeyPadEventListener  {

    ActivityMainBinding mBinding;
    Calculator mCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mCalculator = new Calculator(this);
        PadManager.with(mBinding, this).activate();

    }

    @Override
    public void onCalculatorInputCleared() {
        mBinding.question.setText("");
    }

    @Override
    public void onQuestionChanged(String question) {
       mBinding.question.setText(question);
    }

    @Override
    public void onResultCalculated(String answer) {
        if (answer.contains(".")) {
            String literalBeforePeriod = answer.substring(0, answer.indexOf("."));
            String literalAfterPeriod = answer.substring(answer.indexOf("."));
            if (literalAfterPeriod.length() > 2) {
                literalAfterPeriod = literalAfterPeriod.substring(0, 2);
            }
            answer = literalBeforePeriod + literalAfterPeriod;
        }
        mBinding.answer.setText(answer);
    }

    @Override
    public void onKeyPressed(String keyText) {
        mCalculator.setInput(keyText);
    }

    @Override
    public void onClearKeyPressed() {
        mCalculator.clearInput();
    }

    @Override
    public void onEqualKeyPressed() {
        mCalculator.calculate();
    }
}