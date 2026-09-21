package com.college.erp.model.entity;
import jakarta.persistence.*;
@Entity @Table(name="faculty")
public class Faculty { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id; @Column(unique=true,nullable=false) String username; String name,department,email,status; public Long getId(){return id;} public void setIdForApi(Long v){id=v;} public String getUsername(){return username;} public void setUsername(String v){username=v;} public String getName(){return name;} public void setName(String v){name=v;} public String getDepartment(){return department;} public void setDepartment(String v){department=v;} public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getStatus(){return status;} public void setStatus(String v){status=v;} }
