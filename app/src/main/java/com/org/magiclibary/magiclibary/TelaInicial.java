package com.org.magiclibary.magiclibary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class TelaInicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
    }

    public void showCards(View view) {
        Toast.makeText(this.getApplicationContext(), "Cards", Toast.LENGTH_LONG).show();
    }

    public void showSets(View view) {
        Toast.makeText(this.getApplicationContext(), "Sets", Toast.LENGTH_LONG).show();
    }
}
