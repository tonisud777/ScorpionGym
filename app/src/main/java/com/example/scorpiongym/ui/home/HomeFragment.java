package com.example.scorpiongym.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scorpiongym.R;
import com.example.scorpiongym.adaptadores.PerfilDatos;
import com.example.scorpiongym.entidades.Contact;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {
    View v;
    private HomeViewModel homeViewModel;
    private RecyclerView CallrecyclerView;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Contact, NewsViewHolder> mPeopleRVAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        CallrecyclerView = (RecyclerView) root.findViewById(R.id.callrecyclerview);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("gym");
        mDatabase.keepSynced(true);


        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference().child("gym");
        Query personsQuery = personsRef.orderByKey();


        CallrecyclerView.hasFixedSize();
        CallrecyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));

        FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<Contact>().setQuery(personsQuery, Contact.class).build();
        mPeopleRVAdapter = new FirebaseRecyclerAdapter<Contact, HomeFragment.NewsViewHolder>(personsOptions) {
            @Override
            protected void onBindViewHolder(@NonNull NewsViewHolder holder, int position, @NonNull final Contact model) {

                final String key=getRef(position).getKey();
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), PerfilDatos.class);
                        intent.putExtra("key", key);

                        startActivity(intent);
                    }
                });


                holder.setTitle(model.getName());
                holder.setDesc(model.getDescripcion());
                holder.setImage(model.getImagen());


            }

            @NonNull
            @Override
            public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_item, parent, false);

                return new HomeFragment.NewsViewHolder(view);
            }
        };

        CallrecyclerView.setAdapter(mPeopleRVAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPeopleRVAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPeopleRVAdapter.stopListening();


    }


    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String title) {
            TextView post_title = (TextView) mView.findViewById(R.id.texttitulo);
            post_title.setText(title);
        }

        public void setDesc(String desc) {
            TextView post_desc = (TextView) mView.findViewById(R.id.textdesc);
            post_desc.setText(desc);
        }

        public void setImage(String imagen) {
            ImageView post_image = (ImageView) mView.findViewById(R.id.imagen);
            Picasso.get().load(imagen).into(post_image);
        }
    }
}
