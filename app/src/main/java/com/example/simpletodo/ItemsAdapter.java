package com.example.simpletodo;

import android.service.autofill.TextValueSanitizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// displays data from the model into a row in the recycler view
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{
    public interface OnLongClickListener {
        void onItemLongClicked(int position);
    }

    List<String> items;
    OnLongClickListener longClickListener;

    public ItemsAdapter(List<String> items, OnLongClickListener onLongClickListener) {
        this.items = items;
        this.longClickListener = onLongClickListener;
    }

    @NonNull
    @Override
    // creates each view
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // use layout inflator to inflate a view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(todoView);
    }

    @Override
    // take data at position and puts in viewholder - bind data to particular view holder
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // grab item at position
        String item = items.get(position);

        // bind the intem into specified view holder
        holder.bind(item);

    }

    @Override
    // number of items in list --tells the reccler view
    public int getItemCount() {
        return this.items.size();
    }

    // container to provide easy access to views that represent each row at the list
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItem = itemView.findViewById(android.R.id.text1);
        }

        // update view inside of the view holder
        public void bind(String item) {
            tvItem.setText(item);

            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // notify item was long passed, we notifying listerer of position
                    longClickListener.onItemLongClicked(getAdapterPosition());

                    // callback is doing long click
                    return true;
                }
            });
        }
    }
}
