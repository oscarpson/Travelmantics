package com.oscar.Travelmantics.model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oscar.Travelmantics.R;


/**
 * The type Deal view holder.
 */
public class DealViewHolder  extends RecyclerView.ViewHolder {
    /**
     * The Txt title.
     */
    public TextView txtTitle;
    /**
     * The Txt description.
     */
    public TextView txtDescription;
    /**
     * The Txt price.
     */
    public TextView txtPrice;
    /**
     * The Img deal.
     */
    public ImageView imgDeal;

    /**
     * Instantiates a new Deal view holder.
     *
     * @param itemView the item view
     */
    public DealViewHolder(@NonNull View itemView) {
        super(itemView);
        txtTitle=itemView.findViewById(R.id.txt_title);
        txtDescription=itemView.findViewById(R.id.txt_deal_description);
        txtPrice=itemView.findViewById(R.id.txt_deal_price);
        imgDeal=itemView.findViewById(R.id.img_deal);

    }
}
