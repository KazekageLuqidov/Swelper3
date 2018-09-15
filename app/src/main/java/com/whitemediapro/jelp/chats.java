package com.whitemediapro.jelp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whitemediapro.jelp.R;

/**
 * Created by Lucky on 07/03/2018.
 */

public class chats extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.chats, container, false);
        return rootView;
    }
}
