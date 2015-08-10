package com.example.polycap.materialdesignretrofit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by Polycap on 8/6/2015.
 */
public class NavAdapter extends RecyclerView.Adapter<NavAdapter.NavViewHolder> {

    private final LayoutInflater inflater;
    private List<NavList> data = Collections.emptyList();
    private Context context;
//    private ClickListener clickListener;

    public NavAdapter(Context context, List<NavList> data) {
        this.context=context;
        inflater = LayoutInflater.from(context);
        this.data=data;


    }

    @Override
    public NavViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.nav_row, parent, false);
        return new NavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NavViewHolder navViewHolder, int position) {
        NavList current = data.get(position);
        navViewHolder.title.setText(current.title);
        navViewHolder.icon.setImageResource(current.IconId);
        navViewHolder.icon.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Young Nigga Living it", Toast.LENGTH_SHORT).show();
            }
        });

    }

//    public void setClickListener(ClickListener clickListener){
//        this.clickListener=clickListener;
//    }

    @Override
    public int getItemCount() {
        return 0;
    }



    class NavViewHolder extends RecyclerView.ViewHolder{

        final TextView title;
        final ImageView icon;

        public NavViewHolder(View itemView) {
            super(itemView);

//            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.navText);
            icon = (ImageView) itemView.findViewById(R.id.navIcon);

        }

//        @Override
//        public void onClick(View view) {
//            context.startActivity(new Intent(context, DetailsActivity.class));
//
//            if (clickListener != null){
//                clickListener.itemClicked(view,getPosition());
//            }


        }
    }
//    public interface ClickListener {
//        public void itemClicked (View view, int position);
//    }

