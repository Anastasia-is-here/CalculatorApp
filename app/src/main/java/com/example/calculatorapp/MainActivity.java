package com.example.calculatorapp;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import net.objecthunter.exp4j.ExpressionBuilder;
import java.lang.Exception;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private EditText resultTextView;

    @Override
     public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        resultTextView = findViewById(R.id.resultTextView);

        setupInputChangeListener();

        int[] buttonIds = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
                R.id.buttonDot, R.id.buttonAdd, R.id.buttonSubtract, R.id.buttonMultiply,
                R.id.buttonDivide, R.id.buttonBr1, R.id.buttonBr2, R.id.backspace,
                R.id.AC, R.id.buttonEquals
        };

        for (int buttonId : buttonIds) {
            Button button = this.findViewById(buttonId);
            button.setOnClickListener(v -> onButtonClick(button));
        }

        findViewById(R.id.buttonEquals).setOnClickListener(v -> onEqualsButtonClick());
    }

    private void setupInputChangeListener() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Calculate and display result while typing
                String expression = s.toString();
                try {
                    double result = evaluateExpression(expression);
                    resultTextView.setText(String.valueOf(result));
                } catch (Exception e) {
                    // Handle invalid expressions
                    resultTextView.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }


    private void onButtonClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();
        String currentText = editText.getText().toString();

        Log.e("sometag", buttonText);
        switch (buttonText) {
            case ("AC"):
                editText.setText("");
                break;
            case ("C"):
                if (!currentText.isEmpty()) {
                    editText.setText(currentText.substring(0, currentText.length() - 1));
                    break;
                }
            default: {
                editText.setText(currentText + buttonText);
                break;
                }
        }
    }

    private void onEqualsButtonClick() {
        String currentText = editText.getText().toString();
        try {
            double result = evaluateExpression(currentText);
            //int resultInt = (int) result; // Convert result to integer
            editText.setText(String.valueOf(result));
        } catch (Exception e) {
            editText.setText("Error: " + e.getMessage());
        }
    }




    private double evaluateExpression(String expression){
        return new ExpressionBuilder(expression).build().evaluate();
    }

}
