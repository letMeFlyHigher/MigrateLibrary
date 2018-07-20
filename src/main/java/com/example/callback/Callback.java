package com.example.callback;

import java.sql.PreparedStatement;

/**
 *  回调接口
 */
public interface Callback {
    /**
     *
     * @param ps
     * @param field 字段名
     * @param val   字段值
     */
    void call(PreparedStatement ps, String field, Object val);
}
