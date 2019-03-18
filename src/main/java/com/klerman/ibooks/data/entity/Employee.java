package com.klerman.ibooks.data.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Employee")
@Table(name = "employee")
public class Employee {
 
    @EmbeddedId
    private EmployeeId id;
 
    private String name;
 
    public EmployeeId getId() {
        return id;
    }
 
    public void setId(EmployeeId id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
}
