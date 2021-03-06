package cravebot.results.elysi.results;

        import java.io.BufferedReader;
        import java.io.InputStreamReader;
        import java.net.MalformedURLException;
        import java.net.URI;
        import java.net.URL;
        import java.util.ArrayList;

        import org.apache.http.HttpResponse;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.methods.HttpGet;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.apache.http.util.EntityUtils;
        import org.json.JSONArray;
        import org.json.JSONObject;

        import android.content.Context;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.util.Log;
        import android.widget.Toast;

        import cravebot.results.elysi.results.TestResult;

/**
 *
 * @author Christoffer Kho
 *
 */
public class GoTask extends AsyncTask<String,Void,ArrayList<FoodItem>> {
    //to get the data in the CardLayout, use this key LIST_KEY
    public static final String LIST_KEY = "FoodItemList";

    //this is the main activity, considering that this asynctask will only be accessed from
    //the go button
    Context context;
    //filters
    boolean[] filterClicked;
    //seek bar values
    double seekBarMin;
    double seekBarMax;
    //temporary link
    String link = "http://cravebot.ph/androidGetFoodResto.php?";
    boolean isFirst = true;

    /**
     * @constructor GoTask
     * @param context - this is the previous activity
     * @param filterClicked - the boolean filters
     * @param seekBarMin - seekbar minimum value in double
     * @param seekBarMax - seekbar maximum value in double
     */
    public GoTask(Context context, boolean[] filterClicked, double seekBarMin, double seekBarMax){
        this.context = context;
        //in order: (total 12)
        //beef, beverage, burger, chicken, dessert, fruit, noodle, pizza, pork, seafood, setmeal, snack
        this.filterClicked = filterClicked;
        this.seekBarMin = seekBarMin;
        this.seekBarMax = seekBarMax;

        //get filters
        if(filterClicked[0] == true && isFirst == true){
            link += "beef=1";
            isFirst = false;
        }
        else if(filterClicked[0] == true && isFirst == false)
            link += "&beef=1";

        if(filterClicked[1] == true && isFirst == true){
            link += "beverage=1";
            isFirst = false;
        }
        else if(filterClicked[1] == true && isFirst == false)
            link += "&beverage=1";

        if(filterClicked[2] == true && isFirst == true){
            link += "burger=1";
            isFirst = false;
        }
        else if(filterClicked[2] == true && isFirst == false)
            link += "&burger=1";

        if(filterClicked[3] == true && isFirst == true){
            link += "chicken=1";
            isFirst = false;
        }
        else if(filterClicked[3] == true && isFirst == false)
            link += "&chicken=1";

        if(filterClicked[4] == true && isFirst == true){
            link += "dessert=1";
            isFirst = false;
        }
        else if(filterClicked[4] == true && isFirst == false)
            link += "&dessert=1";

        if(filterClicked[5] == true && isFirst == true){
            link += "fruit=1";
            isFirst = false;
        }
        else if(filterClicked[5] == true && isFirst == false)
            link += "&fruit=1";

        if(filterClicked[6] == true && isFirst == true){
            link += "noodle=1";
            isFirst = false;
        }
        else if(filterClicked[6] == true && isFirst == false)
            link += "&noodle=1";

        if(filterClicked[7] == true && isFirst == true){
            link += "pizza=1";
            isFirst = false;
        }
        else if(filterClicked[7] == true && isFirst == false)
            link += "&pizza=1";

        if(filterClicked[8] == true && isFirst == true){
            link += "pork=1";
            isFirst = false;
        }
        else if(filterClicked[8] == true && isFirst == false)
            link += "&pork=1";

        if(filterClicked[9] == true && isFirst == true){
            link += "seafood=1";
            isFirst = false;
        }
        else if(filterClicked[9] == true && isFirst == false)
            link += "&seafood=1";

        if(filterClicked[10] == true && isFirst == true){
            link += "setmeal=1";
            isFirst = false;
        }
        else if(filterClicked[10] == true && isFirst == false)
            link += "&setmeal=1";

        if(filterClicked[11] == true && isFirst == true){
            link += "snack=1";
            isFirst = false;
        }
        else if(filterClicked[11] == true && isFirst == false)
            link += "&snack=1";
    }

    @Override
    protected void onPreExecute(){
        //start a loading dialog or spinner (the robot gif? can also just be a spinner)
    }

    //All communication with the database is done here
    //Please consult Kitop before changing something here!
    @Override
    protected ArrayList<FoodItem> doInBackground(String... params) {
        // TODO Auto-generated method stub
        ArrayList<FoodItem> list = new ArrayList<FoodItem>();
        ArrayList<Resto> restoList = new ArrayList<Resto>();
        try {
            //URL url = new URL(link);
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(link));
            Log.d("doInBackGround", "set URI");

            HttpResponse response = client.execute(request);
            Log.d("doInBackGround", "execute");

            String json = EntityUtils.toString(response.getEntity());
            Log.d("doInBackGround", "get json string");

            JSONObject req = new JSONObject(json);
            Log.d("doInBackGround", "jsonobject req");

            int success = req.getInt("success");
            if(success == 1){

                JSONArray resto = req.getJSONArray("Resto");
                JSONArray food = req.getJSONArray("FoodItem");
                Log.d("doInBackGround", "jsonarrays recognized");

                for (int i = 0; i < resto.length(); ++i) {
                    JSONObject newResto = resto.getJSONObject(i);
                    Log.d("doInBackGround", "jsonobject resto");

                    Resto newRestoItem = new Resto();

                    newRestoItem.setRestoId(newResto.getInt("restoid"));
                    Log.d("doInBackGround", Integer.toString(newRestoItem.getRestoId()));
                    Log.d("doInBackGround", newResto.toString());

                    newRestoItem.setRestoName(newResto.getString("restoname"));
                    Log.d("doInBackGround", newRestoItem.getRestoName());

                    newRestoItem.setRestoLogo(newResto.getString("restologo"));
                    Log.d("doInBackGround", newRestoItem.getRestoLogo());

                    restoList.add(newRestoItem);
                }


                //restoName, restoLogo, notes, itemName, description, option1, price1, option2, price2,
                //option3, price3, option4, price4, option5, price5, option6, price6, photo;
                for (int i = 0; i < food.length(); ++i) {
                    JSONObject newFood = food.getJSONObject(i);
                    Log.d("doInBackGround", "jsonobject food");

                    String newFoodString = newFood.toString();
                    Log.d("doInBackGround", newFood.getString("itemname") + " :" + newFoodString);

                    double price = newFood.getDouble("price");
                    Log.d("doInBackGround", Double.toString(price));
                    if(price <= seekBarMax && price >= seekBarMin){
                        FoodItem newFoodItem = new FoodItem();

                        newFoodItem.setPrice(price);


                        newFoodItem.setItemName(newFood.getString("itemname"));
                        Log.d("doInBackGround", newFoodItem.getItemName());
                        newFoodItem.setDescription(newFood.getString("description"));
                        newFoodItem.setOption1(newFood.getString("option1"));
                        newFoodItem.setPrice1(newFood.getString("price1"));
                        newFoodItem.setOption2(newFood.getString("option2"));
                        newFoodItem.setPrice2(newFood.getString("price2"));
                        newFoodItem.setOption3(newFood.getString("option3"));
                        newFoodItem.setPrice3(newFood.getString("price3"));
                        newFoodItem.setOption4(newFood.getString("option4"));
                        newFoodItem.setPrice4(newFood.getString("price4"));
                        newFoodItem.setOption5(newFood.getString("option5"));
                        newFoodItem.setPrice5(newFood.getString("price5"));
                        newFoodItem.setOption6(newFood.getString("option6"));
                        newFoodItem.setPrice6(newFood.getString("price6"));
                        newFoodItem.setPhoto(newFood.getString("photo"));

                        int restoId = newFood.getInt("restoid");
                        for(Resto r: restoList){
                            if(r.getRestoId() == restoId){
                                newFoodItem.setRestoName(r.getRestoName());
                                newFoodItem.setRestoLogo(r.getRestoLogo());
                                newFoodItem.setNotes(r.getSurveyLink());
                                break;
                            }
                        }

                        list.add(newFoodItem);
                    }
                }

                Log.d("doInBackGround", "done");
            }

            return list;
        }

        catch(Exception e){
            Log.d("doInBackGround", e.toString());
            return null;
        }

    }//doInBackGround ends here

    //Do all UI work here.
    @Override
    protected void onPostExecute(ArrayList<FoodItem> result){
        //if you used a loading dialog/spinner in onPreExecute(), make sure to disable it here.

        if(result.size() != 0){
            try {
                Log.d("doInBackGround", "size " + Integer.toString(result.size()));
                Intent intent = new Intent(context, TestResult.class);
                Log.d("doInBackGround", "new intent");
                //Intent intent = new Intent(context, CardLayout.class);
                intent.putParcelableArrayListExtra(LIST_KEY, result);
                Log.d("doInBackGround", "put in arraylist");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                Log.d("doInBackGround", "go!");
            } catch (Exception e) {
                // TODO Auto-generated catch block

                Log.d("doInBackGround", e.toString());
            }
        }
        else{
            //this is temporary. You may change this!
            Toast toast = Toast.makeText(context, "No results!", Toast.LENGTH_LONG);
            toast.show();
        }

    }

}

