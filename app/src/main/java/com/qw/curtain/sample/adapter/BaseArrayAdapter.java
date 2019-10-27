package com.qw.curtain.sample.adapter;

import android.widget.BaseAdapter;

public abstract class BaseArrayAdapter extends BaseAdapter {

    protected String[] data = {"this", "is", "a", "better", "guide", "view", "generate", "lib", ".", "for", "android", "more", "and", "more", "useful", "to", "simplify", "your", "coding", ".", "have", "a", "nice", "day", "~"};

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}


