package com.college.erp.model.entity;

import jakarta.persistence.*;

@Entity @Table(name="users", uniqueConstraints=@UniqueConstraint(columnNames="username"))
public class UserAccount {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String username;
    @Column(nullable=false) private String fullName;
    @Column(nullable=false) private String passwordHash;
    @Column(nullable=false) private String role;
    private String email; private String department;
    public Long getId(){return id;} public String getUsername(){return username;} public void setUsername(String v){username=v;}
    public String getFullName(){return fullName;} public void setFullName(String v){fullName=v;} public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
    public String getRole(){return role;} public void setRole(String v){role=v;} public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getDepartment(){return department;} public void setDepartment(String v){department=v;}
}
