package com.wicoding.injaz.babycare;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

/**
 * Created by HunTerAnD1 on 18/05/2017.
 */

public class FragmentPlaces  extends Fragment {

    Spinner mSpinner;
    ArrayList<DataModelLocation> dataModels;
    ListView listView;
    View rootView;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 52;

    LocationManager locationManager;
    LocationListener locationListener;
    ProgressDialog dialog ;
    double LLag = 0,LLog = 0;

    public FragmentPlaces() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_places, container, false);

        listView=(ListView)rootView.findViewById(R.id.list_view);
        mSpinner = (Spinner) rootView.findViewById(R.id.spinner_location);

        mSpinner.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                showListData(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.location_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);


        dialog = new ProgressDialog(getActivity());
        locationManager  = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                dialog.dismiss();
                LLag = location.getLatitude();
                LLog = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }
            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        showListData(mSpinner.getSelectedItemPosition());

        return rootView;
    }


    private void showListData(int x)
    {
        final String xType = (x==0)?"hospital":(x==1)?"pharmacy":null;
        if(xType!=null)
        {
            //Ave Abderrahim Bouabid à Rabat, Agadir 80000, Morocco
            //Latitude: 30.408624 | Longitude: -9.585126
            //?location=-33.8670%2C151.1957&radius=500&types=food&name=cruise&key=API_KEY
            String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();

            if(LLag != 0 || LLog != 0){
                Log.d("Error",LLag+","+LLog);
                params.put("location", LLag+","+LLog);
                params.put("radius", "5000");
                params.put("types", xType);
                //params.put("rankby", "distance");
                params.put("key", GlobalApp.MyKeyPlacesAPI);
                client.get(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        try {
                            dataModels= new ArrayList<>();
                            int Icon = (xType=="hospital")?R.drawable.hospital:((xType=="pharmacy")?R.drawable.syrup:0);
                            JSONArray results  = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject c = results.getJSONObject(i);
                                JSONObject lanlog = c.getJSONObject("geometry").getJSONObject("location");
                                dataModels.add(new DataModelLocation(Icon,c.getString("name"),c.getString("vicinity"),lanlog.getString("lat")+","+lanlog.getString("lng")));
                            }

                            CustomAdapter cAdapter = new CustomAdapter(dataModels,getContext());
                            listView.setAdapter(cAdapter);

                        }catch (Exception e){}

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                    }
                });
            }
            else{
                dialog.setMessage("Please wait! 1");
                dialog.setCancelable(false);
                dialog.show();
                getLastLocation();
            }

        }
}

    private void getLastLocation(){
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.INTERNET}
                        ,REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300000, 10, locationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dialog.setMessage("Please wait! 2");
                    dialog.setCancelable(false);
                    dialog.show();
                    getLastLocation();
                } else {
                    Toast.makeText(getActivity(),"Permission Denied, GPS not active or permission for app .", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    public class CustomAdapter extends ArrayAdapter<DataModelLocation> implements View.OnClickListener{

        private ArrayList<DataModelLocation> dataSet;
        Context mContext;

        // View lookup cache
        private class ViewHolder {
            TextView txtTitle,txtSubTitle;
            ImageView info,infoGPS;
        }

        public CustomAdapter(ArrayList<DataModelLocation> data, Context context) {
            super(context, R.layout.item_location, data);
            this.dataSet = data;
            this.mContext=context;

        }

        @Override
        public void onClick(View v) {

            int position=(Integer) v.getTag();
            Object object= getItem(position);
            DataModelLocation dataModel=(DataModelLocation)object;

            switch (v.getId())
            {
                case R.id.imageView_gps:
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:"+dataModel.getLanLog()+"?q="+dataModel.getLanLog()+"("+dataModel.getTitle()+")"));
                    mContext.startActivity(intent);
                    break;
            }
        }

        private int lastPosition = -1;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            DataModelLocation dataModel = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            ViewHolder viewHolder; // view lookup cache stored in tag

            final View result;

            if (convertView == null) {

                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.item_location, parent, false);
                viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.listview_item_title);
                viewHolder.txtSubTitle = (TextView) convertView.findViewById(R.id.listview_item_subtitle);
                viewHolder.info = (ImageView) convertView.findViewById(R.id.listview_image);
                viewHolder.infoGPS = (ImageView) convertView.findViewById(R.id.imageView_gps);
                result=convertView;

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                result=convertView;
            }

            Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
            result.startAnimation(animation);
            lastPosition = position;

            viewHolder.txtTitle.setText(dataModel.getTitle());
            viewHolder.txtSubTitle.setText(dataModel.getSubTitle());
            viewHolder.info.setImageResource(dataModel.getiType());
            viewHolder.infoGPS.setOnClickListener(this);
            viewHolder.infoGPS.setTag(position);
            // Return the completed view to render on screen
            return convertView;
        }
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                getContext().startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
