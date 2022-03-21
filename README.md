# JCocitoWeather
> it.edu.liceococito.cocitoWeatherStation

A Java library for the weather station, allows for easy data querying, and updating.

[Javadocs](https://stazionemeteococito.github.io/JCocitoWeather/it/edu/liceococito/cocitoWeatherStation/package-summary.html)

Example:

```java
Station station = new Station();
station.udpate();
ArchiveQuery aq = new ArchiveQuery();
TimePeriod tp = new TimePeriod(RecentTimePeriod.TODAY);
aq.allowedDataTypes.add(DataType.TEMPERATURE);
aq.timePeriods.add(tp);
aq.setPageSize(10);
ArchiveQueryResult aqs = station.query(aq);
PageList pgl = aqs.getPageList();
for(int i = 0;i<pgl.size(); i++){
     Page p = pgl.get(i);
     for(int j = 0; j<p.size();j++){
          Value v = p.get(j);
          System.out.println(v.getValue()+" at "+v.getCreated());
     }
}
System.out.println(station.getHardwareReport());
System.out.println("Latest temp: "+station.getLastMeasurements().getTemperature().getValue());
```

See it in [Main.java](https://github.com/StazioneMeteoCocito/JCocitoWeather/blob/main/src/test/java/Main.java)
