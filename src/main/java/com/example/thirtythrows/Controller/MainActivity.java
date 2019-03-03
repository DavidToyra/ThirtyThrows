/*
    MainActivity.java
    David Töyrä
    2019-02-14
 */
package com.example.thirtythrows.Controller;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thirtythrows.Model.DiceGame;
import com.example.thirtythrows.Model.Die;
import com.example.thirtythrows.R;
import com.example.thirtythrows.View.GameView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Main activity screen. Throw dice 30 times and select score choice,
 * when all rounds are done the result screen will be displayed.
 */
public class MainActivity extends AppCompatActivity {


    private Button mThrowButton;
    private Die diceArray[];
    private DiceGame diceGame;
    private GameView mView;
    private TextView mGameText;
    private Spinner comboSpinner;
    private ArrayAdapter<String> arrayAdapter;
    private int throwCounter;
    private ArrayList<String> comboListItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mView = new GameView(this, getWindow().getDecorView().getRootView());

        //Get view components
        this.mGameText = findViewById(R.id.game_announce);
        this.mThrowButton = findViewById(R.id.throw_button);
        this.comboSpinner = mView.getSpinner();
        this.arrayAdapter = mView.getAdapterSpinner();

        //Check if a saved instance exists
        if(savedInstanceState!=null)
        {
            diceGame = savedInstanceState.getParcelable("Dice_Game_Parcel");
            if (diceGame != null) {
                mView.drawDiceImages(diceGame.getDiceArray());
                mGameText.setText("");
                mGameText.append("Round " + diceGame.getRounds());
                ArrayList<String> adapterList = savedInstanceState.getStringArrayList("Adapter_List");
                arrayAdapter.clear();
                if (adapterList != null) {
                    arrayAdapter.addAll(adapterList);
                }
            }
        }
        else{
            for(int i = 0; i<arrayAdapter.getCount(); i++)
            {
                comboListItems.add(arrayAdapter.getItem(i));
            }
            diceGame = new DiceGame();
        }

        setSpinnerListener();
        setThrowListener();

    }

    /**
     * Set listeners on the spinner menu.
     *
     */
    private void setSpinnerListener()
    {
        //add listener to spinner
        comboSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!(diceGame.getRounds()==0) && (diceGame.isAllDiceSelected() || diceGame.getRolls()==0) && position !=0)
                {

                    if(parent.getItemAtPosition(position).equals("Low"))
                    {
                        int score = diceGame.calculateScore(3);
                        scoreToast(score);
                    }
                    else if(position > 0)
                    {
                        int score = diceGame.calculateScore(Integer.parseInt((String)parent.getItemAtPosition(position)));
                        System.out.println("Combo "+ Integer.parseInt((String)parent.getItemAtPosition(position)));
                        scoreToast(score);
                    }

                    //If the game is over start the result activity
                    if(diceGame.getRounds()==10)
                    {
                        showResults();
                    }
                    else
                    {
                        System.out.println("removing "+arrayAdapter.getItem(position));
                        arrayAdapter.remove(arrayAdapter.getItem(position));
                        arrayAdapter.notifyDataSetChanged();
                        diceGame.nextRound();
                        parent.setSelection(0);
                    }
                }
                parent.setSelection(0);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    /**
     * Set an on-click listener on the throw button.
     * Whenever it's clicked it will random generate numbers for the dice
     * and call the draw method to update the pictures.
     */
    private void setThrowListener()
    {
        //Set listener on throw button
        mThrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(diceGame.getRolls()>0)
                {
                    if(diceGame.getRounds() == 0)
                    {
                        diceGame.setRounds(1);
                    }
                    if(diceGame.getRolls()==3)
                    {
                        mGameText.setText("");
                        mGameText.setText(R.string.round);
                        mGameText.append(String.format("%d", diceGame.getRounds()));
                    }
                    throwCounter++;
                    diceGame.roll();
                    diceArray = diceGame.getDiceArray();
                    mView.drawDiceImages(diceArray);
                    mView.setDieButtonListener(diceArray);
                    diceGame.incrementReroll();
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        System.out.println("Save Instanse");
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable("Dice_Game_Parcel", diceGame);
        ArrayList<String> adapterList = new ArrayList<>();
        for(int i = 0; i<arrayAdapter.getCount(); i++)
        {
            adapterList.add(arrayAdapter.getItem(i));
        }

        savedInstanceState.putStringArrayList("Adapter_List", adapterList);
    }



    /**
     * Creates a new dice game, updates the middle screen text,
     * and reintroduces the spinner items.
     */
    private void resetGame()
    {
        this.diceGame = new DiceGame();
        mGameText.setText(R.string.start_text);

        String[] stringResArray = getResources().getStringArray(R.array.dice_combos);
        List<String> resourceList = Arrays.asList(stringResArray);
        ArrayList<String> comboList = new ArrayList<>(resourceList);
        arrayAdapter.clear();
        arrayAdapter.addAll(comboList);
    }


    private void scoreToast(int score)
    {
        Toast.makeText(this, "You got "+score+ " points!",Toast.LENGTH_SHORT).show();

    }
    /**
     * Start the result screen activity. Sends the score array in bundle.
     */
    private void showResults()
    {
        Intent intent = new Intent(getBaseContext(), ResultActivity.class);
        intent.putExtra("Score_Array", diceGame.getRoundScore());
        intent.putExtra("Score_Choice", diceGame.getRoundComboChoice());
        resetGame();
        startActivity(intent);
    }

}
