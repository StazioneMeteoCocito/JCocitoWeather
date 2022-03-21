# JCocitoWeather
> it.edu.liceococito.cocitoWeatherStation

A Java library for the weather station, allows for easy data querying, and updating.

[Javadocs](https://stazionemeteococito.github.io/JCocitoWeather/it/edu/liceococito/cocitoWeatherStation/package-summary.html)

Example:

```java
public class Main implements StationEventListener {
    public static void main(String[] args) throws NotADirectoryException, IOException, GitAPIException, ParseException {
        Main m = new Main();
    }
    public Main() throws GitAPIException, NotADirectoryException, IOException, ParseException {
        Station station = new Station();
        station.udpate();
        ArchiveQuery aq = new ArchiveQuery();
        TimePeriod tp = new TimePeriod();
        aq.allowedDataTypes.add(DataType.TEMPERATURE);
        aq.timePeriods.add(tp);
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
```

See it in [Main.java](https://github.com/StazioneMeteoCocito/JCocitoWeather/blob/main/src/test/java/Main.java)
