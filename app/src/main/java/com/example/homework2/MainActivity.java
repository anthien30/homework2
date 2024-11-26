package com.example.homework2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText inputField;
    private TextView outputView;
    private Button sortButton;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputField = findViewById(R.id.inputField);
        outputView = findViewById(R.id.outputView);
        sortButton = findViewById(R.id.sortButton);
        resetButton = findViewById(R.id.resetButton);

        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = inputField.getText().toString();
                String[] numbers = inputText.split(" ");

                if (numbers.length < 3 || numbers.length > 8) {
                    Toast.makeText(MainActivity.this, "Invalid Input! Please only enter lists with length >=3 and <=8.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int[] arr = new int[numbers.length];
                for (int i = 0; i < numbers.length; i++) {
                    try {
                        arr[i] = Integer.parseInt(numbers[i]);
                        if (arr[i] < 0 || arr[i] > 9) {
                            Toast.makeText(MainActivity.this, "Invalid Input! Please only enter integers >= 0 and <=9.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "Invalid Input! Please only enter integers and whitespace.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                int maxNumber = arr[0];
                int maxIndex = 0;
                for (int i = 1; i < arr.length; i++) {
                    if (arr[i] > maxNumber) {
                        maxNumber = arr[i];
                        maxIndex = i;
                    }
                }

                int nextNumberIndex = (maxIndex + 1) % arr.length;
                int nextNumber = arr[nextNumberIndex];

                SpannableStringBuilder output = new SpannableStringBuilder();
                output.append("Input: \n");
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i] == nextNumber) {
                        int start = output.length();
                        output.append(arr[i] + " ");
                    } else {
                        output.append(arr[i] + " ");
                    }
                }
                output.append("\nOutput:\n");

                SpannableStringBuilder initialStep = new SpannableStringBuilder();
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i] == nextNumber) {
                        int start = initialStep.length();
                        initialStep.append(arr[i] + " ");
                        initialStep.setSpan(new StyleSpan(Typeface.BOLD), start, initialStep.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else {
                        initialStep.append(arr[i] + " ");
                    }
                }
                initialStep.append("\n");
                output.append(initialStep);

                for (int i = 1; i < arr.length; i++) {
                    int key = arr[i];
                    int j = i - 1;

                    while (j >= 0 && arr[j] > key) {
                        arr[j + 1] = arr[j];
                        j = j - 1;
                    }
                    arr[j + 1] = key;

                    SpannableStringBuilder stepOutput = new SpannableStringBuilder();
                    for (int k = 0; k < arr.length; k++) {
                        stepOutput.append(arr[k] + " ");

                        if (k > 0 && arr[k - 1] == maxNumber) {
                            int start = stepOutput.length() - (arr[k] + " ").length();
                            int end = stepOutput.length();
                            stepOutput.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                    stepOutput.append("\n");
                    output.append(stepOutput);
                }

                outputView.setText(output);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputField.setText("");
                outputView.setText("The result will be displayed here");
            }
        });
    }
}