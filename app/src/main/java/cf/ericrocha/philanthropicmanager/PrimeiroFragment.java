package cf.ericrocha.philanthropicmanager;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * Created by rodrigo on 26/01/18.
 */

public class PrimeiroFragment  extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cashier, container, false);

        TextView tv = view.findViewById(R.id.text2);
        tv.setText("Você está na primeira aba");

        return view;
    }

}
