package com.qterminals.geospatial;

import com.qterminals.geospatial.generic.GenericTest;
import com.qterminals.geospatial.util.GeometryUtil;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContainerSizeTest extends GenericTest {
    @Autowired
    private GeometryUtil geometryUtil;

    @Order(1)
    @ParameterizedTest
    @CsvSource({"4.0, 3.0, 12.0", "4.0, 4.0, 16.0", "10, 10, 100.0", "25, 25, 625", "50, 50, 2500"})
    public void calculateSquareMeters(String length, String width, String expected) {
        double lengthInMeter = Double.parseDouble(length);
        double widthInMeter = Double.parseDouble(width);

        double totalSquareMeters = geometryUtil.calculateSquareMeter(lengthInMeter, widthInMeter);
        assertEquals(Double.parseDouble(expected), totalSquareMeters);
    }

    @Order(2)
    @Test
    public void calculateDummyContainerSquareMeters() {
        double containerLengthInMeter = 1;
        double containerWidthInMeter = 1;

        double containerSquareMeters = geometryUtil.calculateSquareMeter(containerLengthInMeter, containerWidthInMeter);
        assertEquals(1.0, containerSquareMeters);

        double totalSquareMeters = 12.0;
        long containerCount = geometryUtil.calculateTotalContainersInSquareMeters(totalSquareMeters, containerSquareMeters);
        assertEquals(12, containerCount);
    }

    @Order(3)
    @ParameterizedTest
    @CsvSource({"4.0, 4.0, 16.0"})
    public void calculateContainersInSquareMeters(String length, String width, String expected) {
        double containerLengthInMeter = 2;
        double containerWidthInMeter = 2;

        double containerSquareMeters = geometryUtil.calculateSquareMeter(containerLengthInMeter, containerWidthInMeter);
        assertEquals(4.0, containerSquareMeters);

        double lengthInMeter = Double.parseDouble(length);
        double widthInMeter = Double.parseDouble(width);

        double totalSquareMeters = geometryUtil.calculateSquareMeter(lengthInMeter, widthInMeter);
        assertEquals(Double.parseDouble(expected), totalSquareMeters);

        long containerCount = geometryUtil.calculateTotalContainersInSquareMeters(totalSquareMeters, containerSquareMeters);
        assertEquals(4, containerCount);
    }
}
