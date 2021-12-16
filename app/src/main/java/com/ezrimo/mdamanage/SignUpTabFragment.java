package com.ezrimo.mdamanage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class SignUpTabFragment extends Fragment {
    Button signupButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.singup_tab_fragment, container, false);
        signupButton = root.findViewById(R.id.signupB);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "signUp", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}
