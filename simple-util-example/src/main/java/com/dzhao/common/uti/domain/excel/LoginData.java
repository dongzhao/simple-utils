package com.dzhao.common.uti.domain.excel;

import com.dzhao.common.util.annotation.ExcelCell;
import com.dzhao.common.util.annotation.Excel;

/**
 * Created by dzhao on 28/07/2015.
 */
@Excel(tabName = "login", startRow = 2)
public class LoginData {
    @ExcelCell(column = "A")
    private String username;
    @ExcelCell(column = "B")
    private String password;
    @ExcelCell(column = "C")
    private String verifyText;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyText() {
        return verifyText;
    }

    public void setVerifyText(String verifyText) {
        this.verifyText = verifyText;
    }
}
