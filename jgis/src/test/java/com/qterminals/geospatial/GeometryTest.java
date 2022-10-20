package com.qterminals.geospatial;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qterminals.geospatial.constants.DistanceUnit;
import com.qterminals.geospatial.generic.GenericTest;
import com.qterminals.geospatial.util.GeometryUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeometryTest extends GenericTest {
    @Autowired
    private GeometryUtil geometryUtil;
    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest
    @ValueSource(strings = {"/linestring-map-1.wkt"})
    public void lineStringMap1(String fileName){
        try {
            Geometry geometry = this.geometryUtil.readWKTFile(fileName);
            assertEquals("LineString", geometry.getGeometryType());
            assertTrue(geometry instanceof LineString);

            LineString lineString = (LineString) geometry;
            Coordinate lastCoordinate = null;

            for(Coordinate coordinate : lineString.getCoordinates()) {
                if(lastCoordinate != null){
                    System.out.println(this.geometryUtil.calculateDistance(lastCoordinate, coordinate, DistanceUnit.KILOMETERS));
                    System.out.println(this.geometryUtil.calculateDistance(lastCoordinate, coordinate, DistanceUnit.CENTIMETERS));
                    System.out.println(this.geometryUtil.calculateDistance(lastCoordinate, coordinate, DistanceUnit.INCHES));
                    System.out.println(this.geometryUtil.calculateDistance(lastCoordinate, coordinate, DistanceUnit.MILIMETERES));
                    System.out.println(this.geometryUtil.calculateDistance(lastCoordinate, coordinate, DistanceUnit.METERS));
                    System.out.println(this.geometryUtil.calculateDistance(lastCoordinate, coordinate, DistanceUnit.FEET));
                    System.out.println(this.geometryUtil.calculateDistance(lastCoordinate, coordinate, DistanceUnit.YARDS));
                    System.out.println(this.geometryUtil.calculateDistance(lastCoordinate, coordinate, DistanceUnit.MILES));
                }

                lastCoordinate = coordinate;
            }

            this.objectMapper.writeValueAsString(lineString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"/polygon-map-1.wkt"})
    public void polygonMap1(String fileName){
        try {
            Geometry geometry = this.geometryUtil.readWKTFile(fileName);
            assertEquals("Polygon", geometry.getGeometryType());
            assertTrue(geometry instanceof Polygon);

            Polygon polygon = (Polygon) geometry;
            Coordinate lastCoordinate = null;
            for(Coordinate coordinate : polygon.getCoordinates()) {
                if(lastCoordinate != null){
                    System.out.println(this.geometryUtil.calculateDistance(lastCoordinate, coordinate, DistanceUnit.KILOMETERS));
                    System.out.println(this.geometryUtil.calculateDistance(lastCoordinate, coordinate, DistanceUnit.CENTIMETERS));
                    System.out.println(this.geometryUtil.calculateDistance(lastCoordinate, coordinate, DistanceUnit.INCHES));
                    System.out.println(this.geometryUtil.calculateDistance(lastCoordinate, coordinate, DistanceUnit.MILIMETERES));
                    System.out.println(this.geometryUtil.calculateDistance(lastCoordinate, coordinate, DistanceUnit.METERS));
                    System.out.println(this.geometryUtil.calculateDistance(lastCoordinate, coordinate, DistanceUnit.FEET));
                    System.out.println(this.geometryUtil.calculateDistance(lastCoordinate, coordinate, DistanceUnit.YARDS));
                    System.out.println(this.geometryUtil.calculateDistance(lastCoordinate, coordinate, DistanceUnit.MILES));
                    System.out.println();
                }

                lastCoordinate = coordinate;
            }

            this.objectMapper.writeValueAsString(polygon);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
