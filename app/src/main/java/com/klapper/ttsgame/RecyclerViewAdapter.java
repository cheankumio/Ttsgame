package com.klapper.ttsgame;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.klapper.ttsgame.reference.message;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by c1103304 on 2017/7/21.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<message> messagelist;
    private Context context;

    public RecyclerViewAdapter(Context context) {
        this.messagelist = new ArrayList<>();
        this.context = context;
    }

    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.messagebox,parent,false));
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, int position) {
        if(messagelist.get(position).getType()==1){ // 0 = 機器人
            holder.rv1.setVisibility(View.VISIBLE);
            holder.rv2.setVisibility(View.GONE);
            holder.tv1.setText(messagelist.get(position).getMessage());
        }else{
            holder.rv2.setVisibility(View.VISIBLE);
            holder.rv1.setVisibility(View.GONE);
            holder.tv2.setText(messagelist.get(position).getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout rv1,rv2;
        TextView tv1,tv2;
        public MyViewHolder(View view) {
            super(view);
            tv1 = (TextView)view.findViewById(R.id.textView1);
            tv2 = (TextView)view.findViewById(R.id.textView2);
            rv1 = (RelativeLayout)view.findViewById(R.id.rv1);
            rv2 = (RelativeLayout)view.findViewById(R.id.rv2);
        }
    }

    public void addMessage(int type, String str){
        messagelist.add(new message(type,str));
        notifyItemInserted(messagelist.size());
    }
}
