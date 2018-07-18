package com.seeds.touch.Server;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.seeds.touch.Configuration.Converter;
import com.seeds.touch.Configuration.Setting;
import com.seeds.touch.Entity.Entities.Item;
import com.seeds.touch.Entity.Entities.Person;
import com.seeds.touch.Management.Interface.CurrentTimeInterface;
import com.seeds.touch.Management.Interface.PostTimeInterface;
import com.seeds.touch.Management.Manager.MainActivity;
import com.seeds.touch.Technical.Enums;
import com.seeds.touch.Technical.Helper;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.seeds.touch.Configuration.Setting.USER_INFORMATION_SHARED_PREFERENCES_TABLE;
import static com.seeds.touch.Technical.Enums.ActivityRepository.MAIN_ACTIVITY;

public class Server {
    private static final String REGISTER_PERSON_URL = "https://hcuot.com/SeedS/register_userprofile.php";
    private static final String LOGIN_PERSON_URL = "https://hcuot.com/SeedS/login_userprofile.php";
    private static final String COMPLETE_USER_PROFILE_URL = "https://hcuot.com/SeedS/complete_user_profile.php";

    public static void registerUserProfile(Context context, RequestQueue requestQueue,
                                           @NonNull String phoneNumber, @NonNull String userName, @NonNull String password,
                                           PostTimeInterface onPost) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_PERSON_URL, response -> {
            Object[] objects = new Object[2];
            try {
                Log.d(Helper.LOG_TOUCH_ERROR, "RESPONSEE : " + response);
                JSONObject job = new JSONObject(response);
                objects[0] = job.getString("state");
                if (objects[0].toString().equals("done")) {
                    Person person = new Person();
                    person.setID(userName);
                    person.setPassword(password);
                    person.setPhoneNumber(phoneNumber);
                    objects[1] = person;
                }

            } catch (Exception e) {
                Log.d(Helper.LOG_TOUCH_ERROR, "fdfgor response,try : " + e.getMessage());
            }
//            try {
//                //onPost.does(objects);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }, error -> {
            Log.d(Helper.LOG_TOUCH_ERROR, "fonbmbnr error,try : " + error.getMessage());
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("test", "test");
                params.put("ID", "SDF");//Setting.encode_Default(userName));
                params.put("Password","CVXB");// Setting.encode_Default(password));
                params.put("PhoneNumber", "CXVV");//Setting.encode_Default(phoneNumber));
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    public static void loginUserProfile(Context context, RequestQueue requestQueue, @NonNull Person person, PostTimeInterface onPost) {
        Gson gson = new Gson();
        Object[] arr = new Object[2];
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_PERSON_URL, response -> {
            try {
                Log.d(Helper.LOG_TOUCH_ERROR, "LL : " + response);
                JSONObject job = new JSONObject(response);
                if (job.getString("state").equals("done")) {
                    arr[0] = "done";
                    Person userAccountPerson = new Person();
                    userAccountPerson.setID(Setting.decode_Default(job.getString("ID")));
                    userAccountPerson.setPassword(Setting.decode_Default(job.getString("Password")));
                    userAccountPerson.setName(Setting.decode_Default(job.getString("Name")));
                    userAccountPerson.setLastName(Setting.decode_Default(job.getString("LastName")));
                    userAccountPerson.setGender(gson.fromJson(Setting.decode_Default(job.getString("Gender")), Enums.Gender.class));
                    userAccountPerson.setBirthdate(Converter.MYSQLI_StringToCalendar(Setting.decode_Default(job.getString("Birthdate"))));
                    userAccountPerson.setPhoneNumber(Setting.decode_Default(job.getString("PhoneNumber")));
                    userAccountPerson.setBiography(Setting.decode_Default(job.getString("Bio")));
                    userAccountPerson.setLocation(gson.fromJson(Setting.decode_Default(job.getString("Location")), Location.class));
                    userAccountPerson.setMacAddress(Setting.decode_Default(job.getString("McAddress")));
                    arr[1] = userAccountPerson;
                    onPost.does(arr);
                } else {
                    arr[0] = "failed";
                    Toast.makeText(context, "Login failed", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.d(Helper.LOG_TOUCH_ERROR, "try : " + e.getMessage());
            }
        }, error -> {
            Log.d(Helper.LOG_TOUCH_ERROR, "for error, try : " + error.getMessage());
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("test", "test");
                params.put("ID", Setting.encode_Default(person.getID()));
                params.put("Password", Setting.encode_Default(person.getPassword()));
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    public static void editUserAccount(@NonNull String id, @NonNull Person newPerson, RequestQueue requestQueue, CurrentTimeInterface onPost) {
        Gson gson = new Gson();
        Object[] objects = new Object[1];
        StringRequest stringRequest = new StringRequest(Request.Method.POST, COMPLETE_USER_PROFILE_URL, response -> {
            try {
                JSONObject job = new JSONObject(response);
                if (job.getString("state").equals("done")) {
                    objects[0] = "done";
                } else {
                    objects[0] = "failed";
                }
                onPost.does(objects);

            } catch (Exception e) {
                Log.d(Helper.LOG_TOUCH_ERROR, "for response,try : " + e.getMessage());
            }
        }, error -> {
            Log.d(Helper.LOG_TOUCH_ERROR, "for error,try : " + error.getMessage());
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("test", "test");
                params.put("ID", Setting.encode_Default(id));
                params.put("Name", Setting.encode_Default(newPerson.getName()));
                params.put("LastName", Setting.encode_Default(newPerson.getLastName()));
                params.put("Bio", Setting.encode_Default(newPerson.getBiography()));
                params.put("McAddress", Setting.encode_Default(newPerson.getMacAddress()));
                params.put("PhoneNumber", Setting.encode_Default(newPerson.getPhoneNumber()));
                params.put("Password", Setting.encode_Default(newPerson.getPassword()));
                params.put("Location", Setting.encode_Default(gson.toJson(newPerson.getLocation())));
                params.put("Birthdate", Setting.encode_Default(gson.toJson(newPerson.getBirthdate())));
                params.put("Followers", Setting.encode_Default(gson.toJson(newPerson.getFollowers())));
                params.put("Followings", Setting.encode_Default(gson.toJson(newPerson.getFollowings())));
                params.put("FollowersQueue", Setting.encode_Default(gson.toJson(newPerson.getFollowersQueue())));
                params.put("FollowingsQueue", Setting.encode_Default(gson.toJson(newPerson.getFollowingsQueue())));
                params.put("Gender", Setting.encode_Default(gson.toJson(newPerson.getGender())));
                params.put("Items", Setting.encode_Default(gson.toJson(newPerson.getItems())));
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }

    public static void getUserProfile(String id,PostTimeInterface onPost) throws Exception {

        Person person=new Person();
        // server operations
            person.setName("Charlie");
        //
        Object[] objects=new Object[1];
        objects[0]=new Gson().toJson(person);
        onPost.does(objects);

    }

    public static void editItemDetails(String databaseID, Item item, PostTimeInterface onPost) {
        Object[] objects=new Object[2];
        objects[0]="done";
        objects[1]=new Gson().toJson(new Item("2",null,null,null,
                null,"fg",null,null,12,null,null));
        try {
            onPost.does(objects);
        } catch (IOException e) {
            Log.d(Helper.LOG_TOUCH_ERROR,e.getMessage());
        }
    }

}
