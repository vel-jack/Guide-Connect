package com.hsv.connect.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.hsv.connect.FindPeople;
import com.hsv.connect.R;
import com.hsv.connect.register.RegisterDomain;
import com.hsv.connect.register.SignUp;

public class SearchPeople extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_people);
    }

    public void findUsers(View view) {
        Spinner s_dom   = (Spinner)findViewById(R.id.domain_spinner);
        Spinner s_field = (Spinner)findViewById(R.id.field_spinner);
        Intent i = new Intent(SearchPeople.this,FindPeople.class);
        i.putExtra("domain",s_dom.getSelectedItem().toString());
        i.putExtra("field",s_field.getSelectedItem().toString());
        startActivity(i);
        finish();
    }
}
