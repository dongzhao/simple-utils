package com.dzhao.common.uti;

import com.dzhao.common.uti.domain.config.MyConfig;
import com.dzhao.common.util.ConfigReader;
import org.apache.commons.configuration.ConfigurationException;

/**
 * Created by Home on 3/08/2015.
 */
public class ConfigReaderTestApp {
    public static void main(String[] args){

        try {
            ConfigReader reader = new ConfigReader("test.properties");

            MyConfig result = reader.getConfig(MyConfig.class);

            System.out.println("int field: " + result.getTestInt());
            System.out.println("String field: " + result.getTestString());
            System.out.println("field no key: " + result.getFieldStr());
            System.out.println("list field: " + result.getTestList());
            System.out.println("boolean field: " + result.isTestBoolean());


        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
}
