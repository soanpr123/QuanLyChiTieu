package com.soanlv.duan1android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.soanlv.duan1android.R;

public class header extends AppCompatActivity {
    private TextView name, email;
    String Name, Email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_man_chinh);
        name = findViewById(R.id.nameAVT);
        email = findViewById(R.id.emailAVT);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Name = bundle.getString("name");
            Email = bundle.getString("email");


        }
        name.setText(Name);
        email.setText(Email);
    }
}
