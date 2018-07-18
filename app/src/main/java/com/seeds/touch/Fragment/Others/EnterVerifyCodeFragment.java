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
import com.seeds.touch.R;

public class EnterVerifyCodeFragment extends Fragment {
    private Button button;
    private String person;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_fp_enterverifycode, container, false);
        findViews(view);
        person=getArguments().getString("PERSON");
        handleListeners(view);
        return  view;
    }

    private void handleListeners(View view) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredCode=((EditText)view.findViewById(R.id.send_code_edittext)).getText().toString();
                if(enteredCode!=null && !enteredCode.isEmpty())
                {
                    Fragment fragment = new ChangePasswordFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    Bundle bundle=new Bundle();
                    bundle.putString("PERSON",new Gson().toJson(person));
                    fragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.forgetpassword1fragment, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else
                {
                    Toast.makeText(view.getContext(),"FILL!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void findViews(View view) {
        button=(Button)view.findViewById(R.id.confirmButton);

    }

}
