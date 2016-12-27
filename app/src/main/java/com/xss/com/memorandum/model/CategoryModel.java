package com.xss.com.memorandum.model;

/**
 * Desc：
 * Author: xss
 * Time：2016/2/18 16:53
 */
public class CategoryModel extends BaseModel {
    private Integer _id;
    private Integer category_id;
    private String type;

    public CategoryModel() {

    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
