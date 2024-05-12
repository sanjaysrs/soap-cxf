package com.srsanjay.soapdemo.endpoint;

import com.srsanjay.employees.*;
import com.srsanjay.soapdemo.entity.Employee;
import com.srsanjay.soapdemo.service.EmployeeService;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class EmployeeEndPoint {

    private EmployeeService employeeService;

    public EmployeeEndPoint(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PayloadRoot(namespace = "http://srsanjay.com/employees", localPart = "GetEmployeeRequest")
    @ResponsePayload
    public GetEmployeeResponse handleGetEmployeeDetailsRequest(
            @RequestPayload GetEmployeeRequest request) {

        Employee employee = employeeService.getEmployeeById(request.getId());
        return getEmployeeResponse(employee);
    }

    @PayloadRoot(namespace = "http://srsanjay.com/employees", localPart = "GetAllEmployeesRequest")
    @ResponsePayload
    public GetAllEmployeesResponse handleGetAllEmployeesRequest(
            @RequestPayload GetAllEmployeesRequest request) {

        GetAllEmployeesResponse response = new GetAllEmployeesResponse();

        employeeService.getAllEmployees()
                .forEach(e->response.getEmployeeDetails().add(getEmployeeDetails(e)));

        return response;
    }

    @PayloadRoot(namespace = "http://srsanjay.com/employees", localPart = "DeleteEmployeeRequest")
    @ResponsePayload
    public DeleteEmployeeResponse handleDeleteEmployeeRequest(
            @RequestPayload DeleteEmployeeRequest request) {

        DeleteEmployeeResponse response = new DeleteEmployeeResponse();
        if (employeeService.deleteEmployeeById(request.getId()).equals(EmployeeService.Status.SUCCESS))
            response.setStatus(Status.SUCCESS);
        else
            response.setStatus(Status.FAILURE);
        return response;
    }

    private GetEmployeeResponse getEmployeeResponse(Employee employee) {

        EmployeeDetails employeeDetails = getEmployeeDetails(employee);
        GetEmployeeResponse response = new GetEmployeeResponse();
        response.setEmployeeDetails(employeeDetails);

        return response;
    }

    private EmployeeDetails getEmployeeDetails(Employee employee) {
        EmployeeDetails employeeDetails = new EmployeeDetails();
        employeeDetails.setId(employee.getId());
        employeeDetails.setFirstName(employee.getFirstName());
        employeeDetails.setLastName(employee.getLastName());
        return employeeDetails;
    }

}
