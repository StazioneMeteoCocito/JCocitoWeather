/*
 * MIT License
 *
 * Copyright (c) 2022 Stazione Meteo del Liceo Cocito
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package it.edu.liceococito.cocitoWeatherStation;

import java.io.File;
import java.time.Instant;

/**
 * Stores latest measurements
 */
public class LatestMeasurements {
    /**
     * Get temperature value
     * @return temperature
     */
    public Value getTemperature() {
        return temperature;
    }
    /**
     * Get humidity value
     * @return humidity
     */
    public Value getHumidity() {
        return humidity;
    }
    /**
     * Get pressure value
     * @return pressure
     */
    public Value getPressure() {
        return pressure;
    }
    /**
     * Get PM10 value
     * @return PM10
     */
    public Value getPm10() {
        return pm10;
    }
    /**
     * Get PM25 value
     * @return pm25
     */
    public Value getPm25() {
        return pm25;
    }
    /**
     * Get smoke and flammable vapours value
     * @return smoke and flammable vapours
     */
    public Value getSmokeAndFlammableGases() {
        return smokeAndFlammableGases;
    }

    private final Value temperature;
    private final Value humidity;
    private final Value pressure;
    private final Value pm10;
    private final Value pm25;
    private final File jsonFile;

    /**
     * Build latest measurements holder
     */
     LatestMeasurements(double temperature, double humidity, double pressure, double pm10, Double pm25, double smokeAndFlammableGases, String filePath, Instant latest) {
        this.jsonFile = new File(filePath);
        this.temperature = new Value(latest,jsonFile,1,temperature,DataType.TEMPERATURE);
        this.humidity = new Value(latest,jsonFile,2,humidity,DataType.HUMIDITY);
        this.pressure = new Value(latest,jsonFile,3,pressure,DataType.PRESSURE);
        this.pm10 = new Value(latest,jsonFile,4,pm10,DataType.PM10);
        this.pm25 = new Value(latest,jsonFile,5,pm25,DataType.PM25);
        this.smokeAndFlammableGases = new Value(latest,jsonFile,6,smokeAndFlammableGases,DataType.SMOKE_AND_FLAMMABLE_VAPOURS);
    }

    private final Value smokeAndFlammableGases;
}
