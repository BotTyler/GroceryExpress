package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends BaseAdapter {
    Context context;
    List<ItemEntity> rowData;
    public ItemAdapter(Context context, List<ItemEntity> items) {
        this.context = context;
        this.rowData = items;
    }
    /*private view holder class*/
    private class ViewHolder {

        TextView txtTitle;
        TextView txtWebTitle;
        TextView txtPrice;
        TextView txtLocation;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.txtPrice = (TextView) convertView.findViewById(R.id.ItemPrice);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.ItemTitle);
            holder.txtWebTitle = (TextView)convertView.findViewById(R.id.ItemWebTitle);
            holder.txtLocation = (TextView) convertView.findViewById(R.id.ItemLocation);
           // holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        ItemEntity rowItem = (ItemEntity) getItem(position);
        String price = "";
        if(rowItem.getPrice() > 0.0){
            price = "$" + rowItem.getPrice();

        }else if(rowItem.getPrice() == 0.0 && !rowItem.getAltPrice().isEmpty()){
            price = "$" + rowItem.getAltPrice();

        }else{
            price = "Error: Price Not Found";

        }
        holder.txtPrice.setText("$"+rowItem.getPrice());
        holder.txtLocation.setText("Location: " + rowItem.getLocation());
        holder.txtTitle.setText(rowItem.getName());
        holder.txtWebTitle.setText(rowItem.getWebName());

        //holder.imageView.setImageResource(rowItem.getImageId());
        return convertView;
    }
    @Override
    public int getCount() {
        return rowData.size();
    }
    @Override
    public Object getItem(int position) {
        return rowData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return rowData.indexOf(getItem(position));
    }
}
