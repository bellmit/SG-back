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
     * ����ʵ�����
     */
    public void save(T entity);

    public T newSave(T entity);

    /**
     * ��������ʵ�����
     */
    public void saveAll(List<T> entities);

    /**
     * ɾ��ʵ�����
     */
    public void remove(T entity);

    /**
     * ����Map������������ɾ��
     */
    public void removeByParams(Map<String, Object> params);

    /**
     * ��������ɾ��ʵ�����
     */
    public void removeByPrimaryKey(Long id);

    /**
     * ������������ɾ��ʵ�����
     */
    public void removeAllByPrimaryKey(List<Long> ids);

    /**
     * ����ɾ��ʵ�����
     */
    public void removeAll(List<T> entities);

    /**
     * ����ʵ�����
     */
    public void modify(T entity);

    /**
     * ��������ʵ�����
     */
    public void modifyAll(List<T> entities);

    /**
     * ������������Ψһ����
     */
    public T findByPrimaryKey(Long id);

    /**
     * ���ݲ�������ʵ����󼯺�
     */
    public List<T> findByParams(Map<String, Object> params) throws Exception;

    /**
     * ��ҳ��ѯ
     *
     * @param params ���ò�����Map����key=��������value=����ֵ
     */
    public Page<T> findByPage(Map<String, Object> params, int pageNo, int limit) throws Exception;

    /**
     * ���ݲ�������ʵ���������
     */
    public Long findCountByParams(Map<String, Object> params) throws Exception;

    /**
     * ���ݿ�ʼʱ�����ʱ���ѯ
     *
     * @return
     */
    public List<T> findByStartAndEndTime(Map<String, Object> params) throws Exception;
}
