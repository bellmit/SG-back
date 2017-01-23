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
     * ��ҳ��ѯ
     *
     * @param params ���ò�����Map����key=��������value=����ֵ
     * @param pageNo ��ѯҳ��
     * @param limit ÿҳ�޶���¼��
     * @return ��װ�õ�Page����
     * @see com.zy.common.pagination.Page
     */
    public Page<T> findByPage(Map<String, Object> params, int pageNo, int limit) throws Exception;//

    /**
     * ����ʵ�����
     */
    public void save(T entity) throws Exception;//

    public T newSave(T entity) throws Exception;

    /**
     * ��������ʵ�����
     */
    public void saveAll(final List<T> entities) throws Exception;//

    /**
     * ����ʵ�����
     */
    public void modify(T entity) throws Exception;//

    /**
     * ��������ʵ�����
     */
    public void modifyAll(final List<T> entities) throws Exception;//

    /**
     * ��ѯȫ����¼
     */
    public List<T> findAll() throws Exception;

    /**
     * ɾ��ʵ�����
     */
    public void remove(T entity) throws Exception;//

    /**
     * ��������ɾ��ʵ�����
     */
    public void removeByPrimaryKey(Long lid) throws Exception;//

    /**
     * ����ɾ��ʵ�����
     */
    public void removeAll(final List<T> entities) throws Exception;//

    /**
     * ������������ɾ��ʵ�����
     */
    public void removeAllByPrimaryKey(final List<Long> lids) throws Exception;//

    /**
     * ������������Ψһ����
     */
    public T findByPrimaryKey(Long lid) throws Exception;//

    /**
     * �����������鷵��һ������
     */
    public List<T> findByPrimaryKeys(Long[] lids);

    /**
     * ���ݲ�������һ��ʵ�����һ�����ڲ�ѯ����ΨһԼ�������ļ�¼ ���뱣֤����Ĳ���ȷʵ�ܶ�λΨһһ������
     */
    public T findUniqueByParams(Map<String, String> params);

    /**
     * ���ݲ�������ʵ����󼯺�
     */
    public List<T> findByParams(Map<String, Object> params) throws Exception;

    /**
     * ���ݿ�ʼʱ�����ʱ���ѯ
     *
     * @param params
     * @return
     * @throws Exception
     */
    public List<T> findByStartAndEndTime(Map<String, Object> params) throws Exception;
}
