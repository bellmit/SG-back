/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.common.service;

import com.shiguo.common.vo.Page;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lenovo
 */
public interface BaseService<T> extends Serializable {

    public static final String PRE_INSERT = "insert";
    public static final String PRE_UPDATE = "update";
    public static final String PRE_DELETE = "delete";
    public static final String PRE_FIND = "find";
    public static final String PRE_FIND_ALL = "findAll";
    public static final String PRE_FIND_BY_PK = "findByPrimaryKey";

    /**
     * 分页查询
     *
     * @param params 放置参数的Map对象，key=参数名，value=参数值
     * @param pageNo 查询页码
     * @param limit 每页限定记录数
     * @return 组装好的Page对象
     * @see com.zy.common.pagination.Page
     */
    public Page<T> findByPage(Map<String, Object> params, int pageNo, int limit) throws Exception;//

    /**
     * 创建实体对象
     */
    public void save(T entity) throws Exception;//

    public T newSave(T entity) throws Exception;

    /**
     * 批量创建实体对象
     */
    public void saveAll(final List<T> entities) throws Exception;//

    /**
     * 更新实体对象
     */
    public void modify(T entity) throws Exception;//

    /**
     * 批量更新实体对象
     */
    public void modifyAll(final List<T> entities) throws Exception;//

    /**
     * 查询全部记录
     */
    public List<T> findAll() throws Exception;

    /**
     * 删除实体对象
     */
    public void remove(T entity) throws Exception;//

    /**
     * 根据主键删除实体对象
     */
    public void removeByPrimaryKey(Long lid) throws Exception;//

    /**
     * 批量删除实体对象
     */
    public void removeAll(final List<T> entities) throws Exception;//

    /**
     * 根据主键批量删除实体对象
     */
    public void removeAllByPrimaryKey(final List<Long> lids) throws Exception;//

    /**
     * 根据主键返回唯一对象
     */
    public T findByPrimaryKey(Long lid) throws Exception;//

    /**
     * 根据主键数组返回一批对象
     */
    public List<T> findByPrimaryKeys(Long[] lids);

    /**
     * 根据参数返回一个实体对象，一般用于查询具有唯一约束条件的记录 必须保证传入的参数确实能定位唯一一个对象
     */
    public T findUniqueByParams(Map<String, String> params);

    /**
     * 根据参数返回实体对象集合
     */
    public List<T> findByParams(Map<String, Object> params) throws Exception;

    /**
     * 根据开始时间结束时间查询
     *
     * @param params
     * @return
     * @throws Exception
     */
    public List<T> findByStartAndEndTime(Map<String, Object> params) throws Exception;
}
