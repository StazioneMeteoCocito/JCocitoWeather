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
 * Watches station events and sends them to receiver
 */
public class StationWatcher {
    private Station station;
    private StationEventListener stationEventListener;
    private int secondsBetweenUpdates;
    private boolean watchingEnabled;
    private StationWatcherThread stationWatcherThread;

    /**
     * Build watcher
     *
     * @param station               the station
     * @param secondsBetweenUpdates polling interval
     * @param sev                   the station event listener
     */
    public StationWatcher(Station station, int secondsBetweenUpdates, StationEventListener sev) {
        this.station = station;
        this.secondsBetweenUpdates = secondsBetweenUpdates;
        this.stationEventListener = sev;
        this.watchingEnabled = false;
    }

    /**
     * Get Staation from watcher
     *
     * @return Station
     */
    public Station getStation() {
        return station;
    }

    /**
     * Set the station used
     *
     * @param station Station
     */
    public void setStation(Station station) {
        this.station = station;
    }

    /**
     * Get how many seconds the watcher sleeps between update checks
     *
     * @return the seconds
     */
    public int getSecondsBetweenUpdates() {
        return secondsBetweenUpdates;
    }

    /**
     * Get how many seconds the watcher sleeps between update checks
     *
     * @param secondsBetweenUpdates the seconds
     */
    public void setSecondsBetweenUpdates(int secondsBetweenUpdates) {
        this.secondsBetweenUpdates = secondsBetweenUpdates;
    }

    /**
     * Get the station event listener that this watcher is associated with
     *
     * @return the station event listener
     */
    public StationEventListener getStationEventListener() {
        return stationEventListener;
    }

    /**
     * Set the station event listener that this watcher is associated with
     *
     * @param stationEventListener the station event listener
     */
    public void setStationEventListener(StationEventListener stationEventListener) {
        this.stationEventListener = stationEventListener;
    }

    /**
     * Check weather Watcher is enabled
     *
     * @return boolean
     */
    public boolean isWatchingEnabled() {
        return watchingEnabled;
    }

    /**
     * Start or resume watching
     */
    public void startWatching() {
        if (!this.watchingEnabled) {
            this.stationWatcherThread = new StationWatcherThread(this);
            this.stationWatcherThread.start();
        }
        this.watchingEnabled = true;
    }

    /**
     * Stop watching
     */
    public void stopWatching() {
        this.watchingEnabled = false;
    }

}
