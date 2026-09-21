package com.college.erp.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sessionals")
public class Sessional {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rollNo, subjectCode, subjectName;
    private Integer s1, s2, s3, total;
    private String status;

    public Long getId(){ return id; }
    public void setIdForApi(Long id){ this.id=id; }
    public String getRollNo(){ return rollNo; } public void setRollNo(String v){ rollNo=v; }
    public String getSubjectCode(){ return subjectCode; } public void setSubjectCode(String v){ subjectCode=v; }
    public String getSubjectName(){ return subjectName; } public void setSubjectName(String v){ subjectName=v; }
    public Integer getS1(){ return s1; } public void setS1(Integer v){ s1=v; }
    public Integer getS2(){ return s2; } public void setS2(Integer v){ s2=v; }
    public Integer getS3(){ return s3; } public void setS3(Integer v){ s3=v; }
    public Integer getTotal(){ return total; } public void setTotal(Integer v){ total=v; }
    public String getStatus(){ return status; } public void setStatus(String v){ status=v; }
}
