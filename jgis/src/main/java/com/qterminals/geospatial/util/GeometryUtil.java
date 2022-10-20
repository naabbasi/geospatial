package com.qterminals.geospatial.util;

import com.qterminals.geospatial.constants.DistanceUnit;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.util.GeometricShapeFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class GeometryUtil {

    private Geometry wktToGeometry(String wellKnownText) {
        try {
            return new WKTReader().read(wellKnownText);
        } catch (org.locationtech.jts.io.ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public Geometry readWKTFile(String filePath){
        try {
            URI uri = GeometryUtil.class.getResource(filePath).toURI();
            Path wktPath = Paths.get(uri);
            byte[] wktFileBytes = Files.readAllBytes(wktPath);
            return wktToGeometry(new String(wktFileBytes, StandardCharsets.UTF_8));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Double calculateDistance(Coordinate lastCoordinate, Coordinate currentCoordinate, DistanceUnit distanceUnit){
        Double lastX = limitPrecision(lastCoordinate.getX());
        Double lastY = limitPrecision(lastCoordinate.getY());

        Double currentX = limitPrecision(currentCoordinate.getX());
        Double currentY = limitPrecision(currentCoordinate.getY());

        Double distanceInKMs = convertToKMs(lastX, lastY, currentX, currentY);

        if (distanceUnit.equals(DistanceUnit.METERS)) {
            distanceInKMs = distanceInKMs * 1000;
        } else if (distanceUnit.equals(DistanceUnit.CENTIMETERS)) {
            distanceInKMs = distanceInKMs * 100000;
        } else if (distanceUnit.equals(DistanceUnit.INCHES)) {
            distanceInKMs = distanceInKMs * 39370;
        } else if (distanceUnit.equals(DistanceUnit.MILIMETERES)) {
            distanceInKMs = distanceInKMs * 1000000;
        } else if(distanceUnit.equals(DistanceUnit.KILOMETERS)){
            //distanceInKMs = distanceInKMs;
        } else if(distanceUnit.equals(DistanceUnit.FEET)){
            distanceInKMs = distanceInKMs * 3281;
        } else if(distanceUnit.equals(DistanceUnit.YARDS)){
            distanceInKMs = distanceInKMs * 1094;
        } else if(distanceUnit.equals(DistanceUnit.MILES)){
            distanceInKMs = distanceInKMs / 1.609;
        }

        BigDecimal distance = new BigDecimal(String.valueOf(distanceInKMs)).setScale(6, RoundingMode.DOWN);
        return distance.doubleValue();
    }

    private Double convertToKMs(Double lat1, Double lon1, Double lat2, Double lon2){
        var R = 6378; // Radius of the earth in km
        var dLat = deg2rad(lat2-lat1);  // deg2rad below
        var dLon = deg2rad(lon2-lon1);
        var a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon/2) * Math.sin(dLon/2);
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        var d = R * c; // DistanceUnit in km

        BigDecimal distance = new BigDecimal(String.valueOf(d)).setScale(6, RoundingMode.DOWN);
        return distance.doubleValue();
    }

    private Double deg2rad(Double deg) {
        return deg * (Math.PI / 180);
    }

    public Double limitPrecision(Double value) {
        BigDecimal distance = new BigDecimal(String.valueOf(value)).setScale(6, RoundingMode.DOWN);
        return distance.doubleValue();
    }

    public Double calculateSquareMeter(Double lengthInMeter, Double widthInMeters) {
        return limitPrecision(lengthInMeter) * limitPrecision(widthInMeters);
    }

    public Long calculateTotalContainersInSquareMeters(Double totalSquareMeter, Double containerSquareMeter) {
        long containerCount = 1;

        while (totalSquareMeter >= (containerSquareMeter * containerCount)) {
            System.out.printf("Container: %d, Remaining SQ: %.3f\n", containerCount, totalSquareMeter - (containerSquareMeter * containerCount));
            if ((totalSquareMeter - (containerSquareMeter * containerCount)) <= 0.0) {
                break;
            }
            containerCount = containerCount + 1;
        }

        return containerCount;
    }

    public Geometry createCircle(double x, double y, double radius) {
        GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
        shapeFactory.setNumPoints(32);
        shapeFactory.setCentre(new Coordinate(x, y));
        shapeFactory.setSize(radius * 2);
        return shapeFactory.createRectangle();
    }
}
