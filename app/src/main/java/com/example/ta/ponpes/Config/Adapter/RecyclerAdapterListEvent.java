package com.example.ta.ponpes.Config.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.ta.ponpes.Config.Model.ResultAllEvent;
import com.example.ta.ponpes.Config.Model.UserAPIService;
import com.example.ta.ponpes.DetailPonPesActivity;
import com.example.ta.ponpes.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by edy akbar on 26/06/2018.
 */

public class RecyclerAdapterListEvent extends RecyclerView.Adapter<RecyclerAdapterListEvent.ViewHolder> {
    private ArrayList<ResultAllEvent> result;
    private Context context;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    private UserAPIService mApiService;
    private  TextView nama_ponpes2,nama_kegiatan2,deskripsi2,tgl_kegiatan2;

    public RecyclerAdapterListEvent(ArrayList<ResultAllEvent> result, Context context) {
        this.context = context;
        this.result = result;
    }

    @Override
    public RecyclerAdapterListEvent.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_row_list_event, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterListEvent.ViewHolder viewHolder, int i) {
        viewHolder.t_nama.setText(result.get(i).getNama_ponpes());
        viewHolder.t_nama_kegiatan.setText(result.get(i).getNama_kegiatan());
       // viewHolder.t_deskripsi.setText(result.get(i).getDeskripsi());
        viewHolder.t_tgl_kegiatan.setText(result.get(i).getTgl_kegiatan());

        Glide.with(context).load(Config.URL_IMG_EVENT+result.get(i).getFoto())
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

    public void setFilter(ArrayList<ResultAllEvent> models) {
        result = new ArrayList<>();
        result.addAll(models);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.t_nama)
        TextView t_nama;
        @BindView(R.id.t_nama_kegiatan)
        TextView t_nama_kegiatan;
//        @BindView(R.id.t_deskripsi)
//        TextView t_deskripsi;
        @BindView(R.id.t_tgl_kegiatan)
        TextView t_tgl_kegiatan;
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


                dialog = new AlertDialog.Builder(context);
                // dialog = new android.support.v7.app.AlertDialog.Builder(ListRequestRuang.this);
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                dialogView = inflater.inflate(R.layout.detail_event, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);
                dialog.setIcon(R.mipmap.ic_launcher);
                dialog.setTitle("Detail");

               // mApiService = Config.getRetrofit();

                nama_ponpes2 = (TextView) dialogView.findViewById(R.id.nama_ponpes);
                nama_kegiatan2 = (TextView) dialogView.findViewById(R.id.nama_kegiatan);
                deskripsi2 = (TextView) dialogView.findViewById(R.id.deskripsi);
                tgl_kegiatan2 = (TextView) dialogView.findViewById(R.id.tanggal);



                nama_ponpes2.setText(result.get(i).getNama_ponpes());
                nama_kegiatan2.setText(result.get(i).getNama_kegiatan());
                deskripsi2.setText(result.get(i).getDeskripsi());
                tgl_kegiatan2.setText(result.get(i).getTgl_kegiatan());


                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        }
    }
}
