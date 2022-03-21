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

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * The Station object
 */
public class Station {
    private final File archivePath;
    private final Repository repo;
    private String remoteGitURI;

    /**
     * Construct Archive in local dir {@code "dati"}
     *
     * @throws NotADirectoryException The selected path is not a dir
     * @throws IOException            Could not create the path
     * @throws GitAPIException        Git error
     */
    public Station() throws NotADirectoryException, IOException, GitAPIException {
        this(new File("dati"));
    }

    /**
     * Construct Archive in given path
     *
     * @param archivePath The Filepath to the dir where to store the git archive
     * @throws NotADirectoryException The selected path is not a dir
     * @throws IOException            Could not create the path
     * @throws GitAPIException        Git error
     */
    public Station(File archivePath) throws NotADirectoryException, IOException, GitAPIException {
        this(archivePath, true);
    }

    /**
     * Construct Archive in given path with {@code createDir} specified
     *
     * @param archivePath The Filepath to the dir where to store the git archive
     * @param createDir   Wether to create the dir if it is not present
     * @throws NotADirectoryException The selected path is not a dir
     * @throws IOException            Could not create the path
     * @throws GitAPIException        Git error
     */
    public Station(@NotNull File archivePath, boolean createDir) throws NotADirectoryException, IOException, GitAPIException {
        this(archivePath, createDir, "https://github.com/StazioneMeteoCocito/dati.git");
    }

    /**
     * Construct Archive in given path with {@code createDir} and {@code remoteGitURI} specified
     *
     * @param archivePath  The Filepath to the dir where to store the git archive
     * @param createDir    Wether to create the dir if it is not present
     * @param remoteGitURI The remote repository custom url. Ignored if the local archive already exists
     * @throws NotADirectoryException The selected path is not a dir
     * @throws IOException            Could not create the path
     * @throws GitAPIException        Git error
     */
    public Station(@NotNull File archivePath, boolean createDir, String remoteGitURI) throws NotADirectoryException, IOException, GitAPIException {
        this.fixslf4j();
        if (!archivePath.exists() && createDir) {
            if (!archivePath.mkdir()) throw new IOException();
        }
        if (!archivePath.isDirectory()) throw new NotADirectoryException();
        this.archivePath = archivePath;
        try {
            this.setRemoteGitURI(remoteGitURI);
        } catch (ArchiveAlreadyExists ignored) {
        }
        if (!this.isGitArchive()) {
            try {
                this.cloneRemoteArchive();
            } catch (ArchiveAlreadyExists ignored) {
            }
        }
        this.repo = this.getRepo();

    }

    /**
     * Get remote git uri path
     *
     * @return The remote path
     */
    public String getRemoteGitURI() {
        return remoteGitURI;
    }

    /**
     * Set the remote git uri path
     * used only in constructor, not handleable outside package as in previous snapshot
     *
     * @param remoteGitURI The remote path
     * @throws ArchiveAlreadyExists The repo has already been inited
     */
    private void setRemoteGitURI(String remoteGitURI) throws ArchiveAlreadyExists {
        if (this.isGitArchive()) throw new ArchiveAlreadyExists();
        this.remoteGitURI = remoteGitURI;
    }

    /**
     * Clone the git repo
     *
     * @throws GitAPIException      Git Error
     * @throws ArchiveAlreadyExists The git repo has already been inited
     */
    public void cloneRemoteArchive() throws GitAPIException, ArchiveAlreadyExists {
        if (this.isGitArchive()) throw new ArchiveAlreadyExists();
        Git.cloneRepository()
                .setURI(this.remoteGitURI)
                .call();
    }

    /**
     * Is the folder a git archive
     *
     * @return boolean
     */
    private boolean isGitArchive() {
        File f = new File(this.archivePath.getAbsolutePath() + File.separator + ".git");
        return f.exists();
    }

    /**
     * @return Get repo from path
     * @throws IOException     Path error
     * @throws GitAPIException Git error
     */
    private Repository getRepo() throws IOException, GitAPIException {
        Git git = Git.init().setDirectory(this.archivePath).call();
        return git.getRepository();
    }


    /**
     * Just ignore SLF4J error messages please
     */
    private void fixslf4j() {// disable warnings
        PrintStream filterOut = new PrintStream(System.err) {
            public void println(String l) {
                if (l != null && !l.startsWith("SLF4J")) {
                    super.println(l);
                }
            }
        };
        System.setErr(filterOut);
    }

    /**
     * @param aq Obtain all the files involved in a query and the datatype associated
     * @return a file desciptor list
     * @see it.edu.liceococito.cocitoWeatherStation.DataType
     * @see it.edu.liceococito.cocitoWeatherStation.InternalFileDescriptor
     */
    private @NotNull ArrayList<InternalFileDescriptor> queryFiles(ArchiveQuery aq) {
        ArrayList<InternalFileDescriptor> fl = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ITALIAN)
                .withZone(ZoneId.of("Europe/Rome"));
        ArrayList<TimePeriod> tps = aq.getTimePeriods();
        ArrayList<DataType> adts = aq.getAllowedDataTypes();
        Instant start, end;
        File a;
        for (int i = 0; i < tps.size(); i++) {
            start = tps.get(i).getStart();
            end = tps.get(i).getEnd();
            for (int j = 0; j < adts.size(); j++) {
                while (start.compareTo(end) < 0) {
                    String path = Paths.get(this.repo.getDirectory().getAbsolutePath()).getParent().getFileName() + File.separator + formatter.format(start).replaceAll("-", File.separator) + File.separator + adts.get(i).getCsvName() + ".csv";
                    a = new File(path);
                    if (a.exists()) fl.add(new InternalFileDescriptor(a, adts.get(i)));
                    start = start.plus(1, ChronoUnit.DAYS);
                }
            }
        }
        return fl;
    }

    /**
     * Execute a query on the archive
     *
     * @param archiveQuery the query to execute
     * @return the result of the query
     * @throws IOException Input/output error
     */
    public ArchiveQueryResult query(ArchiveQuery archiveQuery) throws IOException {
        ArrayList<InternalFileDescriptor> files = this.queryFiles(archiveQuery);
        PageList pages = new PageList();
        DateTimeFormatter valFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.ITALIAN)
                .withZone(ZoneId.systemDefault());
        Page page = new Page();
        int lineN;
        String line;
        for (int i = 0; i < files.size(); i++) {
            File f = files.get(i).getF();
            BufferedReader br = new BufferedReader(new FileReader(f));
            lineN = 0;
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] csvLine = line.split(",");    // use comma as separator
                if (csvLine.length < 2) continue;
                Instant instant = LocalDateTime.parse(csvLine[0], valFormatter).atZone(ZoneId.of("Europe/Rome")).toInstant();
                page.add(new Value(instant, f, lineN, Double.parseDouble(csvLine[1]), files.get(i).getDt()));
                if (lineN * (i + 1) % archiveQuery.getPageSize() == 0 && lineN * (i + 1) != 0 && archiveQuery.getPageSize() != 0) {
                    pages.add(page);
                    page = new Page();
                }
                lineN++;
            }
        }
        if (archiveQuery.getPageSize() == 0) {
            pages.add(page);
        }
        return new ArchiveQueryResult(pages, Instant.now());
    }

    /**
     * Update the local archive with the freshest data
     *
     * @return Operation success status
     * @throws IOException     Input/Output error
     * @throws GitAPIException Git error
     */
    public boolean udpate() throws IOException, GitAPIException {
        Git git = Git.open(this.repo.getDirectory());
        PullResult result = git.pull()
                .setRemote("origin")
                .setRemoteBranchName("main")
                .call();
        return result.isSuccessful();
    }

    /**
     * Get lastest hardware report from archive
     *
     * @return hardware report string
     * @throws IOException Error reading the file
     */
    public String getHardwareReport() throws IOException {
        String hrp = Paths.get(this.repo.getDirectory().getAbsolutePath()).getParent().getFileName() + File.separator + "report.txt";
        Scanner scanner = new Scanner(new File(hrp));
        String text = scanner.useDelimiter("\\A").next();
        scanner.close();
        return text;
    }

    /**
     * Get lastest measurements
     *
     * @return Latest measurements holder object
     * @throws IOException    I/O error
     * @throws ParseException JSON error
     */
    public LatestMeasurements getLastMeasurements() throws IOException, ParseException {
        String lastPath = Paths.get(this.repo.getDirectory().getAbsolutePath()).getParent().getFileName() + File.separator + "last.json";
        JSONParser parser = new JSONParser();
        JSONObject a = (JSONObject) parser.parse(new FileReader(lastPath));
        Instant i = Instant.parse((String) a.get("utciso"));
        double T = ((Number) a.get("T")).doubleValue();
        double H = ((Number) a.get("H")).doubleValue();
        double P = ((Number) a.get("P")).doubleValue();
        double PM10 = ((Number) a.get("PM10")).doubleValue();
        double PM25 = ((Number) a.get("PM25")).doubleValue();
        double S = ((Number) a.get("S")).doubleValue();
        return new LatestMeasurements(T, H, P, PM10, PM25, S, lastPath, i);
    }

    /**
     * Get station watcher with 1 min polling interval (default)
     *
     * @param stationEventListener The event listener
     * @return station watcher
     */
    public StationWatcher getStationWatcher(StationEventListener stationEventListener) {
        return new StationWatcher(this, 60, stationEventListener);
    }

    /**
     * Get station watcher with custom polling interval
     *
     * @param stationEventListener  The event listener
     * @param secondsBetweenUpdated polling interval
     * @return watcher
     */
    public StationWatcher getStationWatcher(StationEventListener stationEventListener, int secondsBetweenUpdated) {
        return new StationWatcher(this, secondsBetweenUpdated, stationEventListener);
    }
}
