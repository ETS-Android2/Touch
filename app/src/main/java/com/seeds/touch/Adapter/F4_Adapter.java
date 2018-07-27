package com.seeds.touch.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seeds.touch.Entity.Entities.News;
import com.seeds.touch.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class F4_Adapter extends RecyclerView.Adapter<F4_Adapter.ViewHolder> {

    private List<News> list;

    public F4_Adapter(List<News> list) {
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView tv1=holder.view.findViewById(R.id.news_text_tv);
        TextView tv2=holder.view.findViewById(R.id.news_time_tv);

        tv1.setText(list.get(position).getText());
        tv2.setText(new SimpleDateFormat("yyyy/MM/dd").format(list.get(position).getTime().getTime()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }

}
