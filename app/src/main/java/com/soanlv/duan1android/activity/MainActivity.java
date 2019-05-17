package com.soanlv.duan1android.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.soanlv.duan1android.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private LoginButton btn_login;
    Button logout;
    private EditText username,Pass;
    ProfilePictureView image;
    CallbackManager callbackManager;
    String name, email;
    public static final String NAME = "name";
    public static final String Email = "description";
    public static final String BUNDLE = "bundel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login_main);
        btn_login = findViewById(R.id.login_button);
        username=findViewById(R.id.ed_username);
        Pass=findViewById(R.id.ed_passs);
        image = findViewById(R.id.imgamefb);
        callbackManager = CallbackManager.Factory.create();
        try {
            PackageInfo info = null;
            try {
                info = getPackageManager().getPackageInfo(
                        "com.soanlv.duan1android",
                        PackageManager.GET_SIGNATURES);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (NoSuchAlgorithmException e) {

        }
        btn_login.setReadPermissions(Arrays.asList("email"));
        setLogin();

    }

    private void setLogin() {
        btn_login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                rerout();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void rerout() {
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("soan", response.getJSONObject().toString());
                try {
                    name = object.getString("name");
                    email = object.getString("email");
                    image.setProfileId(Profile.getCurrentProfile().getId());
                    Intent intent2 = new Intent(MainActivity.this, ManChinhActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", name);
                    bundle.putString("email", email);
                    intent2.putExtras(bundle);
                    startActivity(intent2);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,email");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        LoginManager.getInstance().logOut();
        super.onStart();
    }

    public void login(View view) {
        String user=username.getText().toString();
        String passs=Pass.getText().toString();
        if (user.equals("admin")||passs.equals("admin")){
            Intent intent2 = new Intent(MainActivity.this, ManChinhActivity.class);
            startActivity(intent2);
        }else {
            Toast.makeText(MainActivity.this,"Sai",Toast.LENGTH_SHORT).show();
        }
    }
}
