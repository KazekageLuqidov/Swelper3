package com.whitemediapro.jelp; /**
 * Created by Lucky on 07/03/2018.
 */
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.View;
import android.support.v4.app.Fragment;


public class Nearby extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nearby, container, false);
        return rootView;
    }


}
