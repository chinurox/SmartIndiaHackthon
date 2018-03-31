package com.example.gargc.smartindiahackthon.Activity.Startup;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gargc.smartindiahackthon.R;

import java.util.ArrayList;

/**
 * Created by gargc on 30-03-2018.
 */

public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.ViewHolder> {


    ArrayList<String> arrayList;
    Context context;

    EmailAdapter(ArrayList<String> arrayList,Context context)
    {
        this.arrayList=arrayList;
        this.context=context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.startupfounderprofile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView=(TextView) itemView.findViewById(R.id.cofounderemail);
        }
    }
}
