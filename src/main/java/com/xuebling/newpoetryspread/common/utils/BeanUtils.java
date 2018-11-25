package com.xuebling.newpoetryspread.common.utils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

//利用反射去对类进行操作
public class BeanUtils {
    public static <T> DBObject bean2DBObject(T bean) throws IllegalArgumentException, IllegalAccessException {
        if (bean == null) {
            return null;
        }
        DBObject dbObject = new BasicDBObject();
        // 获取对象对应类中的所有属性域
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 获取属性名
            String varName = field.getName();
            // 修改访问控制权限
            boolean accessFlag = field.isAccessible();
            if (!accessFlag) {
                field.setAccessible(true);
            }
            Object param = field.get(bean);
            if (param == null) {
                continue;
            } else if (param instanceof Integer) {//判断变量的类型
                int value = ((Integer) param).intValue();
                dbObject.put(varName, value);
            } else if (param instanceof String) {
                String value = (String) param;
                dbObject.put(varName, value);
            } else if (param instanceof Double) {
                double value = ((Double) param).doubleValue();
                dbObject.put(varName, value);
            } else if (param instanceof Float) {
                float value = ((Float) param).floatValue();
                dbObject.put(varName, value);
            } else if (param instanceof Long) {
                long value = ((Long) param).longValue();
                dbObject.put(varName, value);
            } else if (param instanceof Boolean) {
                boolean value = ((Boolean) param).booleanValue();
                dbObject.put(varName, value);
            } else if (param instanceof Date) {
                Date value = (Date) param;
                dbObject.put(varName, value);
            }
            // 恢复访问控制权限
            field.setAccessible(accessFlag);
        }
        return dbObject;
    }

    //把DBObject转换成bean对象
    public static <T> T dbObject2Bean(DBObject dbObject, T bean) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        if (bean == null) {
            return null;
        }
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            String varName = field.getName();
            Object object = dbObject.get(varName);
            //给对象设置属性值
            if (object != null) {
//                BeanUtils.setProperty(bean, varName, object);
            }
        }
        return bean;
    }
}

