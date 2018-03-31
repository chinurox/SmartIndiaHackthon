package com.example.gargc.smartindiahackthon.Activity.Investor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.gargc.smartindiahackthon.Model.Startup;
import com.example.gargc.smartindiahackthon.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by gargc on 22-03-2018.
 */

public class StartupAdapter extends RecyclerView.Adapter<StartupAdapter.MyStartupHolder>{


    List<Startup> list;
    ArrayList<String> startupName,startupPhoneNumber,email;
    Context mContext;

    StartupAdapter(List<Startup> list,ArrayList<String> startupName,ArrayList<String> startupPhoneNumber,Context mContext,ArrayList<String> email)
    {
        this.list=list;
        this.startupName=startupName;
        this.startupPhoneNumber=startupPhoneNumber;
        this.mContext=mContext;
        this.email=email;
    }


    @Override
    public MyStartupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single , parent , false);

        return new MyStartupHolder(view);
    }

    @Override
    public void onBindViewHolder(MyStartupHolder holder, int position) {

        holder.startupDescription.setText(list.get(position).getDescription());
       holder.startupPhoneNumber.setText(startupPhoneNumber.get(position));
        holder.startupNameExpanded.setText(startupName.get(position));
          holder.startupName.setText(list.get(position).getName());
          holder.startupCategory.setText(list.get(position).getCategory());
        holder.startupAbout.setText(list.get(position).getAbout());
        holder.email.setText(email.get(position));
//
        Picasso.with(mContext).load(list.get(position).getCover()).placeholder(R.drawable.user_default)
                .into(holder.startupCover);
        Picasso.with(mContext).load(list.get(position).getLogo()).placeholder(R.drawable.user_default)
                .into(holder.statupImageLogo);

        holder.viewPitchDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,ViewPichDeck.class);
                mContext.startActivity(intent);
            }
        });


        holder.favoriteButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    DatabaseReference mLikeDatabase = FirebaseDatabase.getInstance().getReference().child("Favorites");


                }
                else {

                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyStartupHolder extends RecyclerView.ViewHolder
    {
        ImageView startupCover,startupLogoExpanded;
        CircleImageView statupImageLogo;
        TextView startupName,startupCategory,startupAbout,startupNameExpanded,startupPhoneNumber,startupDescription,email;

        ToggleButton favoriteButton;

        TextView viewPitchDeck;


        public MyStartupHolder(View itemView) {
            super(itemView);

            statupImageLogo=(CircleImageView)itemView.findViewById(R.id.startupimagelogo);
            startupName=(TextView) itemView.findViewById(R.id.title_from_address);
           startupCategory=(TextView) itemView.findViewById(R.id.title_to_address);
            startupAbout=(TextView) itemView.findViewById(R.id.startupabout);
//
            startupCover=(ImageView) itemView.findViewById(R.id.head_image);
//            startupLogoExpanded=(ImageView) itemView.findViewById(R.id.startuplogo1);
            startupNameExpanded=(TextView) itemView.findViewById(R.id.content_name_view);
            startupPhoneNumber=(TextView) itemView.findViewById(R.id.content_to_address_1);
            startupDescription=(TextView) itemView.findViewById(R.id.startupdescription);

            viewPitchDeck=(TextView) itemView.findViewById(R.id.content_request_btn);


            email=(TextView) itemView.findViewById(R.id.content_to_address_2);

            final FoldingCell fc = (FoldingCell) itemView.findViewById(R.id.folding_cell);
            fc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fc.toggle(false);
                }
            });

            favoriteButton=(ToggleButton) itemView.findViewById(R.id.head_image_right_text);



        }
    }
}
