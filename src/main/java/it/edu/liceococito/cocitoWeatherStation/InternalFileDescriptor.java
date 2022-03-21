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

/**
 * File desciptor with file and datatype
 */
class InternalFileDescriptor {
    private File f;
    private DataType dt;

    /**
     * Build file descriptor
     *
     * @param f  file
     * @param dt data type
     */
    public InternalFileDescriptor(File f, DataType dt) {
        this.f = f;
        this.dt = dt;
    }

    /**
     * Get file
     *
     * @return file
     */
    public File getF() {
        return f;
    }

    /**
     * Set file
     *
     * @param f file
     */
    public void setF(File f) {
        this.f = f;
    }

    /**
     * Get data type
     *
     * @return data Type
     */
    public DataType getDt() {
        return dt;
    }

    /**
     * SEt data type
     *
     * @param dt data type
     */
    public void setDt(DataType dt) {
        this.dt = dt;
    }

}
