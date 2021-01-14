package com.sxlg.demo.model.config;

public class Config {
     private String key;
     private String value;
     private String defaultValue;
     public Config(String key, String value) {
          this.key = key;
          this.value = value;
     }

     public Config(String key, String value, String defaultValue) {
          this.key = key;
          this.value = value;
          this.defaultValue = defaultValue;
     }

     public String getKey() {
          return key;
     }

     public void setKey(String key) {
          this.key = key;
     }

     public String getValue() {
          return value;
     }

     public void setValue(String value) {
          this.value = value;
     }

     public String getDefaultValue() {
          return defaultValue;
     }

     public void setDefaultValue(String defaultValue) {
          this.defaultValue = defaultValue;
     }

     @Override
     public String toString() {
          return "Config{" +
                  "key='" + key + '\'' +
                  ", value='" + value + '\'' +
                  ", defaultValue='" + defaultValue + '\'' +
                  '}';
     }
}
