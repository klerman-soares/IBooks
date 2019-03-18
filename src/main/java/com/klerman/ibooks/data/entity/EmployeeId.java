package com.klerman.ibooks.data.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class EmployeeId implements Serializable {
 
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "company_id")
    private Company company;
 
    @Column(name = "employee_number")
    private Long employeeNumber;
 
    public EmployeeId() {
    }
 
    public EmployeeId(Company company, Long employeeId) {
        this.company = company;
        this.employeeNumber = employeeId;
    }
 
    public Company getCompanyId() {
        return company;
    }
 
    public Long getEmployeeNumber() {
        return employeeNumber;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeId)) return false;
        EmployeeId that = (EmployeeId) o;
        return Objects.equals(getCompanyId(), that.getCompanyId()) &&
                Objects.equals(getEmployeeNumber(), that.getEmployeeNumber());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getCompanyId(), getEmployeeNumber());
    }
}
