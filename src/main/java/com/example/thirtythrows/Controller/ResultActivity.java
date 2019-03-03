/*
    ResultActivity.java
    David Töyrä
    2019-02-14
 */
package com.example.thirtythrows.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.thirtythrows.R;

import org.w3c.dom.Text;

/**
 * Result screen. Takes an array with scores from the main activity
 * and display the score of each round and a total score.
 */
public class ResultActivity extends AppCompatActivity {
    private int scoreArray[];
    private int comboArray[];
    private TextView mResultTextArray[] = new TextView[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle myBundle = getIntent().getExtras();
        if (myBundle != null) {
            scoreArray= myBundle.getIntArray("Score_Array");
            comboArray=myBundle.getIntArray("Score_Choice");
            for(int i = 0; i<11;i++)
            {
                assert scoreArray != null;
                System.out.println("Score"+ scoreArray[i]);
            }
        }
        makeResultTextViewArray();
        appendScoreToViwe();
    }

    private void makeResultTextViewArray()
    {
        mResultTextArray[0] = findViewById(R.id.Round_1);
        mResultTextArray[1] = findViewById(R.id.Round_2);
        mResultTextArray[2] = findViewById(R.id.Round_3);
        mResultTextArray[3] = findViewById(R.id.Round_4);
        mResultTextArray[4] = findViewById(R.id.Round_5);
        mResultTextArray[5] = findViewById(R.id.Round_6);
        mResultTextArray[6] = findViewById(R.id.Round_7);
        mResultTextArray[7] = findViewById(R.id.Round_8);
        mResultTextArray[8] = findViewById(R.id.Round_9);
        mResultTextArray[9] = findViewById(R.id.Round_10);

    }

    /**
     * Append the score from the array to the textviews.
     */
    private void appendScoreToViwe()
    {
        TextView textView = findViewById(R.id.total_score);
        textView.append(String.format("%d", scoreArray[10]));

        for(int i = 0; i<10; i++)
        {
            String score;
            if(comboArray[i] == 3)
            {
                score = " gives "+ String.format("%d", scoreArray[i]) + " points with combo choice low";
            }
            else
            {
                score = " gives "+ String.format("%d", scoreArray[i]) + " points with combo choice " +comboArray[i];
            }
            mResultTextArray[i].append(score);
        }

        /*
        textView = findViewById(R.id.Round_1);
        String score = ": "+ String.format("%d", scoreArray[0]) + "Combo choice: "+comboArray[0];
        textView.append(score);

        textView = findViewById(R.id.Round_2);
        score = ": "+ String.format("%d", scoreArray[1])+ "Combo choice: "+comboArray[1];
        textView.append(score);

        textView = findViewById(R.id.Round_3);
        score = ": "+ String.format("%d", scoreArray[2])+ "Combo choice: "+comboArray[2];
        textView.append(score);

        textView = findViewById(R.id.Round_4);
        score = ": "+ String.format("%d", scoreArray[3]) + "Combo choice: "+comboArray[3];
        textView.append(score);

        textView = findViewById(R.id.Round_5);
        score = ": "+ String.format("%d", scoreArray[4]) + "Combo choice: "+comboArray[4];
        textView.append(score);

        textView = findViewById(R.id.Round_6);
        score = ": "+ String.format("%d", scoreArray[5]) + "Combo choice: "+comboArray[5];
        textView.append(score);

        textView = findViewById(R.id.Round_7);
        score = ": "+ String.format("%d", scoreArray[6])+ "Combo choice: "+comboArray[6];
        textView.append(score);

        textView = findViewById(R.id.Round_8);
        score = ": "+ String.format("%d", scoreArray[7])+ "Combo choice: "+comboArray[7];
        textView.append(score);

        textView = findViewById(R.id.Round_9);
        score = ": "+ String.format("%d", scoreArray[8])+ "Combo choice: "+comboArray[8];
        textView.append(score);

        textView = findViewById(R.id.Round_10);
        score = ": "+ String.format("%d", scoreArray[9])+ "Combo choice: "+comboArray[9];
        textView.append(score);*/

    }

}
