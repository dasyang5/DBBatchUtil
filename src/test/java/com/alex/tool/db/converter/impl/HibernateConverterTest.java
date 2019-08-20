package com.alex.tool.db.converter.impl;

import com.alex.tool.db.bean.TableOne;
import com.alex.tool.db.bean.TableTwo;
import org.junit.jupiter.api.Test;


class HibernateConverterTest {

    @Test
    void convertForInsert() {
        System.out.println(new HibernateConverter().convertForInsert(TableOne.getInstanceList()));
        System.out.println(new HibernateConverter().convertForInsert(TableTwo.getInstanceList()));
    }

    @Test
    void convertForUpdate() {
        System.out.println(new HibernateConverter().convertForUpdate(TableOne.getInstanceList(),new String[]{"col_one","col_two"},new String[]{"id_one","col_three"}));
        System.out.println(new HibernateConverter().convertForUpdate(TableTwo.getInstanceList(),new String[]{"col_one","col_two"},new String[]{"id_one","id_two"}));
    }

    @Test
    void convertForDelete() {
        System.out.println(new HibernateConverter().convertForDelete(TableOne.getInstanceList(),new String[]{"col_one","id_one"}));
        System.out.println(new HibernateConverter().convertForDelete(TableTwo.getInstanceList(),new String[]{"id_two","id_one"}));
    }
}