package com.qw.curtain.sample.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.qw.curtain.lib.Curtain;
import com.qw.curtain.lib.ViewGetter;
import com.qw.curtain.lib.shape.CircleShape;
import com.qw.curtain.sample.R;
import com.qw.curtain.sample.adapter.BaseArrayAdapter;

public class GirdViewFragment extends Fragment {

    private GridView gridView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        gridView = new GridView(container.getContext());
        gridView.setNumColumns(3);
        return gridView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gridView.setAdapter(new GridAdapter());
        gridView.setHorizontalSpacing(4);
        gridView.setVerticalSpacing(4);
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
        gridView.post(new Runnable() {
            @Override
            public void run() {
                showGuideInItem();
            }
        });
    }

    private void showGuideInItem() {
        View item1 = ViewGetter.getFromAdapterView(gridView, 3);
        View item2 = ViewGetter.getFromAdapterView(gridView, 8);
        if (null == item1 || null == item2) {
            return;
        }
        new Curtain(this)
                .with(item1)
                .with(item2)
                .show();
    }

    private void showGuideInItemChild() {
        View item1 = ViewGetter.getFromAdapterView(gridView, 4);
        View item2 = ViewGetter.getFromAdapterView(gridView, 9);
        if (null == item1 || null == item2) {
            return;
        }
        new Curtain(this)
                .withShape(item1.findViewById(R.id.image), new CircleShape())
                .with(item2.findViewById(R.id.tv_text))
                .show();
    }

    public class GridAdapter extends BaseArrayAdapter {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View result;
            if (null != convertView) {
                result = convertView;
            } else {
                result = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_view, parent, false);
            }
            ((TextView) (result.findViewById(R.id.tv_text))).setText(data[position]);
            return result;
        }
    }

}
