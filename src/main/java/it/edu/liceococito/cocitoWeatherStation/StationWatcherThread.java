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

import org.eclipse.jgit.api.errors.GitAPIException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.Instant;

/**
 * Thread to check station updates and dispach events
 */
class StationWatcherThread extends Thread {
    private final StationWatcher stationWatcher;

    /**
     * Build the thread
     *
     * @param stationWatcher Watcher
     */
    StationWatcherThread(StationWatcher stationWatcher) {
        this.stationWatcher = stationWatcher;
    }

    /**
     * Run the thread
     */
    public void run() {
        try {
            while (this.stationWatcher.isWatchingEnabled()) {// run until asked to stop
                Instant prev = this.stationWatcher.getStation().getLastMeasurements().getInstant();
                try {
                    Thread.sleep(this.stationWatcher.getSecondsBetweenUpdates() * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.stationWatcher.getStation().udpate();
                LatestMeasurements lms = this.stationWatcher.getStation().getLastMeasurements();
                if (lms.getInstant().compareTo(prev) > 0) {
                    StationEventListener sel = this.stationWatcher.getStationEventListener();
                    sel.receiveStationLatestHardwareReport(this.stationWatcher.getStation().getHardwareReport());
                    sel.receiveStationLatestMeasurements(this.stationWatcher.getStation().getLastMeasurements());
                }
            }
        } catch (IOException | ParseException | GitAPIException ignored) {

        }
    }
}
