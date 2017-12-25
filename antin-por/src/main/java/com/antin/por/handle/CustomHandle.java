package com.antin.por.handle;

import com.antin.por.entity.HBaseModel;
import com.antin.por.entity.PortraitModel;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/9/2.
 */
public class CustomHandle extends DefalutHandle {

    @Override
    public Handle loadData(Table table, HBaseModel hm) throws IOException {
        return this;
    }

    @Override
    public List<PortraitModel> handleResult() {
        return null;
    }
}
