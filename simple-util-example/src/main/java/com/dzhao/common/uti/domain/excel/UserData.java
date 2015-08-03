package com.dzhao.common.uti.domain.excel;

import com.dzhao.common.util.annotation.ExcelCell;
import com.dzhao.common.util.annotation.Excel;

/**
 * Created by Home on 28/07/2015.
 */
@Excel(tabName = "user", startRow = 2)
public class UserData {
    @ExcelCell(column = "A")
    private String firstName;
    @ExcelCell(column = "B")
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
