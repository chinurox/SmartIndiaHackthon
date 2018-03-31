package com.example.gargc.smartindiahackthon.Activity.Startup;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gargc.smartindiahackthon.Model.StartupFragmentModel;
import com.example.gargc.smartindiahackthon.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gargc on 30-03-2018.
 */

public class ViewProfileAdapter extends RecyclerView.Adapter<ViewProfileAdapter.ViewHolder> {

    Context context;
    List<StartupFragmentModel> arrayList;
    int i=0;

    public ViewProfileAdapter(Context context, List<StartupFragmentModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardviewforstartupprofile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.startupAbout.setText(arrayList.get(position).getAbout());
        holder.startupdescription.setText(arrayList.get(position).getDescription());
        holder.startupName.setText(arrayList.get(position).getName());
        Picasso.with(context).load(arrayList.get(position).getLogo()).placeholder(R.drawable.coinvest).into(holder.imageViewOfStartup);
        ArrayList<String> emailId=arrayList.get(position).getCoFounderEmail();

        EmailAdapter emailAdapter=new EmailAdapter(emailId,context);
        holder.startupRecyclerView.setAdapter(emailAdapter);
        emailAdapter.notifyDataSetChanged();;

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewOfStartup;
        TextView startupName, startupAbout, startupdescription;
        RecyclerView startupRecyclerView;

        public ViewHolder(View view) {
            super(view);

            startupName = (TextView) view.findViewById(R.id.profilestartupname);
            startupAbout = (TextView) view.findViewById(R.id.profilestartupabout);
            startupdescription = (TextView) view.findViewById(R.id.profilestartupdescription);
            startupRecyclerView = (RecyclerView) view.findViewById(R.id.startuprecyclerview);
            imageViewOfStartup = (ImageView) view.findViewById(R.id.profileimagestartup);


                startupRecyclerView=(RecyclerView) view.findViewById(R.id.startuprecyclerview);
                startupRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                startupRecyclerView.setHasFixedSize(true);


        }
    }

}
