package com.qw.curtain.sample.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.qw.curtain.lib.Curtain;
import com.qw.curtain.lib.ViewGetter;
import com.qw.curtain.lib.shape.CircleShape;
import com.qw.curtain.sample.R;
import com.qw.curtain.sample.adapter.BaseArrayAdapter;

public class ListViewFragment extends Fragment {

    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listView = new ListView(container.getContext());
        return listView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setAdapter(new ListAdapter());
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    showGuideInItemChild();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
//        如果需要在初始化的时候调用 请用post
        listView.post(new Runnable() {
            @Override
            public void run() {
                showGuideInItem();
            }
        });
    }

    private void showGuideInItem() {
        View item1 = ViewGetter.getFromAdapterView(listView, 5);
        View item2 = ViewGetter.getFromAdapterView(listView, 2);
        if (null == item1 || null == item2) {
            return;
        }
        new Curtain(this)
                .with(item1)
                .with(item2)
                .show();
    }

    private void showGuideInItemChild() {
        View item1 = ViewGetter.getFromAdapterView(listView, 1);
        View item2 = ViewGetter.getFromAdapterView(listView, 3);
        if (null == item1 || null == item2) {
            return;
        }
        new Curtain(this)
                .withShape(item1.findViewById(R.id.image), new CircleShape())
                .with(item2.findViewById(R.id.tv_text))
                .show();
    }

    public class ListAdapter extends BaseArrayAdapter {

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
