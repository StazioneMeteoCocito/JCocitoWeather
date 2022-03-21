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
 * A value data point
 */
public class Value {
    private final Instant created;
    private final File file;
    private final int fileLine;
    private final double value;
    private final DataType type;

    /**
     * A value data point
     *
     * @param created  Data point generation instant
     * @param file     File containing the data point
     * @param fileLine Line of the source file containing the data point
     * @param value    Data point value
     * @param type     Type of data point
     */
    public Value(Instant created, File file, int fileLine, double value, DataType type) {
        this.created = created;
        this.file = file;
        this.fileLine = fileLine;
        this.value = value;
        this.type = type;
    }

    /**
     * Get the file containing the data point
     *
     * @return File
     */
    public File getFile() {
        return file;
    }

    /**
     * Get the line of the source file containing the data point
     *
     * @return line
     */
    public int getFileLine() {
        return fileLine;
    }

    /**
     * Get the data point generation instant
     *
     * @return generation isntant
     */
    public Instant getCreated() {
        return created;
    }

    /**
     * Get the data point value
     *
     * @return value
     */
    public double getValue() {
        return value;
    }

    /**
     * Get the data point type
     *
     * @return data type
     */
    public DataType getType() {
        return type;
    }

}
