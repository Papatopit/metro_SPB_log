import core.Line;
import core.Station;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class TestRouteCalculator extends TestCase {

    List<Station> route;
    private static StationIndex stationIndex;
    RouteCalculator routeCalculator;
    List<Station> connectionStations;
    Line line2;
    Line line3;
    Line line5;

    @Override
    protected void setUp() throws Exception {
        route = new ArrayList<>();
        stationIndex = new StationIndex();
        routeCalculator = new RouteCalculator(stationIndex);
        connectionStations = new ArrayList<>();


        line2 = new Line(2, "Вторая");
        line3 = new Line(3, "Третья");
        line5 = new Line(5, "Пятая");

        Station s1l3 = new Station("Приморская", line3);
        line3.addStation(s1l3);
        stationIndex.addStation(s1l3);
        route.add(s1l3);
        Station s2l3 = new Station("Василеостровская", line3);
        line3.addStation(s2l3);
        stationIndex.addStation(s2l3);
        route.add(s2l3);
        Station s3l3 = new Station("Гостиный двор", line3);
        line3.addStation(s3l3);
        stationIndex.addStation(s3l3);
        route.add(s3l3);
        Station s4l2 = new Station("Невский проспект", line2);
        line2.addStation(s4l2);
        stationIndex.addStation(s4l2);
        route.add(s4l2);
        Station s5l2 = new Station("Сенная площадь", line2);
        line2.addStation(s5l2);
        stationIndex.addStation(s5l2);
        route.add(s5l2);
        Station s6l5 = new Station("Садовая", line5);
        line5.addStation(s6l5);
        stationIndex.addStation(s6l5);
        route.add(s6l5);
        Station s7l5 = new Station("Звенигородская", line5);
        line5.addStation(s7l5);
        stationIndex.addStation(s7l5);
        route.add(s7l5);
        Station s8l5 = new Station("Обводный канал", line5);
        line5.addStation(s8l5);
        stationIndex.addStation(s8l5);
        route.add(s8l5);
        Station s9l5 = new Station("Бухарестская", line5);
        line5.addStation(s9l5);
        stationIndex.addStation(s9l5);
        route.add(s9l5);
        Station s10l5 = new Station("Международная", line5);
        line5.addStation(s10l5);
        stationIndex.addStation(s10l5);
        route.add(s10l5);

        List<Station> connectSt1 = new ArrayList<>();
        List<Station> connectSt2 = new ArrayList<>();
        connectSt1.add(s3l3);
        connectSt1.add(s4l2);
        connectSt2.add(s5l2);
        connectSt2.add(s6l5);
        stationIndex.addConnection(connectSt1);
        stationIndex.addConnection(connectSt2);
    }

    public void testCalculateDuration() {
        double actual = RouteCalculator.calculateDuration(route);
        double expected = 24.5;
        assertEquals(expected, actual);
    }

    public void testGetShortestRoute() {
        List<Station> expected = routeCalculator.getShortestRoute(stationIndex.getStation("Приморская"),
                stationIndex.getStation("Василеостровская"));
        List<Station> actual = new ArrayList<>();
        actual.add(stationIndex.getStation("Приморская"));
        actual.add(stationIndex.getStation("Василеостровская"));
        assertEquals(expected, actual);
    }

    public void testRouteSameStation() {
        List<Station> expected = routeCalculator.getShortestRoute(stationIndex.getStation("Приморская"),
                stationIndex.getStation("Приморская"));
        List<Station> actual = new ArrayList<>();
        actual.add(stationIndex.getStation("Приморская"));
        assertEquals(expected, actual);
    }

    public void testReverseRoute() {
        List<Station> expected = routeCalculator.getShortestRoute(stationIndex.getStation("Международная"),
                stationIndex.getStation("Приморская"));
        List<Station> actual = new ArrayList<>();
        actual.add(stationIndex.getStation("Международная"));
        actual.add(stationIndex.getStation("Бухарестская"));
        actual.add(stationIndex.getStation("Обводный канал"));
        actual.add(stationIndex.getStation("Звенигородская"));
        actual.add(stationIndex.getStation("Садовая"));
        actual.add(stationIndex.getStation("Сенная площадь"));
        actual.add(stationIndex.getStation("Невский проспект"));
        actual.add(stationIndex.getStation("Гостиный двор"));
        actual.add(stationIndex.getStation("Василеостровская"));
        actual.add(stationIndex.getStation("Приморская"));
        assertEquals(expected, actual);

    }

    public void testGetRouteOnTheLine() {
        List<Station> routeOneLine = routeCalculator.getShortestRoute(stationIndex.getStation("Приморская"),
                stationIndex.getStation("Гостиный двор"));
        double actual = RouteCalculator.calculateDuration(routeOneLine);
        double expected = 2.5 * 2;
        assertEquals(expected, actual);


    }

    public void testGetRouteWithOneConnection() {
        List<Station> routeWithOneConnection = routeCalculator.getShortestRoute(stationIndex.getStation("Василеостровская"),
                stationIndex.getStation("Сенная Площадь"));
        double actual = RouteCalculator.calculateDuration(routeWithOneConnection);
        double expected = (2.5 * 2) + 3.5;
        assertEquals(expected, actual);


    }

    public void testGetRouteWithTwoConnections() {
        List<Station> routeWithTwoConnection = routeCalculator.getShortestRoute(stationIndex.getStation("Приморская"),
                stationIndex.getStation("Международная"));
        double actual = RouteCalculator.calculateDuration(routeWithTwoConnection);
        double expected = 2.5 * 7 + 2 * 3.5;
        assertEquals(expected, actual);

    }

    @Override
    protected void tearDown() throws Exception {

    }
}
