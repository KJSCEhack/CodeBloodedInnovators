package spit.comps.collegemate.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import junit.framework.Test;

import spit.comps.collegemate.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home2Fragment extends Fragment {


    CarouselView HomeFragment_CarouselView;
    TextView mission,vision;


    public Home2Fragment() {
        // Required empty public constructor
    }


    public static Home2Fragment newInstance(){
        Home2Fragment mainFragment = new Home2Fragment();
        return mainFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        HomeFragment_CarouselView=(CarouselView)view.findViewById(R.id.HomeFragment_CarouselView);
        HomeFragment_CarouselView.setPageCount(4);
        HomeFragment_CarouselView.setViewListener(carouselViewListener);

        mission = (TextView)view.findViewById(R.id.HomeFragment_Tab2Mission);
        vision = (TextView)view.findViewById(R.id.HomeFragment_Tab2Vision);

        return view;
    }


    ViewListener carouselViewListener=new ViewListener() {
        @Override
        public View setViewForPosition(int position) {

            RelativeLayout HomeFragment_CarouselItemContainer=(RelativeLayout)getActivity().findViewById(R.id.HomeFragment_CarouselItemContainer);

            View carouselItemView=getActivity().getLayoutInflater().inflate(R.layout.fragment_home_carousel_item,HomeFragment_CarouselItemContainer,false);
            ImageView HomeFragment_CarouselItemImageView=(ImageView)carouselItemView.findViewById(R.id.HomeFragment_CarouselItemImageView);
            TextView HomeFragment_CarouselItemTextView=(TextView)carouselItemView.findViewById(R.id.HomeFragment_CarouselItemTextView);

            loadImageintoCarouselfromFirebase(position,HomeFragment_CarouselItemImageView,HomeFragment_CarouselItemTextView);

            return carouselItemView;
        }
    };


    public void loadImageintoCarouselfromFirebase(final int position, final ImageView imageView, final TextView textView)
    {
        if (position==0)
        {
            imageView.setImageResource(R.drawable.navmenuheaderbg);

            textView.setText("SPIT Mumbai");

            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/firstfirebase-a064e.appspot.com/o/Entrance.jpg?alt=media&token=33ccc6aa-9bc1-489e-98c2-376b7a8d4f4e").into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        }

        else if (position==1)
        {
            imageView.setImageResource(R.drawable.navmenuheaderbg);
            textView.setText("Hello");
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        else if (position==2)
        {
            imageView.setImageResource(R.drawable.navmenuheaderbg);
            textView.setText("Hello");
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        else if (position==3)
        {
            imageView.setImageResource(R.drawable.navmenuheaderbg);
            textView.setText("Hello");
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

}
