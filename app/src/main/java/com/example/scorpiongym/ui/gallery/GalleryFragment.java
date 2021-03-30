package com.example.scorpiongym.ui.gallery;

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
import com.example.scorpiongym.adaptadores.Perfilejercicio;
import com.example.scorpiongym.entidades.Contact;
import com.example.scorpiongym.ui.home.HomeFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class GalleryFragment extends Fragment {
    View v;
    private GalleryViewModel galleryViewModel;
    private RecyclerView CallrecyclerView2;
    private DatabaseReference mDatabase2;
    private FirebaseRecyclerAdapter<Contact, GalleryFragment.NewsViewHolder> mPeopleRVAdapter2;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        CallrecyclerView2 = (RecyclerView) root.findViewById(R.id.callrecyclerview2);

        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("dieta");
        mDatabase2.keepSynced(true);


        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference().child("dieta");
        Query personsQuery = personsRef.orderByKey();


        CallrecyclerView2.hasFixedSize();
        CallrecyclerView2.setLayoutManager(new GridLayoutManager(getContext(),1));

        FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<Contact>().setQuery(personsQuery, Contact.class).build();
        mPeopleRVAdapter2 = new FirebaseRecyclerAdapter<Contact, GalleryFragment.NewsViewHolder>(personsOptions) {
            @Override
            protected void onBindViewHolder(@NonNull GalleryFragment.NewsViewHolder holder, int position, @NonNull final Contact model) {

                final String key=getRef(position).getKey();
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), Perfilejercicio.class);
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
            public GalleryFragment.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_item, parent, false);

                return new GalleryFragment.NewsViewHolder(view);
            }
        };

        CallrecyclerView2.setAdapter(mPeopleRVAdapter2);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPeopleRVAdapter2.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPeopleRVAdapter2.stopListening();


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