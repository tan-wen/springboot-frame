package com.aoyang.modules.book.dto;

import java.io.Serializable;

/**
 * @author daixy
 * @email daixy@aoyang.com
 * @date 2020-1-21 13:52:44
 */

public class DetectionDep implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 位置
     */
    private String location;
    /**
     * 顺序
     */
    private Integer position;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
