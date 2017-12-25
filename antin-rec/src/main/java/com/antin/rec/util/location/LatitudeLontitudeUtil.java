package com.antin.rec.util.location;

/**
 * 根据经纬度计算距离
 */
public class LatitudeLontitudeUtil {
    private static final double EARTH_RADIUS = 6371000;
    private double distance;
    private Location left_top = null;
    private Location right_top = null;
    private Location left_bottom = null;
    private Location right_bottom = null;

    private LatitudeLontitudeUtil(double distance) {
        this.distance = distance;
    }

    /**
     * 两坐标间距离
     *
     * @param lat0
     * @param lng0
     * @param lat1
     * @param lng1
     * @return
     */
    public static double getDistance(double lat0, double lng0, double lat1, double lng1) {
        lat0 = Math.toRadians(lat0);
        lat1 = Math.toRadians(lat1);
        lng0 = Math.toRadians(lng0);
        lng1 = Math.toRadians(lng1);
        double dlng = Math.abs(lng0 - lng1);
        double dlat = Math.abs(lat0 - lat1);
        double h = hav(dlat) + Math.cos(lat0) * Math.cos(lat1) * hav(dlng);
        double distance = 2 * EARTH_RADIUS * Math.asin(Math.sqrt(h));
        return distance;
    }

    /**
     * 获取某点附近distance正方形四角坐标
     *
     * @param lat      纬度
     * @param lng      经度
     * @param distance 距离
     * @return 四角坐标
     */
    public static Location[] getRectangle4Point(double lat, double lng, double distance) {
        LatitudeLontitudeUtil llu = new LatitudeLontitudeUtil(distance);
        llu.getRectangle4Point(lat, lng);
        Location[] locations = new Location[4];
        // 上左点
        locations[0] = llu.left_top;
        // 上右点
        locations[1] = llu.right_top;
        // 下左点
        locations[2] = llu.left_bottom;
        // 下右点
        locations[3] = llu.right_bottom;
        return locations;
    }

    private void getRectangle4Point(double lat, double lng) {
        double dlng = 2 * Math.asin(Math.sin(distance / (2 * EARTH_RADIUS)) / Math.cos(lat));
        dlng = Math.toDegrees(dlng);
        double dlat = distance / EARTH_RADIUS;
        dlat = Math.toDegrees(dlat);
        left_top = new Location(lat + dlat, lng - dlng);
        right_top = new Location(lat + dlat, lng + dlng);
        left_bottom = new Location(lat - dlat, lng - dlng);
        right_bottom = new Location(lat - dlat, lng + dlng);
    }

    private static double hav(double theta) {
        double s = Math.sin(theta / 2);
        return s * s;
    }
}