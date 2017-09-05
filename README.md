# MyGeoApp is a getting started application-
 
## Problem statement
 Considering the Geo-coordinates of the rooms of you house are configured in the app,  when you move from one room to other the message (say a welcome message)on the screen should change.  
      Say moving from Drawing room to kitchen  , Message should change from "Welcome to Drawing room" to "Welcome to kitchen".
 
 ## Possible Implementaion approach-
 At top level there are 2 approachs to solve the problem
 1. Use location of the handset.
 2. Use place a bluetooth device in each of the room, the app on the mobile shows message based on the device in range.
 
 ## Solution
 MyGeoApp uses first approach i.e use the Geocoordinates to solve the problem.
 ### Challenge with this solution
  Using [Geofence](https://developer.android.com/training/location/geofencing.html) is an obvious choise  for this usecase, but the limitation with GeoFence is that it **supports only Circular region**.
  So we use Ray-Casting Algorith to check if the point is with in a polygon.
  
 
 
*  MainActivity.java is the entry point of the application.
*  It uses ***android native Location service*** to handle the locationChange events.
*  Each room represents a polygon, coordinates of each room is pre-configured/hard coded as of now in the app.
*  On locationChange event the Ray-Casting algorithm is executed on each room(polygon) and message is displayed accordingly.



 
 
