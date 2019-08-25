package com.wesley.growth.hbase;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

/**
 * <p>
 *
 * </p>
 * Email yani@uoko.com
 *
 * @author Created by Yani on 2018/11/20
 */
@Slf4j
public class PropertiesUtil {
    private Properties properties = new Properties();

    private PropertiesUtil(){
        try {
            properties.load(HbaseUtil.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            log.error("加载 application.properties 出错", e);
        }
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue){
        return properties.getProperty(key, defaultValue);
    }

    public static PropertiesUtil getInstance(){
        return new PropertiesUtil();
    }

}
