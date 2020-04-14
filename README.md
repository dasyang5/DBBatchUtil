# DBBatchUtil
Java数据库批处理工具
## 说明
支持快速插入Hibernate实体类
## 使用
参考HibernateEntranceTest类

```
Entrance entrance = new HibernateEntrance(url, username, password, driverClassName);
entrance.start()开始事务<
        .insertObject(list1)//插入数据
        .insertObject(list2)//插入数据
        .insertObject(list3)//插入数据
        .end();//结束事务
```
