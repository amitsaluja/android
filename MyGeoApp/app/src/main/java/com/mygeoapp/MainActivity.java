package com.mygeoapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.support.v4.content.ContextCompat;

import com.google.maps.android.PolyUtil;
import com.google.maps.android.geometry.Point;
import java.util.*;

public class MainActivity extends AppCompatActivity {


    TextView textLat;
    TextView textLong;
    TextView textMessage;



    private boolean isPointInPolygon(Point tap, List<Point> vertices) {
        int intersectCount = 0;
        for(int j=0; j<vertices.size()-1; j++) {
            if( rayCastIntersect(tap, vertices.get(j), vertices.get(j+1)) ) {
                intersectCount++;
            }
        }

        return (intersectCount%2 == 1); // odd = inside, even = outside;

    }

    private boolean rayCastIntersect(Point tap, Point vertA, Point vertB) {

        double aY = vertA.y;
        double bY = vertB.y;
        double aX = vertA.x;
        double bX = vertB.x;
        double pY = tap.y;
        double pX = tap.x;

        if ( (aY>pY && bY>pY) || (aY<pY && bY<pY) || (aX<pX && bX<pX) ) {
            return false; // a and b can't both be above or below pt.y, and a or b must be east of pt.x
        }

        double m = (aY-bY) / (aX-bX);               // Rise over run
        double bee = (-aX) * m + aY;                // y = mx + b
        double x = (pY - bee) / m;                  // algebra is neat!

        return x > pX;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textLat = (TextView) findViewById(R.id.textLat);
       textLong = (TextView) findViewById(R.id.textLong);
        textMessage = (TextView) findViewById(R.id.textMessage);

        textLat.setText("123.456");//default for emulator
        textLong.setText("789.321");//default for emulator
        textMessage.setText(" Welcome !!!");//default for emulator



        //kidsroom
       final ArrayList<Point> kidsRoom=new ArrayList<Point>();

        Point p1=new Point(77.61349774d,12.859560377d);
        Point p2=new Point(77.61351172d,12.859561744d);
        Point p3=new Point(77.61348432d,12.859596038d);
        Point p4=new Point(77.61350131d,12.859585754d);
        kidsRoom.add(p1);
        kidsRoom.add(p2);
        kidsRoom.add(p3);
        kidsRoom.add(p4);

        //guest room
         ArrayList<Point> gRoom=new ArrayList<Point>();

        //TODO- populate guest bed room coordinates
        //Hall
        ArrayList<Point> hall=new ArrayList<Point>();
        // TODO- populate hall room coordinates
       //masterBedroom
       //TODO- populate MBR room coordinates
        ArrayList<Point> mbr=new ArrayList<Point>();

        //masterKitchen
        // TODO populate kitchen  coordinates
        ArrayList<Point> kitchen=new ArrayList<Point>();



        final Map<String,List<Point>> roomsList=new HashMap<String,List<Point>>();
        roomsList.put("Kids Room",kidsRoom);
        roomsList.put("Guest Room",gRoom);
        roomsList.put("Hall ",hall);
        roomsList.put("Master Bed Room ",mbr);
        roomsList.put("Kitchen ",kitchen);


        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener ll = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                System.out.print("on location changed called");
                if (location != null) {
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();

                    textLat.setText(Double.toString(latitude));
                    textLong.setText(Double.toString(longitude));

                    Point p= new Point(longitude,latitude);
                    System.out.println("latitude="+latitude+" longitude="+longitude);

                    Set keySet=roomsList.keySet();

                    Iterator<String> itr=keySet.iterator();
                    Boolean isInHouse=false;
                    while (itr.hasNext()){
                       String room= itr.next();
                        Boolean isInThisRoom= isPointInPolygon(p,roomsList.get(room));
                        if(isInThisRoom){
                            textMessage.setText("Welcome to "+room);
                            isInHouse=true;
                            break;
                       }
                    }

                    if(!isInHouse){

                        textMessage.setText("Seems your are not in house, Have a good day!!!!");

                    }



                }
            }



            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // Check Permissions Now

               //textLat.setText("checking permission now .....");

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

            } else {
                // permission has been granted, continue as usual
               // textLat.setText("permission granted .....");
            }


            if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

                ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                        1);
            }
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
       }catch (Exception e){
           System.out.print(e);
       }

    }
}
