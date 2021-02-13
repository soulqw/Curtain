package com.qw.curtain.lib.shape;


import android.util.SparseArray;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Padding {

    public static final int ALL = 1;

    public static final int LEFT = 1 << 1;

    public static final int TOP = 2 << 1;

    public static final int RIGHT = 3 << 1;

    public static final int BOTTOM = 4 << 1;

    SparseArray<Integer> paddingArrays;

    @IntDef(flag = true,
            value = {ALL, LEFT, TOP, RIGHT, BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PaddingDirection {
    }

    public static Padding All(int size) {
        return new Padding(size);
    }

    public Padding() {
        this.paddingArrays = new SparseArray<>(4);
    }

    public Padding(int size) {
        this.paddingArrays = new SparseArray<>(1);
        paddingArrays.append(ALL, size);
    }

    public void appendPadding(@PaddingDirection int direction, int size) {
        paddingArrays.append(direction, size);
    }

    public int getSizeByDirection(@PaddingDirection int direction) {
        return paddingArrays.get(direction, 0);
    }

}
