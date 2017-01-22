/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.inhand.common.smart.model;

import java.util.List;

/**
 *
 * @author lenovo
 */
public class ContainerDto {

    private String modelId;     //货柜机型
    private String modelName;   //机型名称
    private String cid;         //货柜编号
    private Integer type;      //货柜类型
    private String vender;       //厂家
    private String venderNum;    //厂家编号
    private String modelPicId;
    private String modelPicName;
    private List<GoodsConfig> shelves;

    public String getVenderNum() {
        return venderNum;
    }

    public void setVenderNum(String venderNum) {
        this.venderNum = venderNum;
    }
    
    
    public String getModelPicId() {
        return modelPicId;
    }

    public void setModelPicId(String modelPicId) {
        this.modelPicId = modelPicId;
    }

    public String getModelPicName() {
        return modelPicName;
    }

    public void setModelPicName(String modelPicName) {
        this.modelPicName = modelPicName;
    }
    
    
    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getVender() {
        return vender;
    }

    public void setVender(String vender) {
        this.vender = vender;
    }

    
    
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<GoodsConfig> getShelves() {
        return shelves;
    }

    public void setShelves(List<GoodsConfig> shelves) {
        this.shelves = shelves;
    }
    
}
