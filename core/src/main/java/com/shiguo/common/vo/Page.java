/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.common.vo;

import java.util.List;

/**
 *
 * @author lenovo
 */
public class Page<T> {
    // 记录总数

    private long total;
    // 结果集存放List
    private List<T> rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getOffset(int pageNo, int pageSize) {
        return (pageNo - 1);
    }
}
