package com.csvdb.repository;

import javax.persistence.Id;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csvdb.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Id> {

}
