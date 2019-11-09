package com.img.snt.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.img.snt.Model.Item;
import com.img.snt.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    List<Item> items;
    private LayoutInflater inflater;
    private Context mContext;
    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    private final OnItemClickListener listener;
    public ItemAdapter(List<Item> items, Context mContext,OnItemClickListener listener) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        Collections.sort(items);
        Collections.reverse(items);
        this.items = items;
        this.listener = listener;
    }
    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        if(position%4==0 || position==0){
            return 1;

        }else return  0;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            //case 0: return new MyViewHolder(inflater.inflate(R.layout.row,parent,false));
            case 1: return new MyViewHolder(inflater.inflate(R.layout.row2,parent,false));
            default: return new MyViewHolder(inflater.inflate(R.layout.row,parent,false));
        }
//        View itemView = inflater.inflate(R.layout.row,parent,false);
//        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtTitle.setText(items.get(position).getTitle());
        holder.txtPubDate.setText(items.get(position).getPubDate());
        holder.txtContent.setText(items.get(position).getSourceName());
        String imageUrl = items.get(position).getThumbnail();
        if(imageUrl!= null && imageUrl.length()>10 ){
            Picasso.get().load(imageUrl).into(holder.image);
           //Picasso.get().
        }
        holder.bind(items.get(position), listener);

        // holder.txtContent.setText(items.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle,txtPubDate,txtContent;
        public ImageView image;
        //private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = (TextView)itemView.findViewById(R.id.txtTitle);
            txtPubDate = (TextView)itemView.findViewById(R.id.txtPubDate);
            txtContent = (TextView)itemView.findViewById(R.id.txtContent);
            image = (ImageView) itemView.findViewById(R.id.imageView);
        }
        public void bind(final Item item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
//    public class MyViewHolder2 extends RecyclerView.ViewHolder {
//        public TextView txtTitle,txtPubDate,txtContent;
//        public ImageView image;
//        //private ItemClickListener itemClickListener;
//        public MyViewHolder2(@NonNull View itemView) {
//            super(itemView);
//            txtTitle = (TextView)itemView.findViewById(R.id.txtTitle);
//            txtPubDate = (TextView)itemView.findViewById(R.id.txtPubDate);
//            txtContent = (TextView)itemView.findViewById(R.id.txtContent);
//            image = (ImageView) itemView.findViewById(R.id.imageView);
//        }
//        public void bind(final Item item, final OnItemClickListener listener) {
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override public void onClick(View v) {
//                    listener.onItemClick(item);
//                }
//            });
//        }
//    }
}
