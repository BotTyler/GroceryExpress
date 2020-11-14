package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;


import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends BaseAdapter {
    Context context;
    List<ListEntity> rowData;
    static int temp = Color.WHITE;
    ViewHolder holder = null;


    public ListAdapter(Context context, List<ListEntity> list) {
        this.context = context;
        this.rowData = list;

    }
    /*private view holder class*/
    private class ViewHolder {

        TextView title1;
        //TextView title2;
        Button btn;
        int fav;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_list, null);
            holder = new ViewHolder();
            holder.title1 = (TextView) convertView.findViewById(R.id.ListNameHolder);
            //holder.title2 = (TextView) convertView.findViewById(R.id.ListName2Holder);
            holder.btn = (Button) convertView.findViewById(R.id.FavoriteBtnHolder);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }



        ListEntity rowItem = (ListEntity) getItem(position);
        holder.title1.setText(rowItem.getListName());
        holder.title1.setTextColor(Color.WHITE);
        ViewHolder finalHolder = holder;
        Log.d("TAG", finalHolder.fav + "");
        finalHolder.fav = ((ListEntity)getItem(position)).getFavorite();
        if(finalHolder.fav == 0){
            finalHolder.btn.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        }else{
            finalHolder.btn.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#FFFF00")));
        }


        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalHolder.fav = ((ListEntity)getItem(position)).getFavorite();
                Log.d("TAG", finalHolder.fav + "");
                if(finalHolder.fav == 0){
                    //finalHolder.btn.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#FFFF00")));
                    ((ListEntity)getItem(position)).setFavorite(1);
                }else{
                    //finalHolder.btn.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    ((ListEntity)getItem(position)).setFavorite(0);
                }
                MainActivity.myDB.listDao().update((ListEntity)getItem(position));
                updateList();

            }
        });

       // holder.imageView.setImageResource(rowItem.getImageId());
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
    public void updateList(){
        List<ListEntity> listEntities = MainActivity.myDB.listDao().getAllList();
        ListAdapter lists = new ListAdapter(context, listEntities);
        ArrayAdapter<ListEntity> l = new ArrayAdapter<ListEntity>(context,android.R.layout.simple_list_item_1, listEntities);
        MainActivity.groceryListView.setAdapter(lists);


    }
}
