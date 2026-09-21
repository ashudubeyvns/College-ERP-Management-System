package com.college.erp.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "assignments")
public class Assignment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String section, subjectCode, subjectName, type;
    private Integer credits, maxMarks, obtainedMarks;
    @Column(length = 2000)
    private String details;

    public Long getId(){ return id; }
    public void setIdForApi(Long id){ this.id=id; }
    public String getSection(){ return section; } public void setSection(String v){ section=v; }
    public String getSubjectCode(){ return subjectCode; } public void setSubjectCode(String v){ subjectCode=v; }
    public String getSubjectName(){ return subjectName; } public void setSubjectName(String v){ subjectName=v; }
    public String getType(){ return type; } public void setType(String v){ type=v; }
    public Integer getCredits(){ return credits; } public void setCredits(Integer v){ credits=v; }
    public Integer getMaxMarks(){ return maxMarks; } public void setMaxMarks(Integer v){ maxMarks=v; }
    public Integer getObtainedMarks(){ return obtainedMarks; } public void setObtainedMarks(Integer v){ obtainedMarks=v; }
    public String getDetails(){ return details; } public void setDetails(String v){ details=v; }
}
