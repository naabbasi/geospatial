package com.qterminals.geospatial;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qterminals.geospatial.generic.GenericTest;
import com.qterminals.geospatial.util.GeometryUtil;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.junit.jupiter.api.Test;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.cs.CoordinateSystem;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class GeoToolsReadShapeFile extends GenericTest {
    @Autowired
    private GeometryUtil geometryUtil;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void calculateDistance(){
        URL url = this.getClass().getResource("/qt-shape1/layers/POLYGON.shp");
        FileDataStore store = null;
        try {
            store = FileDataStoreFinder.getDataStore(new File(url.toURI()));
            SimpleFeatureSource featureSource = store.getFeatureSource();
            SimpleFeatureType simpleFeatureType = featureSource.getSchema();

            CoordinateReferenceSystem coordinateReferenceSystem = simpleFeatureType.getCoordinateReferenceSystem();
            CoordinateSystem coordinateSystem = coordinateReferenceSystem.getCoordinateSystem();
            System.out.println(coordinateSystem);
            coordinateReferenceSystem.getAlias();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
