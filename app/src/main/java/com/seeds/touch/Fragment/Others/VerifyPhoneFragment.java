package com.seeds.touch.Fragment.Others;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.seeds.touch.Entity.Entities.Person;
import com.seeds.touch.R;

public class VerifyPhoneFragment extends Fragment {
    private Button verifyButton;
    private Person person;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fp_verify_phone, container, false);
        findViews(view);
        this.person = new Gson().fromJson(getArguments().getString("PERSON"), Person.class);
        handleListeners(view);

        return view;
    }

    private void handleListeners(View view) {
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String last4digits = ((EditText) view.findViewById(R.id.fourdigitedittext)).getText().toString();
                if (last4digits == null || last4digits.isEmpty()) {
                    Toast.makeText(view.getContext(), "should be filled", Toast.LENGTH_SHORT).show();
                } else {
                    if (last4digits.equalsIgnoreCase(person.getPhoneNumber().substring(person.getPhoneNumber().length() - 4))) {
                        //send the code //

                        //////////////////
                        Fragment fragment= new EnterVerifyCodeFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("PERSON",new Gson().toJson(person));
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.forgetpassword1fragment, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    } else {
                        Toast.makeText(view.getContext(), "Wrong Info :)  ", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void findViews(View view) {
        verifyButton = (Button) view.findViewById(R.id.button_send);
    }

}
