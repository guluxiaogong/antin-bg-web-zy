package com.antin.helper;

/**
 * Created by Administrator on 2017/6/20.
 * hdfs客户端
 */

import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class FileSystemUtil {
    private static FileSystem fs = (FileSystem) SpringContextHolder.getBean("hadoop-cluster");

    public void mkdirs(String dir) throws Exception { // create HDFS folder 创建一个文件夹
        Path path = new Path(dir);
        fs.mkdirs(path);
    }

    public void create(String fileName, String content) throws Exception { // create a file 创建一个文件
        Path path = new Path(fileName);
        FSDataOutputStream out = fs.create(path);
        out.write(content.getBytes());
    }

    public void rename(String src, String dst) throws Exception { // rename a file 重命名
        Path path = new Path(src);
        Path newPath = new Path(dst);
        System.out.println(fs.rename(path, newPath));
    }

    public void copyFromLocalFile(String local, String hdfs) throws Exception { // upload a local file
        // 上传文件
        Path src = new Path(local);
        Path dst = new Path(hdfs);
        fs.copyFromLocalFile(src, dst);
    }

    // upload a local file
    // 上传文件
    public void uploadLocalFile2(String local, String hdfs) throws Exception {
        Path src = new Path(local);
        Path dst = new Path(hdfs);
        InputStream in = new BufferedInputStream(new FileInputStream(new File(
                local)));
        FSDataOutputStream out = fs.create(new Path(hdfs));
        IOUtils.copyBytes(in, out, 4096);
    }

    public void listFiles(String dir) throws Exception { // list files under folder
        // 列出文件
        Path dst = new Path(dir);
        FileStatus[] files = fs.listStatus(dst);
        for (FileStatus file : files) {
            System.out.println(file.getPath().toString());
        }
    }

    public void getBlockInfo(String file) throws Exception { // list block info of file
        // 查找文件所在的数据块
        Path dst = new Path(file);
        FileStatus fileStatus = fs.getFileStatus(dst);
        BlockLocation[] blkloc = fs.getFileBlockLocations(fileStatus, 0,
                fileStatus.getLen()); // 查找文件所在数据块
        for (BlockLocation loc : blkloc) {
            for (int i = 0; i < loc.getHosts().length; i++)
                System.out.println(loc.getHosts()[i]);
        }
    }
}