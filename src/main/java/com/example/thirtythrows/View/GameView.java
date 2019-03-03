/*
    GameView.java
    David Töyrä
    2019-02-14
 */
package com.example.thirtythrows.View;

import android.content.Context;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.thirtythrows.Model.DiceGame;
import com.example.thirtythrows.Model.Die;
import com.example.thirtythrows.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameView
{
    private ImageButton mDieButton1;
    private ImageButton mDieButton2;
    private ImageButton mDieButton3;
    private ImageButton mDieButton4;
    private ImageButton mDieButton5;
    private ImageButton mDieButton6;
    private ImageButton mDiceButtonArray[] = new ImageButton[6];
    private Button mThrowButton;
    private Integer mWhiteDiceImageArray[] = new Integer[6];
    private Integer mRedDiceImageArray[] = new Integer[6];
    private View view;
    private Context context;
    private Spinner spinner;
    private ArrayAdapter<String> adapterSpinner;

    ArrayList<String> comboList;
    public GameView(Context context, View view)
    {
        this.view = view;
        this.context = context;
        mDieButton1= view.findViewById(R.id.die_one);
        mDieButton2= view.findViewById(R.id.die_two);
        mDieButton3= view.findViewById(R.id.die_three);
        mDieButton4= view.findViewById(R.id.die_four);
        mDieButton5= view.findViewById(R.id.die_five);
        mDieButton6= view.findViewById(R.id.die_six);
        mDiceButtonArray[0]=mDieButton1;
        mDiceButtonArray[1]=mDieButton2;
        mDiceButtonArray[2]=mDieButton3;
        mDiceButtonArray[3]=mDieButton4;
        mDiceButtonArray[4]=mDieButton5;
        mDiceButtonArray[5]=mDieButton6;
        mThrowButton = view.findViewById(R.id.throw_button);
        makeDieImageArray();
        spinner=makeSpinner();
    }

    /**
     * Create spinner and fill it with combo options.
     * @return spinner
     */
    public Spinner makeSpinner()
    {
        String[] stringResArray = context.getResources().getStringArray(R.array.dice_combos);
        List<String> resourceList = Arrays.asList(stringResArray);
        comboList = new ArrayList<>(resourceList);

        //Make spinner
        Spinner spinner = view.findViewById(R.id.dice_combo);

        // Create an ArrayAdapter using the string array and a default spinner layout
        this.adapterSpinner = new ArrayAdapter<>(context,android.R.layout.simple_spinner_item, comboList);

        // Specify the layout to use when the list of choices appears
        this.adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapterSpinner to the spinner
        spinner.setAdapter(this.adapterSpinner);

        return spinner;

    }

    /**
     * Loop through every die button and set a listener on it.
     * @param dieArray array with diceGame
     */
    public void setDieButtonListener(Die dieArray[])
    {

        int dieSelect = 0;
        for(ImageButton dieButton: mDiceButtonArray)
        {
            dieButton.setOnClickListener(new DieSelectListener(dieButton,dieArray[dieSelect],
                    mRedDiceImageArray,mWhiteDiceImageArray));
            dieSelect++;
        }
    }

    /**
     * Get the spinner
     * @return spinner
     */
    public Spinner getSpinner() {
        return spinner;
    }



    /**
     * Make an array for the red and the white diceGame images.
     */
    public void makeDieImageArray()
    {
        mWhiteDiceImageArray[0]=R.drawable.white1;
        mWhiteDiceImageArray[1]=R.drawable.white2;
        mWhiteDiceImageArray[2]=R.drawable.white3;
        mWhiteDiceImageArray[3]=R.drawable.white4;
        mWhiteDiceImageArray[4]=R.drawable.white5;
        mWhiteDiceImageArray[5]=R.drawable.white6;

        mRedDiceImageArray[0]=R.drawable.red1;
        mRedDiceImageArray[1]=R.drawable.red2;
        mRedDiceImageArray[2]=R.drawable.red3;
        mRedDiceImageArray[3]=R.drawable.red4;
        mRedDiceImageArray[4]=R.drawable.red5;
        mRedDiceImageArray[5]=R.drawable.red6;
    }

    /**
     * Update the button with dice images. Red if selected and white if not
     * selected.
     * @param diceArray array with dice.
     */
    public void drawDiceImages(Die diceArray[])
    {
        int dieLoop = 0;
        for(int i = 0; i<6; i++)
        {
            if(diceArray[dieLoop].isSelected())
            {
                mDiceButtonArray[i].setBackgroundResource
                        (mRedDiceImageArray[diceArray[dieLoop].getDieNumber()-1]);
            }
            else
            {
                mDiceButtonArray[i].setBackgroundResource
                        (mWhiteDiceImageArray[diceArray[dieLoop].getDieNumber()-1]);
            }
            dieLoop++;
        }
    }

    public ArrayAdapter<String> getAdapterSpinner() {
        return adapterSpinner;
    }

}
