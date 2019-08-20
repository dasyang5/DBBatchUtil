package com.alex.tool.db.analyze.impl;

import com.alex.tool.db.bean.TableOne;
import org.junit.jupiter.api.Test;

class HibernateAnalyzeTest {

    @Test
    void getTableName() {
        System.out.println(new HibernateAnalyze().getTableName(TableOne.getInstance()));
    }

    @Test
    void getPrimaryKeys() {
        System.out.println(new HibernateAnalyze().getPrimaryKeys(TableOne.getInstance()).toString());
    }

    @Test
    void getNonPrimaryKeys() {
        System.out.println(new HibernateAnalyze().getNonPrimaryKeys(TableOne.getInstance()).toString());
    }

    @Test
    void getAllKeys() {
        System.out.println(new HibernateAnalyze().getAllKeys(TableOne.getInstance()).toString());
    }

    @Test
    void getSpecificKeys() {
        System.out.println(new HibernateAnalyze().getSpecificKeys(TableOne.getInstance(),new String[]{"col_one","col_two"}).toString());
    }
}