package com.example.util;

import java.util.Map;

/**
 * 用于帮助处理从数据库中查询出来的结果。
 */
public interface FieldHelper {
    /**
     *  对查出来的数据进行加工。
     * @param map 指的是每一行的map
     */
    public void editMapForUpdate(Map<String,Object> map);

    /**
     * 返回指定属性名的类型。
     * @param fieldName 指的是参数名
     * @return 指的是sqlTypes的代表值。
     */
    public int getFiledNameType(String fieldName);
}
