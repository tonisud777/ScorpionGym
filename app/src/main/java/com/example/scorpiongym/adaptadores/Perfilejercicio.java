package com.example.scorpiongym.adaptadores;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scorpiongym.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Perfilejercicio extends AppCompatActivity {

    ImageView perfilim;
    TextView text1, text2;
    private DatabaseReference mdatareferenceu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfilejercicio);
        perfilim = (ImageView) findViewById(R.id.idimagentrabajador);
        text1 = (TextView) findViewById(R.id.idnombretrabajador);
        text2 = (TextView) findViewById(R.id.iddescripciontrabajador);

        mdatareferenceu = FirebaseDatabase.getInstance().getReference().child("dieta");

        Intent intent = getIntent();
        String key = intent.getExtras().getString("key");




        mdatareferenceu.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String title = (String) dataSnapshot.child("name").getValue();
                String desc = (String) dataSnapshot.child("descripcion").getValue();
                String imagen  = (String) dataSnapshot.child("imagen").getValue();
                Picasso.get().load(imagen).into(perfilim);


                text1.setText(""+title);
                text2.setText(desc);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}