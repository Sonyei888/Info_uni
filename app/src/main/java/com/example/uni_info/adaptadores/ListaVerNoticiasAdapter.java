package com.example.uni_info.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uni_info.AdminEditarNoticia;
import com.example.uni_info.Entidades.Noticias;
import com.example.uni_info.R;

import java.util.ArrayList;
import java.util.List;

public class ListaVerNoticiasAdapter extends RecyclerView.Adapter<ListaVerNoticiasAdapter.VerNoticiasViewHolder> {
    List<Noticias> listanoticias;

    public ListaVerNoticiasAdapter(List<Noticias> listanoticias) {
        this.listanoticias = listanoticias;
    }

    @NonNull
    @Override
    public VerNoticiasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_ver_admin_noticias, null, false);
        return new ListaVerNoticiasAdapter.VerNoticiasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerNoticiasViewHolder holder, int position) {
        holder.view_nombre_noticia.setText(listanoticias.get(position).getNombre());
        holder.view_resumen.setText(listanoticias.get(position).getResumen());
        holder.view_fecha.setText(listanoticias.get(position).getFecha());
        holder.view_hora.setText(listanoticias.get(position).getHora());
    }

    @Override
    public int getItemCount() {
        return listanoticias.size();
    }

    public class VerNoticiasViewHolder extends RecyclerView.ViewHolder {
        TextView view_nombre_noticia;
        TextView view_resumen;
        TextView view_fecha;
        TextView view_hora;
        public VerNoticiasViewHolder(@NonNull View itemView) {
            super(itemView);
            view_nombre_noticia = itemView.findViewById(R.id.txt_titulo_noticia_admin);
            view_resumen = itemView.findViewById(R.id.txt_resumen_noticia_admin);
            view_fecha = itemView.findViewById(R.id.txt_fecha_admin);
            view_hora = itemView.findViewById(R.id.txt_hora_admin);
            Noticias noticias = new Noticias();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Toast.makeText(context, "Editar", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, AdminEditarNoticia.class);
                    intent.putExtra("ID", listanoticias.get(getPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
