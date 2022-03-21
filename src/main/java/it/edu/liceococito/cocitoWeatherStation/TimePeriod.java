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

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

/**
 * Time period with start and end
 */
public class TimePeriod {
    private Instant start, end;

    /**
     * Time period given two instants
     *
     * @param start start instant
     * @param end   end instant
     */
    public TimePeriod(Instant start, Instant end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Time period given Recent Time period
     *
     * @param rtp recent time period
     */
    public TimePeriod(RecentTimePeriod rtp) {
        if (rtp.equals(RecentTimePeriod.TODAY)) {
            this.start = Instant.now().truncatedTo(ChronoUnit.DAYS);
            this.end = Instant.now().truncatedTo(ChronoUnit.DAYS).plus(1, ChronoUnit.DAYS);
        } else if (rtp.equals(RecentTimePeriod.YESTERDAY)) {
            this.start = Instant.now().truncatedTo(ChronoUnit.DAYS).minus(1, ChronoUnit.DAYS);
            this.end = Instant.now().truncatedTo(ChronoUnit.DAYS);
        } else if (rtp.equals(RecentTimePeriod.THIS_WEEK)) {
            Instant start = Instant.now().truncatedTo(ChronoUnit.DAYS);
            while (this.getDayOfWeek(start) != DayOfWeek.MONDAY) {
                start = start.minus(1, ChronoUnit.DAYS);
            }
            this.start = start;
            this.end = start.plus(1, ChronoUnit.DAYS);
        } else if (rtp.equals(RecentTimePeriod.LAST_WEEK)) {
            Instant start = Instant.now().truncatedTo(ChronoUnit.DAYS);
            while (this.getDayOfWeek(start) != DayOfWeek.MONDAY) {
                start = start.minus(1, ChronoUnit.DAYS);
            }
            this.start = start.minus(7, ChronoUnit.DAYS);
            this.end = start.plus(1, ChronoUnit.DAYS);
        } else if (rtp.equals(RecentTimePeriod.THIS_MONTH)) {
            this.start = Instant.now().truncatedTo(ChronoUnit.MONTHS);
            this.end = Instant.from(YearMonth.from(this.start.atZone(ZoneId.of("UTC"))).atEndOfMonth());
        } else if (rtp.equals(RecentTimePeriod.LAST_MONTH)) {
            this.start = Instant.now().truncatedTo(ChronoUnit.MONTHS).minus(1, ChronoUnit.MONTHS);
            this.end = Instant.from(YearMonth.from(this.start.atZone(ZoneId.of("UTC"))).atEndOfMonth());
        } else if (rtp.equals(RecentTimePeriod.THIS_YEAR)) {
            this.start = Instant.now().truncatedTo(ChronoUnit.YEARS);
            this.end = Instant.now().truncatedTo(ChronoUnit.YEARS).plus(1, ChronoUnit.YEARS);
        } else if (rtp.equals(RecentTimePeriod.LAST_YEAR)) {
            this.start = Instant.now().truncatedTo(ChronoUnit.YEARS).minus(1, ChronoUnit.YEARS);
            this.end = Instant.now().truncatedTo(ChronoUnit.YEARS);
        }
    }

    /**
     * Get today's time period
     */
    public TimePeriod() {
        this(RecentTimePeriod.TODAY);
    }

    /**
     * Get start of time peridod
     *
     * @return start instant
     */
    public Instant getStart() {
        return start;
    }

    /**
     * Set start of time period
     *
     * @param start start instant
     */
    public void setStart(Instant start) {
        this.start = start;
    }

    /**
     * Get end of time period
     *
     * @return end instant
     */
    public Instant getEnd() {
        return end;
    }

    /**
     * Set end of time period
     *
     * @param end end instant
     */
    public void setEnd(Instant end) {
        this.end = end;
    }

    /**
     * Get Day Of week from instant
     *
     * @param i Instant
     * @return day of weeek
     */
    private DayOfWeek getDayOfWeek(Instant i) {
        return i.atZone(ZoneId.systemDefault()).getDayOfWeek();
    }
}
