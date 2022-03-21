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

import java.util.HashMap;

/**
 * Generate Statistical data from archive query result
 */
public class StatisticalReporter {
    private final HashMap<DataType, StatisticalReportDataPamphlet> datamap;

    /**
     * Build and compute stats
     *
     * @param aqs query results
     */
    public StatisticalReporter(ArchiveQueryResult aqs) {
        PageList pgl = aqs.getPageList();
        StatisticalReportDataPamphlet temperature = new StatisticalReportDataPamphlet(DataType.TEMPERATURE);
        StatisticalReportDataPamphlet humidity = new StatisticalReportDataPamphlet(DataType.HUMIDITY);
        StatisticalReportDataPamphlet pressure = new StatisticalReportDataPamphlet(DataType.PRESSURE);
        StatisticalReportDataPamphlet pm10 = new StatisticalReportDataPamphlet(DataType.PM10);
        StatisticalReportDataPamphlet pm25 = new StatisticalReportDataPamphlet(DataType.PM25);
        StatisticalReportDataPamphlet smokeAndFlammableVapours = new StatisticalReportDataPamphlet(DataType.SMOKE_AND_FLAMMABLE_VAPOURS);
        this.datamap = new HashMap<>();
        datamap.put(DataType.TEMPERATURE, temperature);
        datamap.put(DataType.HUMIDITY, humidity);
        datamap.put(DataType.PRESSURE, pressure);
        datamap.put(DataType.PM10, pm10);
        datamap.put(DataType.PM25, pm25);
        datamap.put(DataType.SMOKE_AND_FLAMMABLE_VAPOURS, smokeAndFlammableVapours);

        for (Page p : pgl) {
            for (Value v : p) {
                StatisticalReportDataPamphlet srdp = datamap.get(v.getType());
                srdp.setDataType(v.getType());
                srdp.getValues().add(v.getValue());
            }
        }

    }

    /**
     * Get result Pamphlet from datatype
     *
     * @param dataType the datatype
     * @return result pamhplet
     */
    public StatisticalReportDataPamphlet getReportPamphletFromType(DataType dataType) {
        StatisticalReportDataPamphlet s = this.datamap.get(dataType);
        s.compute();
        return s;
    }
}
