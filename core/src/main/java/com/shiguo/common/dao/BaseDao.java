/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.common.dao;

import com.shiguo.common.vo.Page;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lenovo
 */
public interface BaseDao<T> {

    /**
     * 创建实体对象
     */
    public void save(T entity);

    public T newSave(T entity);

    /**
     * 批量创建实体对象
     */
    public void saveAll(List<T> entities);

    /**
     * 删除实体对象
     */
    public void remove(T entity);

    /**
     * 按照Map参数进行条件删除
     */
    public void removeByParams(Map<String, Object> params);

    /**
     * 根据主键删除实体对象
     */
    public void removeByPrimaryKey(Long id);

    /**
     * 根据主键批量删除实体对象
     */
    public void removeAllByPrimaryKey(List<Long> ids);

    /**
     * 批量删除实体对象
     */
    public void removeAll(List<T> entities);

    /**
     * 更新实体对象
     */
    public void modify(T entity);

    /**
     * 批量更新实体对象
     */
    public void modifyAll(List<T> entities);

    /**
     * 根据主键返回唯一对象
     */
    public T findByPrimaryKey(Long id);

    /**
     * 根据参数返回实体对象集合
     */
    public List<T> findByParams(Map<String, Object> params) throws Exception;

    /**
     * 分页查询
     *
     * @param params 放置参数的Map对象，key=参数名，value=参数值
     */
    public Page<T> findByPage(Map<String, Object> params, int pageNo, int limit) throws Exception;

    /**
     * 根据参数返回实体对象数量
     */
    public Long findCountByParams(Map<String, Object> params) throws Exception;

    /**
     * 根据开始时间结束时间查询
     *
     * @return
     */
    public List<T> findByStartAndEndTime(Map<String, Object> params) throws Exception;
}