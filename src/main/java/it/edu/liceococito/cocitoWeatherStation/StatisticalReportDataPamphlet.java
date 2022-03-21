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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Data holder for statistical data results for a given data type
 */
public class StatisticalReportDataPamphlet {
    public ArrayList<Double> values;
    private double mean;
    private double min;
    private double max;
    private int mode;
    private double stdev;
    private int numberElements;
    private DataType dataType;
    /**
     * Build Pamphlet
     *
     * @param dataType data type
     */
    StatisticalReportDataPamphlet(DataType dataType) {
        this.dataType = dataType;
        this.values = new ArrayList<>();
    }

    /**
     * Get mean value
     *
     * @return mean
     */
    public double getMean() {
        return mean;
    }

    /**
     * Get min value
     *
     * @return min
     */
    public double getMin() {
        return min;
    }

    /**
     * Get max value
     *
     * @return max
     */
    public double getMax() {
        return max;
    }

    /**
     * Get integer mode
     *
     * @return mode
     */

    public int getMode() {
        return mode;
    }

    /**
     * Get elements count
     *
     * @return count
     */
    public int getNumberElements() {
        return numberElements;
    }

    /**
     * Get data type
     *
     * @return data type
     */
    public DataType getDataType() {
        return dataType;
    }

    /**
     * Set data type
     *
     * @param dataType data type
     */
    void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    /**
     * Get standard deviation
     *
     * @return standard deviation
     */
    public double getStdev() {
        return stdev;
    }

    /**
     * Get value list
     *
     * @return value lsit
     */
    ArrayList<Double> getValues() {
        return values;
    }

    /**
     * Set value list
     *
     * @param values value list
     */
    void setValues(ArrayList<Double> values) {
        this.values = values;
    }

    /**
     * Compute data
     */
    void compute() {

        /* 21 mar 2022 23:04:50
           Listening to Maniac by Micheal Sembello
           0:30
        */
        this.numberElements = this.values.size();
        int sum = 0;
        int i;
        double max = 0, min = 0;
        HashMap<Integer, Integer> integerModeOccurrence = new HashMap<>();
        for (i = 0; i < this.numberElements; i++) {
            double val = this.values.get(i);
            sum += val;
            if (i == 0 || max < val) {
                max = val;
            }
            if (i == 0 || min > val) {
                min = val;
            }
            if (integerModeOccurrence.containsKey((int) val)) {
                integerModeOccurrence.put((int) val, integerModeOccurrence.get((int) val) + 1);
            } else {
                integerModeOccurrence.put((int) val, 1);
            }
        }
        this.mean = sum / (double) i;
        this.max = max;
        this.min = min;
        int biggestValue = 0, biggestKey = 0;
        Iterator<Map.Entry<Integer, Integer>> it = integerModeOccurrence.entrySet().iterator();
        i = 0;
        while (it.hasNext()) {
            Map.Entry<Integer, Integer> pair = it.next();
            if (i == 0 || biggestValue < pair.getValue()) {
                biggestValue = pair.getValue();
                biggestKey = pair.getKey();
            }
            i++;
        }
        this.mode = biggestKey;
        this.stdev = this.sd();
        this.values.clear();
    }

    /**
     * Calculate standard deviation
     *
     * @return standard deviation
     */
    private double sd() {

        /*
         * Standard deviation:
         * Step 1: Find the mean.
         * Step 2: For each data point, find the square of its distance to the mean.
         * Step 3: Sum the values from Step 2.
         * Step 4: Divide by the number of data points.
         * Step 5: Take the square root.
         */
        // Step 1:
        double mean = this.mean;
        double temp = 0;

        for (double val : this.values) {
            // Step 2:
            double squrDiffToMean = Math.pow(val - mean, 2);

            // Step 3:
            temp += squrDiffToMean;
        }

        // Step 4:
        double meanOfDiffs = temp / (double) (this.values.size());

        // Step 5:
        return Math.sqrt(meanOfDiffs);
    }

}
