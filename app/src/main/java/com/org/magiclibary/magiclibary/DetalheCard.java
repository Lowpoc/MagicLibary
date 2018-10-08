package com.org.magiclibary.magiclibary;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Objects;

public class DetalheCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_card);

        TextView name = findViewById(R.id.nameCard);
        TextView description = findViewById(R.id.description);
        SimpleDraweeView image = findViewById(R.id.imageUrl);

        name.setText(Objects.requireNonNull(getIntent().getExtras().get("name")).toString());
        description.setText(Objects.requireNonNull(getIntent().getExtras().get("description")).toString());
        Uri uri = Uri.parse(Objects.requireNonNull(getIntent().getExtras().get("url")).toString());
        image.setImageURI(uri);
    }
}
