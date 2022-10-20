package com.example.uni_info.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uni_info.Entidades.Noticias;
import com.example.uni_info.R;
import com.example.uni_info.usuNoticia;

import java.util.ArrayList;

public class ListaNoticiasAdapter extends RecyclerView.Adapter<ListaNoticiasAdapter.NoticiasViewHolder> {
    ArrayList<Noticias> listanoticias;

    public ListaNoticiasAdapter(ArrayList<Noticias> listanoticias) {
        this.listanoticias = listanoticias;
    }

    @NonNull
    @Override
    public NoticiasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_noticias, null, false);
        return new ListaNoticiasAdapter.NoticiasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticiasViewHolder holder, int position) {
        holder.view_nombre_noticia.setText(listanoticias.get(position).getNombre());
        holder.view_resumen.setText(listanoticias.get(position).getResumen());
        holder.view_fecha.setText(listanoticias.get(position).getFecha());
        holder.view_hora.setText(listanoticias.get(position).getHora());
    }

    @Override
    public int getItemCount() {
        return listanoticias.size();
    }

    public class NoticiasViewHolder extends RecyclerView.ViewHolder {
        TextView view_nombre_noticia;
        TextView view_resumen;
        TextView view_fecha;
        TextView view_hora;
        public NoticiasViewHolder(@NonNull View itemView) {
            super(itemView);
            view_nombre_noticia = itemView.findViewById(R.id.txt_titulo_noticia);
            view_resumen = itemView.findViewById(R.id.txt_resumen_noticia);
            view_fecha = itemView.findViewById(R.id.txt_fecha);
            view_hora = itemView.findViewById(R.id.txt_hora);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view_nombre_noticia.getContext();
                    Intent intent = new Intent(context, usuNoticia.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}
