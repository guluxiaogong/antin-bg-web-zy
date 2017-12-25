package com.antin.rec.util.helper;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/3/1.
 */
@Component
public class SqlHelper implements ResourceLoaderAware {
    private ResourceLoader resourceLoader;
    private static String prefix = "sqlXml";

    Logger logger = Logger.getLogger(SqlHelper.class);

    public static HashMap<String, String> sqlMap = new HashMap<>();

    @Override
    public void setResourceLoader(ResourceLoader loader) {
        this.resourceLoader = loader;
    }

    @PostConstruct
    public void initialize() throws Exception {
        refreshConfig();
        logger.info("Initializing bean " + getClass());
    }

    /**
     * 重新加载配置，以支持热部署
     *
     * @throws Exception
     */
    public void refreshConfig() throws Exception {
        // 加载客户端系统配置列表
        try {
            if (!sqlMap.isEmpty())
                sqlMap.clear();
//            for (String fileName : getXmlFileName())
//                loadSqlXml(fileName);
            loadSqlXml(prefix + "/recom-sql.xml");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("加载sqlXml配置失败");
        }
    }

    /**
     * 获取sql
     */
    public static String getSql(String id) {
        return sqlMap.get(id);
    }

    private List<String> getXmlFileName() {

        List<String> list = new ArrayList<>();
        try {
            File file = new File(SqlHelper.class.getClassLoader().getResource(prefix).getPath());//TODO 打成jar时文件取不到
            if (file.exists())
                listFile(file, list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private void listFile(File file, List<String> list) {
        for (File children : file.listFiles()) {
            if (!children.isFile())
                listFile(children, list);
            else {
                String path = children.getAbsolutePath();
                list.add(path.substring(path.indexOf(prefix)));
            }

        }
    }

    private void loadSqlXml(String fileName) throws Exception {

        Resource resource = resourceLoader.getResource("classpath:" + fileName);
        // dom4j
        SAXReader reader = new SAXReader();
        Document doc = reader.read(resource.getInputStream());

        Element rootElement = doc.getRootElement();
        List<Element> systemElements = rootElement.elements();

        for (Element element : systemElements) {
            logger.info("加载sql" + element.attributeValue("id") + ":" + element.getText().trim());
            sqlMap.put(element.attributeValue("id"), element.getText().trim());
        }
    }


}
