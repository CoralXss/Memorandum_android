package com.xss.com.memorandum.model;

import com.xss.com.memorandum.utils.Constants;

/**
 * Desc：
 * Author: xss
 * Time：2016/2/16 10:58
 */
public class MemorandumModel extends BaseModel {
    private Integer _id;
    private String title;
    private String content;
    private String create_date;
    private Integer category_id;

    public MemorandumModel() {

    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getCategoryType(Integer id) {
        return Constants.CATEGORY_TYPES.get(id);
    }
}
