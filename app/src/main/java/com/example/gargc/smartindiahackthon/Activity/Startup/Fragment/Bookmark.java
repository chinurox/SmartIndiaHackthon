package com.example.gargc.smartindiahackthon.Activity.Startup.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gargc.smartindiahackthon.R;
import com.ramotion.foldingcell.FoldingCell;

/**
 * A simple {@link Fragment} subclass.
 */
public class Bookmark extends Fragment {


    FoldingCell f1,f2,f3,f4;
    public Bookmark() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bookmark, container, false);
        f1=(FoldingCell) view.findViewById(R.id.folding_cell);
        f2=(FoldingCell) view.findViewById(R.id.folding_cell1);
        f3=(FoldingCell) view.findViewById(R.id.folding_cell2);

        f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FoldingCell) view).toggle(false);

            }
        });

        f2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FoldingCell) view).toggle(false);

            }
        });

        f3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FoldingCell) view).toggle(false);

            }
        });
        return view;
    }

}
