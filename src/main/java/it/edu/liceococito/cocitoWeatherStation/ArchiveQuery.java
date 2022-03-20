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

/**
 * A query to the archive
 */
public class ArchiveQuery {
    private final int defaultPageSize = 100;
    public ArrayList<DataType> allowedDataTypes;
    public ArrayList<TimePeriod> timePeriods;
    private int pageSize = 0;

    /**
     * Create the archive query
     */
    public ArchiveQuery() {
        this.allowedDataTypes = new ArrayList<>();
        this.timePeriods = new ArrayList<>();
        this.pageSize = this.defaultPageSize;

    }

    /**
     * Get the allowed types list
     * @return Arraylist of allowed types
     */
    public ArrayList<DataType> getAllowedDataTypes() {
        return allowedDataTypes;
    }

    /**
     * Set the allowed types list
     * @param allowedDataTypes list of allowed types
     */
    public void setAllowedDataTypes(ArrayList<DataType> allowedDataTypes) {
        this.allowedDataTypes = allowedDataTypes;
    }

    /**
     * Gets time Period domain list
     * @return Arraylist of time periods
     */
    public ArrayList<TimePeriod> getTimePeriods() {
        return timePeriods;
    }

    /**
     * Set time Period domain list
     * @param timePeriods Arraylist of time periods
     */
    public void setTimePeriods(ArrayList<TimePeriod> timePeriods) {
        this.timePeriods = timePeriods;
    }

    /**
     * Get pagination page size
     * @return item number in a page
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Set pagination page size
     * @param pageSize items in a page
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     *  Alias of {@code setPageSize(0)}
     * @see it.edu.liceococito.cocitoWeatherStation.ArchiveQuery#setPageSize(int)
     */
    public void disablePagination() {
        this.pageSize = 0;
    }

    /**
     * Set Page size to default page size of {@code 100}
     */
    public void enablePagination() {
        this.pageSize = this.defaultPageSize;
    }
}
