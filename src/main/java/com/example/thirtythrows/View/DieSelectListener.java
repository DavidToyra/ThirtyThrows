/*
    DieSelectListener.java
    David Töyrä
    2019-02-14
 */
package com.example.thirtythrows.View;

import android.view.View;
import android.widget.ImageButton;

import com.example.thirtythrows.Model.DiceGame;
import com.example.thirtythrows.Model.Die;

/**
 * Listener for the diceGame buttons. When clicked the die will change
 * colour to red which means that die will be saved.
 */
public class DieSelectListener implements View.OnClickListener {

    private ImageButton mDieButton;
    private Die die;
    private Integer mRedDiceImageArray[];
    private Integer mWhiteDiceArray[];
    public DieSelectListener(ImageButton mDieButton, Die die, Integer mRedDiceArray[],Integer mWhiteDiceArray[])
    {
        this.mRedDiceImageArray = mRedDiceArray;
        this.mWhiteDiceArray = mWhiteDiceArray;
        this.mDieButton = mDieButton;
        this.die = die;
    }
    @Override
    public void onClick(View v) {
        if(!die.isSelected()) {
            die.setSelected(true);
            mDieButton.setBackgroundResource(mRedDiceImageArray[die.getDieNumber() - 1]);
        }
        else
        {
            die.setSelected(false);
            mDieButton.setBackgroundResource(mWhiteDiceArray[die.getDieNumber() - 1 ]);
        }

    }
}
