package com.sxlg.demo.model.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ConfigProvider {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigProvider.class);
    private Map<String,Config> properties = new HashMap<>();
    private String configFilePath = "demo.properties";


    public ConfigProvider(String configFilePath) {
        this.configFilePath = configFilePath;
        load();
    }

    public ConfigProvider() {
        load();
    }

    private void load() {
        InputStream input = getClass().getClassLoader().getResourceAsStream(configFilePath);
        Properties properties = new Properties();
        try {
            properties.load(input);
        } catch (IOException e) {
            LOG.error("the configuration file failed to load, fileName: {}",configFilePath);
        }
        build(properties);
    }

    private void build(Properties properties) {
        if (properties != null) {
            Iterator<String> it = properties.stringPropertyNames().iterator();
            while(it.hasNext()) {
                String key = it.next();
                String value = properties.getProperty(key);
                this.properties.put(key,new Config(key,value));
            }
        }
    }
}
