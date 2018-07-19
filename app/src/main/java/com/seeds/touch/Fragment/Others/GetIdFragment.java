package com.seeds.touch.Fragment.Others;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.seeds.touch.Entity.Entities.Person;
import com.seeds.touch.R;
import com.seeds.touch.Server.Server;
import com.seeds.touch.Technical.GSON_Wrapper;
import com.seeds.touch.Technical.Helper;

public class GetIdFragment extends Fragment {
    private Button nextButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fp_get_id, container, false);
        findViews(view);
        handleListeners(view);
        return view;
    }

    private void handleListeners(View view) {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gottenID = ((EditText) view.findViewById(R.id.forget_username_edittext)).getText().toString();
                if (gottenID == null || gottenID.isEmpty()) {
                    Toast.makeText(view.getContext(), "Empty Field Should be Filled", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        Server.getUserProfile(gottenID, objects -> {
                            Fragment fragment = new VerifyPhoneFragment();
                            Bundle bundle=new Bundle();
                            bundle.putString("PERSON", GSON_Wrapper.getInstance().toJson((Person)objects[0]));
                            fragment.setArguments(bundle);
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.forgetpassword1fragment, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        });
                    } catch (Exception e) {
                        Log.d(Helper.LOG_TOUCH_ERROR, "Error While Get information of user from server");
                    }
                }
            }
        });
    }

    private void findViews(View view) {
        nextButton = (Button) view.findViewById(R.id.forget_next_button);
    }

}
