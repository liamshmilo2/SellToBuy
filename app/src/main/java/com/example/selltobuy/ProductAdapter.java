package com.example.selltobuy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selltobuy.classes.Product;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private ArrayList<Product> products;

    private View.OnClickListener mOnItemClickListener;

    public ProductAdapter(ArrayList<Product> productsArrayList) {
        this.products = productsArrayList;
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleritem_product,parent,false);
        return new ProductViewHolder(productView);
    }

    public void setmOnItemClickListener (View.OnClickListener itemClickListener)
    {
        mOnItemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product currentProduct = products.get(position);
        holder.nameTextView.setText(currentProduct.getName());
        holder.priceTextView.setText(currentProduct.getPrice()+"");
        holder.startTextView.setText(currentProduct.getStratDate().toString());
        holder.finalTextView.setText(currentProduct.getFinalDate().toString());

        holder.imageView.setImageBitmap(currentProduct.getImage());

        //holder.imageView.setImageResource(holder.nameTextView.getResources().getIdentifier(currentProduct.getImage(),"drawable",holder.nameTextView.getContext().getPackageName()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public  TextView priceTextView;
        public  TextView startTextView;
        public  TextView finalTextView;
        public ImageView imageView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textview_name);
            priceTextView = itemView.findViewById(R.id.textview_price);
            startTextView = itemView.findViewById(R.id.textview_strat);
            finalTextView = itemView.findViewById(R.id.textview_final);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }
}
