package com.antin.por.extension;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * Created by jichangjin on 2017/9/20.
 */
public class PutExtension extends Put {

    String columnFamilyName;

    public PutExtension(String columnFamilyName, byte[] row) {
        super(row);
        this.columnFamilyName = columnFamilyName;
    }

    public PutExtension build(String paramName, Object param) throws IOException {
        if (param != null) {
            this.addColumn(columnFamilyName.getBytes(), paramName.getBytes(),
                    Bytes.toBytes(param.toString()));
        }
        return this;
    }

}

