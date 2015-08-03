package com.dzhao.common.uti.domain.config;

import com.dzhao.common.util.annotation.Config;
import com.dzhao.common.util.annotation.ConfigField;

import java.util.List;

/**
 * Created by DZhao on 3/08/2015.
 */
@Config(prefix = "my.test.config")
public class MyConfig {
    @ConfigField(name = "int")
    private int testInt;
    @ConfigField(name = "string")
    private String testString;
    @ConfigField(name = "boolean")
    private boolean testBoolean;
    @ConfigField(name = "list")
    private List<String> testList;
    @ConfigField
    private String fieldStr;

    public int getTestInt() {
        return testInt;
    }

    public void setTestInt(int testInt) {
        this.testInt = testInt;
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    public boolean isTestBoolean() {
        return testBoolean;
    }

    public void setTestBoolean(boolean testBoolean) {
        this.testBoolean = testBoolean;
    }

    public List<String> getTestList() {
        return testList;
    }

    public void setTestList(List<String> testList) {
        this.testList = testList;
    }

    public String getFieldStr() {
        return fieldStr;
    }

    public void setFieldStr(String fieldStr) {
        this.fieldStr = fieldStr;
    }
}
