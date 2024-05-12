package com.srsanjay.soapdemo.service;

import com.srsanjay.soapdemo.entity.Employee;
import com.srsanjay.soapdemo.exception.EmployeeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    public enum Status {
        SUCCESS, FAILURE
    }

    private static List<Employee> employees = new ArrayList<>();

    static {
        employees.add(new Employee(1, "John", "Doe"));
        employees.add(new Employee(2, "Mary", "Jane"));
        employees.add(new Employee(3, "Peter", "Pan"));
    }

    public Employee getEmployeeById(Integer id) {
        return employees.stream().filter(e->e.getId().equals(id))
                .findFirst().orElseThrow(()->new EmployeeNotFoundException("Employee not found with id " + id));
    }

    public List<Employee> getAllEmployees() {
        return employees;
    }

    public Status deleteEmployeeById(Integer id) {
        for (int i=0; i<employees.size(); i++) {
            if (employees.get(i).getId().equals(id)) {
                employees.remove(i);
                return Status.SUCCESS;
            }
        }
        return Status.FAILURE;
    }

}
