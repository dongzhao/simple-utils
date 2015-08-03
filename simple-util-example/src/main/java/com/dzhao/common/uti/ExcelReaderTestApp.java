package com.dzhao.common.uti;

import com.dzhao.common.uti.domain.excel.LoginData;
import com.dzhao.common.uti.domain.excel.UserData;
import com.dzhao.common.util.ExcelReader;

import java.io.IOException;
import java.util.List;

/**
 * Created by Home on 28/07/2015.
 */
public class ExcelReaderTestApp {
    public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException {

        ExcelReader reader = new ExcelReader("test_data.xlsx");

        List<LoginData> loginResults = reader.convert(LoginData.class);
        List<UserData> userResults = reader.convert(UserData.class);

        for(LoginData result : loginResults){
            System.out.println("username: " + result.getUsername());
            System.out.println("password: " + result.getPassword());
        }

        for(UserData result : userResults){
            System.out.println("First Name: " + result.getFirstName());
            System.out.println("Last Name: " + result.getLastName());
        }

    }
}
