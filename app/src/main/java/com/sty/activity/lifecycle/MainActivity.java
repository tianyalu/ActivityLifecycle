package com.sty.activity.lifecycle;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"Activity--onCreate()");
        setContentView(R.layout.activity_main);


        android.app.Fragment tFragment = new TFragment();
        getFragmentManager().beginTransaction().add(R.id.FLayout,tFragment).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"Activity--onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"Activity--onResumed()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"Activity--onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"Activity--onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"Activity--onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"Activity--onDestroy()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG,"Activity--onSaveInstanceState()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG,"Activity--onRestoreInstanceState()");
    }

    public static class TFragment extends android.app.Fragment{

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            Log.e(TAG,"Fragment--onAttach()");
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.e(TAG,"Fragment--onCreate()");
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            Log.e(TAG,"Fragment--onCreateView()");
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Log.e(TAG,"Fragment--onActivityCreated()");
        }

        @Override
        public void onStart() {
            super.onStart();
            Log.e(TAG,"Fragment--onStart()");
        }

        @Override
        public void onResume() {
            super.onResume();
            Log.e(TAG,"Fragment--onResume()");
        }

        @Override
        public void onPause() {
            super.onPause();
            Log.e(TAG,"Fragment--onPause()");
        }

        @Override
        public void onStop() {
            super.onStop();
            Log.e(TAG,"Fragment--onStop()");
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            Log.e(TAG,"Fragment--onDestroyView()");
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.e(TAG,"Fragment--onDestroy()");
        }

        @Override
        public void onDetach() {
            super.onDetach();
            Log.e(TAG,"Fragment--onDetach()");
        }
    }
}