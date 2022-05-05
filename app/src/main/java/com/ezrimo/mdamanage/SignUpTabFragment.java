package com.ezrimo.mdamanage;

import static java.lang.Long.parseLong;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpTabFragment extends Fragment {
    Button signupButton;
    EditText email, fullName, type, password;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    boolean valid, isAdmin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.singup_tab_fragment, container, false);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        signupButton = root.findViewById(R.id.signupB);
        email = root.findViewById(R.id.email);
        fullName = root.findViewById(R.id.FullName);
        type = root.findViewById(R.id.type);
        password = root.findViewById(R.id.Password);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(email);
                checkField(fullName);
                checkField(type);
                checkField(password);

                if(!(checkField(email)||checkField(fullName)||checkField(type)||checkField(password))){
                    valid = false;
                    return;
                }
                isAdmin=false;
                if(!(type.getText().toString().equals("1")||type.getText().toString().equals("0"))){//if not equal to 1||0
                    Toast.makeText(getContext(), "enter only 1 or 0", Toast.LENGTH_SHORT).show();
                    type.setError("Error");
                    valid=false;
                }

                if(type.getText().toString().equals("1"))
                    isAdmin = true;

                if(valid){//if all the inputs are valid
                    User generalUser = new User(fullName.getText().toString(), email.getText().toString(), parseLong(type.getText().toString()));//creating local User
                    fAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            DocumentReference dr = fStore.collection("User").document(user.getUid());
                            Toast.makeText(root.getContext(), "signed up success", Toast.LENGTH_SHORT).show();
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("fullName", generalUser.getFullName());
                            userInfo.put("email", generalUser.getEmail());

                            //here we will specify if it's an admin
                            if(generalUser.getIsAdmin()==1) {
                                userInfo.put("isAdmin", 1);
                                dr.set(userInfo);
                                Intent go = new Intent(root.getContext(), adminActivity.class);
                                startActivity(go);
                                getActivity().finish();
                            }else{
                                userInfo.put("isAdmin", 0);
                                dr.set(userInfo);
                                Intent go = new Intent(root.getContext(), SuccessfullSU.class);
                                startActivity(go);
                                getActivity().finish();
                            }


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(root.getContext(), "failed to create account- "+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        return root;
    }

    /**
     * validates fields
     * @param textField input
     * @return if the field is valid t/f
     */
    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }
}
