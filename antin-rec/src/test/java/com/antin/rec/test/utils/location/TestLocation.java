package com.antin.rec.test.utils.location;

import com.antin.rec.util.location.LatitudeLontitudeUtil;
import com.antin.rec.util.location.Location;
import org.junit.Test;

/**
 * Created by Administrator on 2017/8/14 0014.
 */
public class TestLocation {

    @Test
    public void test1() {

        //两坐标距离，单位：米
        double distance = LatitudeLontitudeUtil.getDistance(24.492386218336801, 118.171336235620998, 24.457261828297238, 118.094112966803010);
        System.out.println(distance);
        //坐标附近500米组成正方形四点坐标
        Location[] rectangle4Point = LatitudeLontitudeUtil.getRectangle4Point(24.492386218336801, 118.171336235620998, 500);
        for (Location d : rectangle4Point) {
            System.out.println(d.toString());
        }
    }
}
