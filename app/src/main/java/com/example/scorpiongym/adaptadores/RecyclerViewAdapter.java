package com.example.scorpiongym.adaptadores;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scorpiongym.R;
import com.example.scorpiongym.entidades.Contact;
import com.example.scorpiongym.ui.home.HomeFragment;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>  implements View.OnClickListener{

    Context mContext;
    List<Contact> mData;
    Dialog myDialog;
    HomeFragment mFragment;


    public RecyclerViewAdapter(Context mContex, List<Contact> mData, HomeFragment mFragment) {
        this.mContext = mContex;
        this.mData = mData;
        this.mFragment = mFragment;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.layout_item,parent,false);
        final MyViewHolder vHolder = new MyViewHolder(v);

        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.activity_perfil_datos);

        vHolder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView dialog_name = (TextView) myDialog.findViewById(R.id.idnombretrabajador);
                TextView dialog_descripcion = (TextView) myDialog.findViewById(R.id.iddescripciontrabajador);
                ImageView dialog_img = (ImageView)myDialog.findViewById(R.id.idimagentrabajador);

                Intent intent = new Intent(mContext, PerfilDatos.class);
                intent.putExtra("title",mData.get(viewType).getTitle());
                intent.putExtra("desc",mData.get(viewType).getDescripcion());
                intent.putExtra("image",mData.get(viewType).getImagen());

                dialog_name.setText(mData.get(vHolder.getAdapterPosition()).getName());
                dialog_descripcion.setText(mData.get(vHolder.getAdapterPosition()).getDescripcion());
                dialog_img.setImageResource(Integer.parseInt(mData.get(vHolder.getAdapterPosition()).getImagen()));
                myDialog.show();


            }
        });
        return vHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.titulo1.setText(mData.get(position).getName());
        holder.contenido1.setText((CharSequence) mData.get(position).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout contact;
        private TextView titulo1;
        private TextView contenido1;
        private ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);

            contact = (LinearLayout) itemView.findViewById(R.id.lista_elemento);
            titulo1 = (TextView) itemView.findViewById(R.id.idnombretrabajador);
            contenido1 = (TextView) itemView.findViewById(R.id.iddescripciontrabajador);
            img = (ImageView) itemView.findViewById(R.id.idimagentrabajador);

        }
    }
}
