package com.xuebling.newpoetryspread.common.converter;

import com.mongodb.DBObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@Component
//将DBObject转为Object
public class DbObjectToObjectConverterFactory implements ConverterFactory<DBObject, Object> {
    public <T extends Object> Converter<DBObject, T> getConverter(Class<T> targetType) {
        return new DbObjectToObjectConverter(targetType);
    }

    private final class DbObjectToObjectConverter<T extends Object> implements Converter<DBObject, T> {
        //需要转换的类
        private Class<T> objectType;

        public DbObjectToObjectConverter(Class<T> objectType) {
            this.objectType = objectType;
        }

        public T convert(DBObject source) {
            T object = null;
            try {
                object = objectType.newInstance();
                //拿到所有属性
                Field[] fields = objectType.getClass().getDeclaredFields();
                for (Field field : fields) {
                    //获取字段名
                    String key = field.getName();
                    //获取字段的数据
                    Object value = source.get(key);
                    //属性不存在,则添加属性
                    if (value != null) {
                        field.setAccessible(true);
                        field.set(object, value);
                        field.setAccessible(false);
                    }
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return object;
        }
    }
}
