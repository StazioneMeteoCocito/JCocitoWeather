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



import it.edu.liceococito.cocitoWeatherStation.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Example usage of API
 */
public class Main implements StationEventListener {
    public static void main(String[] args) throws NotADirectoryException, IOException, GitAPIException, ParseException {
        Main m = new Main();
    }
    public Main() throws GitAPIException, NotADirectoryException, IOException, ParseException {
        Station station = new Station();
        station.udpate();
        ArchiveQuery aq = new ArchiveQuery();
        TimePeriod tp = new TimePeriod();
        aq.getAllowedDataTypes().add(DataType.TEMPERATURE);
        aq.getTimePeriods().add(tp);
        aq.setPageSize(10);
        ArchiveQueryResult aqs = station.query(aq);
        PageList pgl = aqs.getPageList();
        for (Page p : pgl) {
            for (Value v : p) {
                System.out.println(v.getValue() + " at " + v.getCreated());
            }
        }
        System.out.println(station.getHardwareReport());
        System.out.println("Latest temp: " + station.getLastMeasurements().getTemperature().getValue());


        StatisticalReporter str = new StatisticalReporter(aqs);
        StatisticalReportDataPamphlet pa = str.getReportPamphletFromType(DataType.TEMPERATURE);
        double max = pa.getMax();
        double min = pa.getMin();

        System.out.println("Max : "+max+" °C, Min: "+min+" °C");
        StationWatcher stationWatcher = station.getStationWatcher(this);
        stationWatcher.startWatching();

        System.out.println("Started station watcher");
    }
    @Override
    public void receiveStationLatestMeasurements(LatestMeasurements latestMeasurements) {
        System.out.println("New Humidity  "+latestMeasurements.getHumidity().getValue());
    }

    @Override
    public void receiveStationLatestHardwareReport(String latestHardwareReport) {
        System.out.println("New hardware report is "+latestHardwareReport.length()+" char long");
    }
}
