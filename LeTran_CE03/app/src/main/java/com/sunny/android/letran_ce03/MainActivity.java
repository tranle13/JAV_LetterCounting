
    // NAME: Tran Le
    // JAV1 - 1808
    // FILE NAME: MainActivity.java

package com.sunny.android.letran_ce03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

    public class MainActivity extends AppCompatActivity {

        private Toast feedback = null;
        private ArrayList<String> allWords = new ArrayList<>();
        private static final String TAG = "check";
        private ArrayList<Integer> wordLength = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_Add).setOnClickListener(addTapped);
        findViewById(R.id.btn_View).setOnClickListener(viewTapped);
    }

    // Create function to handle view event
    private View.OnClickListener viewTapped = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    // Create function to handle add event
    private View.OnClickListener addTapped = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText input = (EditText)findViewById(R.id.txt_TextInput);
            String inputText = input.getText().toString();
            NumberPicker picker = (NumberPicker)findViewById(R.id.npk_NumberPicker);
            checkString(inputText, input);
            updatePicker_Texts(picker);
        }
    };

    // Create function to validate the input and add it to the array if all conditions are satisfied
    private void checkString(String text, EditText input) {

        if (feedback != null) {
            feedback.cancel();
        }
        String trimmedText = text.replace(" ", "");

        if (!allWords.contains(text) && !text.isEmpty()) {
            if (trimmedText.length() == 0) {
                feedback = Toast.makeText(this, R.string.feedback_allwhitespace, Toast.LENGTH_SHORT);
            } else {
                allWords.add(text);
                int newTextLength = text.length();
                wordLength.add(newTextLength);
                input.setText("");
                feedback = Toast.makeText(this, R.string.feedback_success, Toast.LENGTH_SHORT);
            }
        } else if (allWords.contains(text)){
            feedback = Toast.makeText(this, R.string.feedback_alreadyexist, Toast.LENGTH_SHORT);
        }
        else if (text.isEmpty()){
            feedback = Toast.makeText(this, R.string.feedback_empty, Toast.LENGTH_SHORT);
        }

        feedback.show();
    }

    // Create function to update the picker and textviews
    private void updatePicker_Texts(NumberPicker picker) {
        float average = 0f;
        float median = 0f;

        if (allWords.size() > 0) {
            picker.setMinValue(1);
            picker.setMaxValue(allWords.size());
            for (String word: allWords) {
                average += word.length();
            }
            Collections.sort(wordLength);

            if (wordLength.size() % 2 == 0) {
                int middleIndex = wordLength.size()/2;
                median = wordLength.get(middleIndex) + wordLength.get(middleIndex -1);
                median /= 2;
                average /= allWords.size();
            } else {
                if (wordLength.size() > 1) {
                    median = wordLength.get(wordLength.size() / 2 - 1);
                } else {
                    median = wordLength.get(0);
                }
            }
        } else {
            picker.setMinValue(0);
            picker.setMaxValue(0);
        }

        TextView averageText = (TextView)findViewById(R.id.txt_Average);
        TextView medianText = (TextView)findViewById(R.id.txt_Median);

        medianText.setText(String.format(Locale.getDefault(), "%.2f", median));
        averageText.setText(String.format(Locale.getDefault(), "%.2f", average));
    }
}
