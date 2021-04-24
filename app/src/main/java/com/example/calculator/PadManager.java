package com.example.calculator;

import android.widget.Button;

import com.example.calculator.databinding.ActivityMainBinding;

public class PadManager {
    private final ActivityMainBinding binding;
    private final KeyPadEventListener eventListener;

    private PadManager(ActivityMainBinding binding,KeyPadEventListener eventListener) {
        this.binding = binding;
        this.eventListener = eventListener;
    }
    
    public void activate() {
        for (Button btn: new Button[] {
                binding.button7, binding.button8, binding.button9, binding.button4,
                binding.button5, binding.button6, binding.button1, binding.button2, binding.button3,
                binding.button0, binding.buttonDot, binding.buttonDivide, binding.buttonMultiply,
                binding.buttonAdd ,binding.buttonSubtract, binding.buttonEqual
        }) {
            btn.setOnClickListener(v -> eventListener.onKeyPressed(btn.getText().toString()));
        }

        binding.buttonClear.setOnClickListener(v -> eventListener.onClearKeyPressed());
    }

    public static PadManager with(ActivityMainBinding binding, KeyPadEventListener listener) {
        return new PadManager(binding, listener);
    }
}
