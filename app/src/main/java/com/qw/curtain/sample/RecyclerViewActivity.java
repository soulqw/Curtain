package com.qw.curtain.sample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qw.curtain.lib.Curtain;
import com.qw.curtain.lib.ViewGetter;
import com.qw.curtain.lib.shape.CircleShape;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

public class RecyclerViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerAdapter());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_IDLE) {
                    showGuideInItemChild();
                }
            }
        });
        setContentView(recyclerView);
        //如果需要在初始化的时候调用 请用post
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                showGuideInItem();
            }
        });
    }

    private void showGuideInItem() {
        View item1 = ViewGetter.getFromRecyclerView(recyclerView, 1);
        View item2 = ViewGetter.getFromRecyclerView(recyclerView, 4);
        if (null == item1 || null == item2) {
            return;
        }
        new Curtain(this)
                .with(item1)
                .with(item2)
                .show();
    }

    private void showGuideInItemChild() {
        View item1 = ViewGetter.getFromRecyclerView(recyclerView, 2);
        View item2 = ViewGetter.getFromRecyclerView(recyclerView, 5);
        if (null == item1 || null == item2) {
            return;
        }
        new Curtain(this)
                .withShape(item1.findViewById(R.id.image), new CircleShape())
                .with(item2.findViewById(R.id.tv_text))
                .show();
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        String[] data = {"this", "is", "a", "better", "guide", "view", "generate", "lib", ".", "for", "android", "more", "and", "more", "useful", "to", "simplify", "your", "coding", ".", "have", "a", "nice", "day", "~"};

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_view, parent, false);
            return new MyRecyclerHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((TextView) (holder.itemView.findViewById(R.id.tv_text))).setText(data[position]);
        }

        @Override
        public int getItemCount() {
            return data.length;
        }
    }

    public class MyRecyclerHolder extends RecyclerView.ViewHolder {

        MyRecyclerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
