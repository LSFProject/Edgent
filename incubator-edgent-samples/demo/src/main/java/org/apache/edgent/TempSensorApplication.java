package org.apache.edgent;

import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;

import java.util.concurrent.TimeUnit;

public class TempSensorApplication {
    public static void main(String[] args) throws Exception {
        TempSensor sensor = new TempSensor();
        DirectProvider dp = new DirectProvider();
        Topology topology = dp.newTopology();

        TStream<Number> tempReadings = topology.poll(sensor, 1, TimeUnit.MILLISECONDS);
        TStream<Number> filteredReadings = tempReadings.filter(reading -> reading.doubleValue() < 50 || reading.doubleValue() > 80);
        filteredReadings.print();

        dp.submit(topology);
    }
}