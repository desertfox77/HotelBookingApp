package com.example.gehen.arraydb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.gehen.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class HotelJSONAdapter extends ArrayAdapter<HotelJSON> implements Filterable {

    private Context context;

    private List<HotelJSON> mOriginalValues;
    private List<HotelJSON> getmOriginalValues;

    public HotelJSONAdapter(Context context, List<HotelJSON> mOriginalValues) {
        super(context, R.layout.item_product, mOriginalValues);
        this.context = context;
        this.mOriginalValues = mOriginalValues;
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View listViewItem = inflater.inflate(R.layout.item_product, null, true);


        TextView tvProductName = listViewItem.findViewById(R.id.tv_productName);
        TextView tvProductAddress = listViewItem.findViewById(R.id.tv_productAddress);
        TextView tvProductPrice = listViewItem.findViewById(R.id.tv_productPrice);

        HotelJSON p = mOriginalValues.get(i);
        tvProductName.setText(p.getHotelname());
        tvProductAddress.setText(p.getHoteladdress());
        tvProductPrice.setText("Rp. " + p.getHotelprice());

        return listViewItem;
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                mOriginalValues = (List<HotelJSON>) results.values; // has

                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<HotelJSON> FilteredArrList = new ArrayList<HotelJSON>();

                if (getmOriginalValues == null) {
                    getmOriginalValues = new ArrayList<HotelJSON>(mOriginalValues);

                }

                /********
                 *
                 * If constraint(CharSequence that is received) is null returns
                 * the mOriginalValues(Original) values else does the Filtering
                 * and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {


                    results.count = getmOriginalValues.size();
                    results.values = getmOriginalValues;
                } else {
                    Locale locale = Locale.getDefault();
                    constraint = constraint.toString().toLowerCase(locale);
                    for (int i = 0; i < getmOriginalValues.size(); i++) {
                        HotelJSON model = getmOriginalValues.get(i);

                        String data = model.getHotelname();
                        if (data.toLowerCase(locale).contains(constraint.toString())) {

                            FilteredArrList.add(model);
                        }
                    }

                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;

                }
                return results;
            }
        };
        return filter;
    }



}
