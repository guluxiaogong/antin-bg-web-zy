package com.antin.por.handle;

/**
 * Created by Administrator on 2017/9/2.
 */
public class HandleFactory {

    public static Handle handle() {
        return new DefalutHandle();
    }

}
