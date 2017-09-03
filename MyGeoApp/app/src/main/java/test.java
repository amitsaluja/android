import com.google.maps.android.geometry.Point;

import java.util.ArrayList;

/**
 * Created by amitsaluja on 02/09/17.
 */

public class test {

     // public static void main(String[] args)
    private boolean isPointInPolygon(Point tap, ArrayList<Point> vertices) {
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


}
