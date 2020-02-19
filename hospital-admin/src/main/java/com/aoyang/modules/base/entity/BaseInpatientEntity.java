package com.aoyang.modules.base.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class BaseInpatientEntity {
    private String patNo;
    private String name;
    private Long gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String age;
    private String phone;
    private String ward;
    private String genderDesc;
}
