/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.common.service;

import com.shiguo.common.dao.BaseDao;
import com.shiguo.common.entity.AbstractEntity;
import com.shiguo.common.vo.Page;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lenovo
 */
public abstract class CommonService<T extends AbstractEntity> implements BaseService<T> {

    private static final long serialVersionUID = -7565546859045758489L;

    protected abstract BaseDao<T> getServiceDao();

    public Page<T> findByPage(Map<String, Object> params, int pageNo, int limit) throws Exception {
        return getServiceDao().findByPage(params, pageNo, limit);
    }

    public void save(T entity) throws Exception {
        getServiceDao().save(entity);
    }

    public T newSave(T entity) throws Exception {
        return getServiceDao().newSave(entity);
    }

    public void saveAll(List<T> entities) throws Exception {
        getServiceDao().saveAll(entities);
    }

    public void modify(T entity) throws Exception {
        getServiceDao().modify(entity);
    }

    public void modifyAll(List<T> entities) throws Exception {
        getServiceDao().modifyAll(entities);
    }

    public List<T> findAll() throws Exception {
        return getServiceDao().findByParams(new HashMap<String, Object>());
    }

    public void remove(T entity) throws Exception {
        getServiceDao().remove(entity);
    }

    public void removeByPrimaryKey(Long lid) throws Exception {
        getServiceDao().removeByPrimaryKey(lid);
    }

    public void removeAll(List<T> entities) throws Exception {
        getServiceDao().removeAll(entities);
    }

    public void removeAllByPrimaryKey(List<Long> lids) throws Exception {
        getServiceDao().removeAllByPrimaryKey(lids);
    }

    public T findByPrimaryKey(Long lid) throws Exception {
        return getServiceDao().findByPrimaryKey(lid);
    }

    public List<T> findByPrimaryKeys(Long[] ids) {
        List<T> aList = new ArrayList<T>();
        if (ids != null) {
            for (Long id : ids) {
                T val = getServiceDao().findByPrimaryKey(id);
                if (val != null) {
                    aList.add(val);
                }
            }
        }
        return aList;
    }

    public T findUniqueByParams(Map<String, String> params) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                String val = params.get(key);
                paramMap.put(key, val);
            }
        }
        T ret = null;
        try {
            List<T> values = getServiceDao().findByParams(paramMap);
            if (values != null && !values.isEmpty()) {
                ret = values.get(0);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ret;
    }

    public List<T> findByParams(Map<String, Object> params) throws Exception {
        return getServiceDao().findByParams(params);
    }

    public List<T> findByStartAndEndTime(Map<String, Object> params) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Long findCountByParams(Map<String, Object> params) throws Exception {
        return getServiceDao().findCountByParams(params);
    }
}
