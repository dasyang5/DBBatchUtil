package com.alex.tool.db.bean;

import com.alex.tool.db.util.UUIDUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "table_two")
public class TableTwo {

    private String idOne;

    private String idTwo;

    private String colOne;

    private String colTwo;

    public static TableTwo getInstance() {
        TableTwo tableTwo = new TableTwo();
        tableTwo.setIdOne(UUIDUtil.getUUID32());
        tableTwo.setIdTwo(UUIDUtil.getUUID32());
        tableTwo.setColOne("1");
        tableTwo.setColTwo("2");
        return tableTwo;
    }

    public static List getInstanceList() {
        List<TableTwo> list = new ArrayList<>();
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

    @Id
    @Column(name = "id_two")
    public String getIdTwo() {
        return idTwo;
    }

    public void setIdTwo(String idTwo) {
        this.idTwo = idTwo;
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
}
