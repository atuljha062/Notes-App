package com.atul.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    List<Notes> data;
    RecyclerClickListner recyclerClickListner;
    Context context;

    public RecyclerAdapter(List<Notes> data, RecyclerClickListner recyclerClickListner, Context context) {
        this.data = data;
        this.recyclerClickListner = recyclerClickListner;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_item_layout,parent,false);
        return new RecyclerViewHolder(view,recyclerClickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        holder.title.setText(data.get(position).getTitle());
        holder.description.setText(data.get(position).getDescription());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotesDataHelper db = new NotesDataHelper(context);
                Boolean checkDelete = db.deleteNotes(data.get(position).getID());

                if(checkDelete){
                    Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    MainActivity.data.clear();
                    MainActivity.storeDataInArrays(context);
                    MainActivity.adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(context, "Error Deleting", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, description;
        ImageView delete;

        RecyclerClickListner recyclerClickListner;

        public RecyclerViewHolder(@NonNull View itemView, RecyclerClickListner recyclerClickListner) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.notesTitle);
            description = (TextView) itemView.findViewById(R.id.notesDescription);
            delete = (ImageView) itemView.findViewById(R.id.deleteButton);

            itemView.setOnClickListener(this);

            this.recyclerClickListner = recyclerClickListner;
        }

        @Override
        public void onClick(View v) {
            recyclerClickListner.onClick(getAbsoluteAdapterPosition());
        }
    }

    public interface RecyclerClickListner{
        void onClick(int position);
    }
}
