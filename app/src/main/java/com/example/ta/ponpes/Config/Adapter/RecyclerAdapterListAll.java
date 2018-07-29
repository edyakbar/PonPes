package com.example.ta.ponpes.Config.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ta.ponpes.Config.Config;
import com.example.ta.ponpes.Config.Model.ResultAll;
import com.example.ta.ponpes.DetailPonPesActivity;
import com.example.ta.ponpes.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by edy akbar on 26/06/2018.
 */

public class RecyclerAdapterListAll extends RecyclerView.Adapter<RecyclerAdapterListAll.ViewHolder> {
    private ArrayList<ResultAll> result;
    private Context context;

    public RecyclerAdapterListAll(ArrayList<ResultAll> result, Context context) {
        this.context = context;
        this.result = result;
    }

    @Override
    public RecyclerAdapterListAll.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_row_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterListAll.ViewHolder viewHolder, int i) {
        viewHolder.t_nama.setText(result.get(i).getNama_ponpes());
        viewHolder.t_alamat.setText(result.get(i).getAlamat());
        viewHolder.t_program.setText(result.get(i).getProgram());
        viewHolder.t_idjenis.setText(result.get(i).getJenis());

        Glide.with(context).load(Config.URL_IMG+result.get(i).getGambar())
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .error(R.drawable.ponpestegal)
                .into(viewHolder.img_list);
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public void setFilter(ArrayList<ResultAll> models) {
        result = new ArrayList<>();
        result.addAll(models);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.t_nama)
        TextView t_nama;
        @BindView(R.id.t_idjenis)
        TextView t_idjenis;
        @BindView(R.id.t_program)
        TextView t_program;
        @BindView(R.id.t_alamat)
        TextView t_alamat;
        @BindView(R.id.img_recycler)
        ImageView img_list;
        @BindView(R.id.cardview)
        CardView cardView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == cardView) {
                int i = getAdapterPosition();
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailPonPesActivity.class);
                intent.putExtra("id_ponpes", result.get(i).getId());
                intent.putExtra("nm_ponpes", result.get(i).getNama_ponpes());
                intent.putExtra("alamat", result.get(i).getAlamat());
                intent.putExtra("profil", result.get(i).getProfil());
                intent.putExtra("kegiatan", result.get(i).getKegiatan());
                intent.putExtra("lt", result.get(i).getLatitude());
                intent.putExtra("lg", result.get(i).getLongitude());
                intent.putExtra("syarat", result.get(i).getSyarat());
                intent.putExtra("gambar", result.get(i).getGambar());
                intent.putExtra("jml_pengajar", result.get(i).getJml_pengajar());
                intent.putExtra("jml_santri", result.get(i).getJml_santri());
                intent.putExtra("fasilitas", result.get(i).getFasilitas());
                intent.putExtra("sekitar", result.get(i).getSekitar());

                intent.putExtra("jenis", result.get(i).getJenis());
                intent.putExtra("program", result.get(i).getProgram());
                intent.putExtra("ijin", result.get(i).getIjin());

                context.startActivity(intent);
            }
        }
    }
}
