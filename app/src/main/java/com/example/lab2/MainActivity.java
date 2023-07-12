package com.example.lab2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    int number;
    String history = "";
    int i = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnNew = findViewById(R.id.btnNew);
        Button btnFinish = findViewById(R.id.btnFinish);
        TextView txtResult = findViewById(R.id.txtResult);
        TextView guessThTime = findViewById(R.id.guessThTime);
        TextView numberTime = findViewById(R.id.numberTime);
        Button btnGuess = findViewById(R.id.btnGuess);
        EditText txtNumber = findViewById(R.id.txtNumber);
        TextView txtHistory = findViewById(R.id.txtHistory);
        TextView lblGameOVer = findViewById(R.id.lblGameOver);
        txtNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (txtNumber.getText().toString() != null && !txtNumber.getText().toString().isEmpty()) {
                    btnGuess.setEnabled(true);
                }
                else {
                    btnGuess.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        View.OnClickListener generateNumber = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int min = 100;
                int max = 999;
                Random random = new Random();
                number = random.nextInt(max - min) + min;
                numberTime.setText(String.valueOf(i));
                btnNew.setEnabled(false);
                btnFinish.setEnabled(true);
                history = "";
                txtNumber.setEnabled(true);
                lblGameOVer.setText("Vui lòng đoán số");
            }
        };
        View.OnClickListener finish = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblGameOVer.setText("Thua cuộc!!! Số cần đoán: " + number);
                btnNew.setEnabled(true);
                i = 1;
                txtResult.setText("");
                txtHistory.setText("");
                numberTime.setText(String.valueOf(i));
                btnFinish.setEnabled(false);
                txtNumber.setEnabled(false);
            }
        };
        View.OnClickListener guess = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resultValue = "";
                resultValue = txtNumber.getText().toString();
                history += "\n";
                String value = txtNumber.getText().toString();
                int numGuess = Integer.parseInt(value);
                if(value != null) {
                    btnGuess.setEnabled(true);
                }
                List<Integer> numberList = new ArrayList<>();
                List<Integer> guessList = new ArrayList<>();

               int temp = number / 10;
               int numDigit3 = number % 10;
               int numDigit2 = temp % 10;
               int numDigit1 = temp / 10;
               numberList.add(numDigit1);
               numberList.add(numDigit2);
               numberList.add(numDigit3);

               int guessDigit3 = numGuess % 10;
               numGuess /= 10;
               int guessDigit2 = numGuess % 10;
               int guessDigit1 = numGuess / 10;
               guessList.add(guessDigit1);
               guessList.add(guessDigit2);
               guessList.add(guessDigit3);
               if (guessList.equals(numberList)) {
                   lblGameOVer.setText("Chúc mừng bạn đã đoán đúng, hun cái nà ಥ_ಥ");
                   btnNew.setEnabled(true);
                   i = 1;
                   txtResult.setText("");
                   txtHistory.setText("");
                   numberTime.setText(String.valueOf(i));
                   btnFinish.setEnabled(false);
                   txtNumber.setEnabled(false);
               }
               List<Integer> remainingDigits = new ArrayList<>(numberList);

               for (int i = 0; i < numberList.size(); i++) {
                   int numberDigit = numberList.get(i);
                   int guessDigit = guessList.get(i);

                   if (numberDigit == guessDigit) {
                       resultValue += "+";
                       remainingDigits.remove(Integer.valueOf(guessDigit));
                   }
               }
                for (int i = 0; i < guessList.size(); i++) {
                    int guessDigit = guessList.get(i);

                    if (guessDigit != numberList.get(i) && remainingDigits.contains(guessDigit)) {
                        resultValue += "?";
                        remainingDigits.remove(Integer.valueOf(guessDigit));
                    }
                }
                numberTime.setText(String.valueOf(i));
                i++;
               history += resultValue;
               txtResult.setText(resultValue);
               txtHistory.setText(history + "\n");
               txtNumber.setText("");
                if (i > 7) {
                    finish.onClick(null);
                }
            }
        };
        btnNew.setOnClickListener(generateNumber);
        btnGuess.setOnClickListener(guess);
        btnFinish.setOnClickListener(finish);

    }
}