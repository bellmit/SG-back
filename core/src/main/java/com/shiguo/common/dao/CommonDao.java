/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.common.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.shiguo.common.entity.AbstractEntity;
import com.shiguo.common.vo.Page;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

/**
 *
 * @author lenovo
 */
public class CommonDao<T extends AbstractEntity> implements BaseDao<T> {

    @Resource
    private SqlMapClient sqlMapClient;
    protected Class<T> entityClass;

    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    @SuppressWarnings("unchecked")
    protected Class<T> getEntityClass() {
        if (entityClass == null) {
            try {
                Object genericClz = getClass().getGenericSuperclass();
                if (genericClz instanceof ParameterizedType) {
                    entityClass = (Class<T>) ((ParameterizedType) genericClz).getActualTypeArguments()[0];
                }
            } catch (RuntimeException e) {
            }
        }
        return entityClass;
    }

    protected String generateSqlMapId(String name) {
        String perfix = getEntityClass().getSimpleName();
        return perfix + "." + name;
    }

    public void save(T entity) {
        try {
            Object id = sqlMapClient.insert(this.generateSqlMapId("insert"), entity);
            if (id != null) {
                entity.setId(Long.valueOf(id.toString()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public T newSave(T entity) {
        try {
            Object id = sqlMapClient.insert(this.generateSqlMapId("insert"), entity);
            if (id != null) {
                entity.setId(Long.valueOf(id.toString()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    public void saveAll(List<T> entities) {
        for (T entity : entities) {
            save(entity);
        }
    }

    public void remove(T entity) {
        try {
            sqlMapClient.delete(this.generateSqlMapId("delete"), entity.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeByParams(Map<String, Object> params) {
        try {
            sqlMapClient.delete(this.generateSqlMapId("deleteByParams"), params);
        } catch (SQLException e) {
        }
    }

    public void removeByPrimaryKey(Long id) {
        try {
            sqlMapClient.delete(this.generateSqlMapId("delete"), id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeAllByPrimaryKey(List<Long> ids) {
        for (Long id : ids) {
            removeByPrimaryKey(id);
        }
    }

    public void removeAll(List<T> entities) {
        for (T entity : entities) {
            remove(entity);
        }
    }

    public void modify(T entity) {
        try {
            sqlMapClient.update(this.generateSqlMapId("update"), entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void modifyAll(List<T> entities) {
        for (T entity : entities) {
            modify(entity);
        }
    }

    public T findByPrimaryKey(Long id) {
        T ret = null;
        try {
            ret = (T) sqlMapClient.queryForObject(this.generateSqlMapId("findByPrimaryKey"), id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }

    public List<T> findByParams(Map<String, Object> params) throws Exception {
        return sqlMapClient.queryForList(this.generateSqlMapId("findByParams"), params);
    }

    public Page<T> findByPage(Map<String, Object> params, int pageNo, int limit) throws Exception {
        Page<T> page = new Page<T>();
        Long totalRows = this.findCountByParams(params);
        page.setTotal(totalRows);
        @SuppressWarnings("unchecked")
        List<T> result = (List<T>) sqlMapClient.queryForList(this.generateSqlMapId("findByPage"), params);
        page.setRows(result);
        return page;
    }

    public Long findCountByParams(Map<String, Object> params) throws Exception {
        return (Long) sqlMapClient.queryForObject(this.generateSqlMapId("findCountByParams"), params);
    }

    public List<T> findByStartAndEndTime(Map<String, Object> params) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
