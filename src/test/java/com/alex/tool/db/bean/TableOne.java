package com.alex.tool.db.bean;

import com.alex.tool.db.util.UUIDUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "table_one")
public class TableOne {

    private String idOne;

    private String colOne;

    private String colTwo;

    private String colThree;

    public static TableOne getInstance() {
        TableOne tableOne = new TableOne();
        tableOne.setIdOne(UUIDUtil.getUUID32());
        tableOne.setColOne("1");
        tableOne.setColTwo("2");
        tableOne.setColThree("3");
        return tableOne;
    }

    public static List getInstanceList() {
        List<TableOne> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(getInstance());
        }
        return list;
    }

    @Id
    @Column(name = "id_one")
    public String getIdOne() {
        return idOne;
    }

    public void setIdOne(String idOne) {
        this.idOne = idOne;
    }

    @Column(name = "col_one")
    public String getColOne() {
        return colOne;
    }

    public void setColOne(String colOne) {
        this.colOne = colOne;
    }

    @Column(name = "col_two")
    public String getColTwo() {
        return colTwo;
    }

    public void setColTwo(String colTwo) {
        this.colTwo = colTwo;
    }

    @Column(name = "col_three")
    public String getColThree() {
        return colThree;
    }

    public void setColThree(String colThree) {
        this.colThree = colThree;
    }
}
