package com.example.calculator;

public interface KeyPadEventListener {
    void onKeyPressed(String keyText);
    void onClearKeyPressed();
    void onEqualKeyPressed();
}
