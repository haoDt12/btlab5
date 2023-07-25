package com.example.lab5.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab5.Activity.EdActivity;
import com.example.lab5.Model.ProductModel;
import com.example.lab5.R;

import java.util.List;

public class TableListAdapter extends RecyclerView.Adapter<TableListAdapter.TableViewHolder> {
    private List<ProductModel> list;
    private Context context;

    public TableListAdapter(List<ProductModel> list, Context context) {
        this.list = list;
        this.context = context;
        notifyDataSetChanged();
    }
    public void setTableItems(List<ProductModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        ProductModel model = list.get(position);
        if (model == null) {
            return;
        }
        holder.nameTextView.setText(model.getName());
        holder.relativeLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, EdActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("id",model.getId());
            intent.putExtra("name",model.getName());
            intent.putExtra("price",String.valueOf(model.getPrice()));
            intent.putExtra("description",model.getDescription());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TableViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private RelativeLayout relativeLayout;

        public TableViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_name);
            relativeLayout = itemView.findViewById(R.id.id_relativeLayout);
        }
    }

}
