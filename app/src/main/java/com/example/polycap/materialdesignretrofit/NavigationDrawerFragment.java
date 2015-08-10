package com.example.polycap.materialdesignretrofit;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {

    private static final String Pref_File_Name = "testpref";
    private static final String Key_User_Learned_Drawer = "user_learned_drawer";
    private ActionBarDrawerToggle mDrawerToggle;

    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    private static void savedToPreferences(Context context, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Pref_File_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NavigationDrawerFragment.Key_User_Learned_Drawer, preferenceValue);
        editor.apply();

    }

    private static String readFromPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Pref_File_Name, Context.MODE_PRIVATE);
        return sharedPreferences.getString(NavigationDrawerFragment.Key_User_Learned_Drawer, "false");

    }

    private static List<NavList> getData() {

        List<NavList> data = new ArrayList<>();
        int[] icons = {R.drawable.ic_home_black_24dp, R.drawable.ic_event_seat_black_24dp,
                R.drawable.ic_hdr_weak_white_24dp, R.drawable.ic_nature_people_white_24dp,
                R.drawable.ic_face_black_24dp};

        String[] titles = {"Home,Throne,Circle,Nature,Niggaz"};

        for (int i = 0; i < titles.length && i < icons.length; i++) {
            NavList current = new NavList();
            current.IconId = icons[i];
            current.title = titles[i];
            data.add(current);
        }
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity()));

        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        NavAdapter navAdapter = new NavAdapter(getActivity(), getData());
//        navAdapter.setClickListener(this);
        recyclerView.setAdapter(navAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Toast.makeText(getActivity(),"Young Nigga Click"+ position,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return layout;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        View containerView = getActivity().findViewById(fragmentId);
        DrawerLayout mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    savedToPreferences(getActivity(), true + "");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }


        };

        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {

            mDrawerLayout.openDrawer(containerView);

        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener){

            this.clickListener= clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {

                    View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
                    if (child !=null && clickListener !=null){
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                    super.onLongPress(e);
                }
            });

        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(),e.getY());

            if (child !=null && clickListener !=null && gestureDetector.onTouchEvent(e)){
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {


        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public static interface ClickListener{
        public void onClick(View view, int position);
        public void onLongClick (View view, int position);
    }



//    @Override
//    public void itemClicked(View view, int position) {
//        startActivity(new Intent(getActivity(),DetailsActivity.class));
//
//    }
}
