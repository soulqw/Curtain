package com.qw.curtain.sample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qw.curtain.lib.Curtain;

public class AdapterViewActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = new ListView(this);
        setContentView(listView);
        listView.setAdapter(new ListAdapter());
        //如果需要在onCreate时候调用 请用post
        listView.post(new Runnable() {
            @Override
            public void run() {
                new Curtain(AdapterViewActivity.this)
                        .with(Curtain.ViewGetter.getFromAdapterView(listView, 7))
                        .with(Curtain.ViewGetter.getFromAdapterView(listView, 5))
                        .show();
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    showGuideInListView();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    private void showGuideInListView() {
        new Curtain(AdapterViewActivity.this)
                .with(Curtain.ViewGetter.getFromAdapterView(listView, 7))
                .with(Curtain.ViewGetter.getFromAdapterView(listView, 5))
                .show();
    }

    public class ListAdapter extends BaseAdapter {

        private String[] data = {"this", "is", "a", "better", "guide", "view", "generate", "lib", "for", "android"};

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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View result;
            if (null != convertView) {
                result = convertView;
            } else {
                result = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_view, null);
            }
            ((TextView) (result.findViewById(R.id.tv_text))).setText(data[position]);
            return result;
        }

    }
}
