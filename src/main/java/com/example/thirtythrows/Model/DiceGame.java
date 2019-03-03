/*
    DiceGame.java
    David Töyrä
    2019-02-14
 */
package com.example.thirtythrows.Model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * DiceGame class for thirty, holds the dice, handles rolls, counts scores.
 */
public class DiceGame implements Parcelable
{

    private Die diceArray[] = new Die[6];
    private int rolls;
    private int roundScore[] = new int[11];
    private int roundComboChoice[] = new int[10];
    private int rounds;

    /**
     * Construcor for dicegame, sets the variables and creates new dice.
     */
    public DiceGame()
    {
        rounds = 0;
        rolls = 3;
        for(int i = 0 ; i<6;i++)
        {
            this.diceArray[i] = new Die((int)(Math.random()*6+1));
            System.out.println("Die"+i+"="+diceArray[i]);
        }
    }

    protected DiceGame(Parcel in) {
        rolls = in.readInt();
        roundScore = in.createIntArray();
        rounds = in.readInt();
    }

    public static final Creator<DiceGame> CREATOR = new Creator<DiceGame>() {
        @Override
        public DiceGame createFromParcel(Parcel in) {
            return new DiceGame(in);
        }

        @Override
        public DiceGame[] newArray(int size) {
            return new DiceGame[size];
        }
    };

    /**
     * New round routine, incrases the roundcounter by one and
     * deselect all dice.
     */
    public void nextRound()
    {
        rounds++;
        for(int i = 0; i<6; i++)
        {
            diceArray[i].setSelected(false);
        }
        resetReroll();
    }

    /**
     * Checks if all dice is selected.
     * @return
     */
    public boolean isAllDiceSelected()
    {
        for(int i = 0; i < 6 ; i++)
        {
            if(!diceArray[i].isSelected())
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Use one reroll.
     * @return
     */
    public boolean incrementReroll()
    {
        if(rolls <1)
        {
            return false;
        }
        this.rolls--;
        return true;
    }

    /**
     * Reset rolls for next round.
     */
    public void resetReroll()
    {
        rolls = 3;
    }

    /**
     * Calculates the score of the low combo. All dice values under
     * 4 gets counted.
     * @return final score of those dice.
     */
    private int calculateLowScore()
    {
        int sum = 0;
        for(int i = 0; i<6; i++)
        {
            if(diceArray[i].getDieNumber() < 4)
            {
                sum += diceArray[i].getDieNumber();
            }
        }
        System.out.println("LowScore = " + sum);
        return sum;
    }
    /**
     * Calculates the score in accordance with the user choice.
     * @param scoreChoice int that decides how the score should be calculated.
     */
    public int calculateScore(int scoreChoice)
    {
        int score;
        if(scoreChoice<4)
        {
            score = calculateLowScore();
            roundScore[rounds-1] = score;
            roundComboChoice[rounds-1] = 3;

        }
        else
        {
            score = calculateComboScore(scoreChoice);
            roundScore[rounds-1] = score;
            roundComboChoice[rounds-1] = scoreChoice;
        }

        roundScore[10] +=roundScore[rounds-1];
        return score;
    }

    /**
     * Calculates the score.
     * @param combo score to sum up to
     * @return
     */
    private int calculateComboScore(int combo)
    {
        int scoreSum = 0;
        for(int i = 0 ;i<6 ; i++)
        {
            if(diceArray[i].getDieNumber()==combo)
            {
                scoreSum+=diceArray[i].getDieNumber();
                continue;
            }
            for(int j = 1; j<6 ;j++)
            {
                if(diceArray[i].getDieNumber()+diceArray[j].getDieNumber() == combo)
                {
                    scoreSum+=diceArray[i].getDieNumber()+diceArray[j].getDieNumber();
                    break;
                }
            }
        }
        return scoreSum;
    }

    /**
     * Roll all six dice, if a die is selected then it will keep its value.
     */
    public void roll()
    {
        for(int i = 0 ; i<6;i++)
        {
            if (!diceArray[i].isSelected())
            {
                diceArray[i].setDieNumber((int) (Math.random() * 6 + 1));
            }
        }
    }


    public Die[] getDiceArray() {
        return diceArray;
    }

    public int getRolls() {
        return rolls;
    }
    public int getRounds() {
        return rounds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rolls);
        dest.writeInt(rounds);
        dest.writeIntArray(roundScore);
        dest.writeTypedArray(diceArray,0);
    }

    public int[] getRoundScore() {
        return roundScore;
    }
    public void setRounds(int rounds) {
        this.rounds = rounds;
    }
    public int[] getRoundComboChoice() {
        return roundComboChoice;
    }
}
