package com.qterminals.geospatial.util;

import org.locationtech.jts.algorithm.Angle;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.util.ArrayList;
import java.util.List;

/*https://gis.stackexchange.com/questions/424169/find-polygon-corners-using-jts*/
public class FindCorners {

  public static void main(String[] args) throws ParseException {
    GeometryFactory gf = new GeometryFactory();
    WKTReader reader = new WKTReader();
    String wkt = "Polygon ((578250.83733634161762893 -5529552.83186665736138821, 578250.83733634161762893 -5529552.83186665736138821, 610067.62374519626609981 -5530111.02110190037637949, 612579.47530379006639123 -5498294.23469304572790861, 651373.62715318310074508 -5496898.76160493772476912, 652646.66333545825909823 -5465547.4130439143627882, 655839.14103512768633664 -5429357.86414052732288837, 709983.49685370502993464 -5428520.58028766233474016, 709115.27472816628869623 -5461139.35142331290990114, 709445.62126647483091801 -5510588.09887696336954832, 706076.17220700345933437 -5551601.30665875878185034, 574064.41807201865594834 -5558299.57748167496174574, 578250.83733634161762893 -5529552.83186665736138821))";
    Polygon poly = (Polygon) reader.read(wkt);
    List<Coordinate> coords = findCorners(poly);
    for (Coordinate c : coords) {
      System.out.println(gf.createPoint(c));
    }
  }

  private static List<Coordinate> findCorners(Polygon poly) {
   
    List<Coordinate> ret = new ArrayList<>();
    LinearRing exteriorRing = poly.getExteriorRing();
    int n = exteriorRing.getNumPoints();
    Coordinate last = exteriorRing.getCoordinateN(n-1);
    double ninety = Math.PI/2.0;
    double twoseventy = Math.PI + ninety;
    for (int i = 1; i < n - 1; i++) {
      Coordinate current = exteriorRing.getCoordinateN(i);
      Coordinate next = exteriorRing.getCoordinateN(i+1);
     
      double angle = Angle.interiorAngle(last, current, next);
      if (Math.abs(angle - ninety) < 0.2 || Math.abs(angle - twoseventy) < 0.2) {
        ret.add(current);
      }
      last = current;
    }

    double angle = Angle.interiorAngle(last, exteriorRing.getCoordinateN(0), exteriorRing.getCoordinateN(1));
    if (Math.abs(angle - ninety) < 0.2 || Math.abs(angle - twoseventy) < 0.2) {
      ret.add(exteriorRing.getCoordinateN(0));
    }
    return ret;
  }

}