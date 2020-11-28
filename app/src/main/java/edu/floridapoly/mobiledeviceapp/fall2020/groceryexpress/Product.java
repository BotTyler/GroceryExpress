package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import java.net.*;

public class Product extends AsyncTask<Integer, Void, Integer>{
    private String url;
    private Activity context;
    private String html;
    private ArrayList<WebObject> webList;
    private ListView listView;
    private ItemEntity item;
    private int pageNumber = 1; // might use this idk yet

    ProgressDialog progressDialog;

    // use this when you want to insert the item into the database
    // if minprice is not specified att -1
    public Product(Activity context, ItemEntity item, double minPrice){
        this.item = item;
        this.context = context;
        this.url = getUrl(item.getName(), minPrice);

    }



    // use this when you want to get a list of obj back from the scraper
    // if minprice is not provided use -1
    public Product(Activity context, String itemName, ListView listView, double minPrice){
        this.context = context;
        this.listView = listView;
        this.url = getUrl(itemName, minPrice);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Showing progress dialog
        webList = new ArrayList<>();
        progressDialog = new ProgressDialog(context);

        progressDialog.setMessage("Fetching data");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        if (progressDialog.isShowing())
            progressDialog.dismiss();
        if(result == 1 && webList.get(0) != null){
            item.setPrice(webList.get(0).getPrice());
            item.setLocation(webList.get(0).getLocation());
            item.setUrl(webList.get(0).getUrl());
            item.setAltPrice(webList.get(0).getAltPrice());
            item.setWebName(webList.get(0).getName());

            MainActivity.myDB.itemDao().insertItem(item);

            Log.d("Output", "Name = " + item.getName());
            Log.d("Output", "webName = " + item.getWebName());
            Log.d("Output", "location = " + item.getLocation());
            Log.d("Output", "price = " + item.getPrice());
            Log.d("Output", "altPrice = " + item.getAltPrice());
            Log.d("Output", "url = " + item.getUrl());


            context.finish();

        }else if(result > 1){
            WebObjectListAdapter adapter = new WebObjectListAdapter(context,webList);
            listView.setAdapter(adapter);
        }

    }




    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("rawHtml.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }






    // pValue[0] = how many entities you want loaded
    // pValue[1] = how many pages you want to load (NEEDS IMPLEMENTING)
    @Override
    protected Integer doInBackground(Integer... pValue) {
        try {
            Document doc = Jsoup.connect(url).timeout(6000).get();

            //writeToFile("", context);
           // Log.d("RawHtml", );

            Elements products = doc.getElementsByClass("sh-dlr__list-result");
            Log.d("html", products.html());

            if(webList == null){
                return null;
            }
            //for(int counter = 0; counter < pValue[1]; counter ++) {
                for (int i = 0; i < Math.min(pValue[0], products.size()); i++) {
                    Element item = products.get(i);

                    WebObject obj = new WebObject();

                    obj.setName(getItemName(item));
                    obj.setLocation(getLocationName(item));
                    obj.setPrice(getItemPrice(item));
                    obj.setAltPrice(getItemPriceAlt(item));
                    obj.setUrl(getItemLink(item));


                    webList.add(obj);



                }


            //}

        }catch (IOException e){ e.printStackTrace(); };

        return pValue[0];
    }

    private String getItemName(Element doc){
        String html = doc.html();

        String tempNameHTMLPlaceHolder = html.substring(html.indexOf("<h3"), html.indexOf("</h3")); // might change this

        int startNamePos = tempNameHTMLPlaceHolder.indexOf(">") + 1;

        String name = tempNameHTMLPlaceHolder.substring(startNamePos).replace("&amp;", "&");
        return name;
    }

    private String getLocationName(Element doc){
        String LocationName = "";
        Elements a = doc.select("div");
        for(int i = 0 ; i < a.size(); i ++){
            if(a.get(i).className().equals("puehic")){
                LocationName = a.get(i+1).text();
            }
            //Log.d("Child0", i + ":::: " + a.get(i).className() + '\n'+'\n'+'\n');
            //Log.d("Child1", i + ":::: " + a.get(i).text() + '\n'+'\n'+'\n');
        }
     return LocationName;
    }

    private Double getItemPrice(Element doc){
        String html = doc.html();
        int startPricePos = html.indexOf(">$")+2;
        int endPricePos = html.indexOf("<", startPricePos);
        double price = 0;
        try{
            String tempPrice =html.substring(startPricePos, endPricePos).replace(",", "");
            Log.d("PriceChecker", tempPrice);
            price = Double.parseDouble(tempPrice);
            return price;
        }catch (Exception e){
            Log.w("InvalidType", "Tried to parse string to double and failed.");
            return 0.0;
        }

    }
    private String getItemPriceAlt(Element doc){
        String html = doc.html();
        int startPricePos = html.indexOf(">$")+2;
        int endPricePos = html.indexOf("<", startPricePos);
        String tempPrice =html.substring(startPricePos, endPricePos).replace(",", "");
        return tempPrice;
    }

    private String getItemLink(Element doc){
        Element links = doc.select("a").get(0);
        String html = links.toString();
        //Log.d("LocationHTML", html);
        int startIndex = html.indexOf("<a");
        int endIndex = html.indexOf(">", startIndex);
        //Log.d("LocationHTML", "Start = " + startIndex + " End = " + endIndex);
        String locationLine = html.substring(startIndex, endIndex);
        startIndex = locationLine.indexOf("href=") + 6;
        endIndex = locationLine.indexOf('"', startIndex);


        Log.d("LocationHTML", locationLine);
        Log.d("LocationHTML", "Start = " + startIndex + " End = " + endIndex);

        String link = "https://www.google.com"+locationLine.substring(startIndex, endIndex).replace("&amp;", "&");
        //Log.d("Links", link);
        return link;
    }
    //http://shopping.google.com/search?q=graphics+card&tbs=price:1,ppr_min:600
    private String getUrl(String itemName, double minPrice){
        // need to add sorting by price
        String minPriceText = "";
        if(minPrice > -1)
            minPriceText = ",price:1,ppr_min:" + minPrice;
        else
            minPriceText= "";
        String url = "https://shopping.google.com/search?q=" + (itemName.replace(' ', '+')) + "&tbs=p_ord:p,vw:l" + minPriceText;
        Log.d("url", url);
            return url;
    }

    public ArrayList<WebObject> getWebList() {
        return webList;
    }
}

