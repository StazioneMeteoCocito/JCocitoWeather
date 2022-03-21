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

/**
 * Enum of available data types
 */
public enum DataType {
    TEMPERATURE("Temperatura", "Temperature", "T", "°C", "temperature"),
    HUMIDITY("Umidità", "Humidity", "H", "%", "humidity"),
    PRESSURE("Pressione", "Pressure", "P", "hPa", "pressure"),
    PM10("PM10", "PM10", "PM10", "µg/m³", "pm10"),
    PM25("PM2,5", "PM2,5", "PM25", "µg/m³", "pm25"),
    SMOKE_AND_FLAMMABLE_VAPOURS("Fumo e vapori infiammabili", "Smoke and flammable vapours", "S", "µg/m³", "smoke");

    private final String italianName;
    private final String englishName;
    private final String symbol;
    private final String unitOfMeasurement;
    private final String csvName;

    /**
     * Enum datatype constructor
     *
     * @param italianName       name in italian
     * @param englishName       name in english
     * @param symbol            symbol
     * @param unitOfMeasurement unit
     * @param csvName           csv file name without extension
     */
    DataType(String italianName, String englishName, String symbol, String unitOfMeasurement, String csvName) {
        this.italianName = italianName;
        this.englishName = englishName;
        this.symbol = symbol;
        this.unitOfMeasurement = unitOfMeasurement;
        this.csvName = csvName;
    }

    /**
     * Get name italian name of data type
     *
     * @return italian name
     */
    public String getItalianName() {
        return italianName;
    }

    /**
     * Get name english name of data type
     *
     * @return english name
     */
    public String getEnglishName() {
        return englishName;
    }

    /**
     * Get symbol of data type
     *
     * @return symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Get unit of measurement of data type
     *
     * @return unit of measurement
     */
    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    /**
     * Get csv data type filename without extension
     *
     * @return filename without extension
     */
    public String getCsvName() {
        return csvName;
    }
}
