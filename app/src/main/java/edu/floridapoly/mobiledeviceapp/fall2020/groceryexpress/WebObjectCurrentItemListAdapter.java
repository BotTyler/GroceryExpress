package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class WebObjectCurrentItemListAdapter extends BaseAdapter {

    Activity activity;
    List<WebObject> webObjectList;
    LayoutInflater inflater;

    public WebObjectCurrentItemListAdapter(Activity activity, List<WebObject> webObjectList) {
        this.activity = activity;
        this.webObjectList = webObjectList;
        this.inflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return webObjectList.size();
    }

    @Override
    public Object getItem(int i) {
        return webObjectList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public List<WebObject> getItems(){
        return this.webObjectList;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;

        if (view == null){

            view = inflater.inflate(R.layout.web_object_current_layout, viewGroup, false);

            holder = new ViewHolder();

            holder.name = view.findViewById(R.id.WebObjectTitle);
            // holder.cheapestPrice = view.findViewById(R.id.WebObjectCheapestPriceText);
            holder.currentPrice = view.findViewById(R.id.WebObjectCurrentPriceText);
            holder.location = view.findViewById(R.id.WebObjectLocationText);

            view.setTag(holder);
        }else
            holder = (ViewHolder)view.getTag();

        WebObject webObject = webObjectList.get(i);

        holder.name.setText(webObject.getName());
        // holder.cheapestPrice.setText(webObject.getCheapestPrice() + "");
        String price = "";
        if(webObject.getPrice() > 0.0){
            price = "$" + webObject.getPrice();
        }
        else if(webObject.getPrice() == 0.0 && !webObject.getAltPrice().isEmpty()){
            price = "$" + webObject.getAltPrice();
        }else{
            price = "Error: Price Not Found";
        }
        holder.currentPrice.setText(price);
        holder.location.setText(webObject.getLocation());


        return view;

    }

    public void updateRecords(List<WebObject>  webObjectList){
        this.webObjectList = webObjectList;
        notifyDataSetChanged();
    }


    class ViewHolder{

        TextView name;
        TextView currentPrice;
        //TextView cheapestPrice;
        TextView location;
        //ImageView ivCheckBox;

    }
}
