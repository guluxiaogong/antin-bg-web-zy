package com.antin.rec.test.utils.coordinate;

import com.antin.rec.util.coordinate.AddressCoordinateUtils;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class TestCoordinate {

    @Test
    public void test1() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String str2 = AddressCoordinateUtils.getCoordinateUtils("福建省厦门市思明区吕岭路1309号", "acGunaFvhWliO9ubU7pCwt8zYuHP8fzl");
        System.out.println(str2);
    }
}
