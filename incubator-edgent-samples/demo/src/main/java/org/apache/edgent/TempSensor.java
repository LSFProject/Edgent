package org.apache.edgent;

import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import org.apache.edgent.function.Supplier;

import java.util.Random;

public class TempSensor  implements Supplier<Number> {
    double currentTemp = 65.0;
    Random rand;

    TempSensor(){
        rand = new Random();
    }

    @Override
    public Number get() {
        // Change the current temperature some random amount
        double newTemp = rand.nextGaussian() + currentTemp;
        currentTemp = newTemp;
        Number number=0.0;
        try {
            number = Modbus4jReadUtils.readHoldingRegister(1, 0, DataType.FOUR_BYTE_FLOAT);
        } catch (ModbusTransportException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (ModbusInitException e) {
            e.printStackTrace();
        }
        return number;
    }
}