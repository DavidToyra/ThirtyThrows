/*
    Die.java
    David Töyrä
    2019-02-14
 */
package com.example.thirtythrows.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents a die. Has a value and a boolean that tells if it's
 * selected or not.
 */
public class Die implements Parcelable
{

    private int dieNumber;
    private boolean selected;

    /**
     * Constructor for die, takes a number that will be its value.
     * @param number
     */
    public Die(int number)
    {
        this.dieNumber = number;
        this.selected = false;
    }

    protected Die(Parcel in) {
        dieNumber = in.readInt();
        selected = in.readByte() != 0;
    }

    public static final Creator<Die> CREATOR = new Creator<Die>() {
        @Override
        public Die createFromParcel(Parcel in) {
            return new Die(in);
        }

        @Override
        public Die[] newArray(int size) {
            return new Die[size];
        }
    };

    public int getDieNumber() {
        return dieNumber;
    }

    public void setDieNumber(int dieNumber) {
        this.dieNumber = dieNumber;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dieNumber);
        dest.writeByte((byte) (selected ? 1 : 0));
    }
}
