package telran.employees.net;

import telran.employees.dto.Employee;
import telran.employees.dto.ReturnCode;
import telran.employees.services.EmployeesMethods;
import telran.net.ApplProtocol;
import telran.net.dto.Request;
import telran.net.dto.Response;
import telran.net.dto.ResponseCode;
import java.util.*;
import static telran.employees.net.dto.ApiConstants.*
;

import java.io.Serializable;public class EmployeesProtocol implements ApplProtocol {
public EmployeesProtocol(EmployeesMethods employees) {
		this.employees = employees;
	}

private EmployeesMethods employees;

	@Override
	public Response getResponse(Request request) {
		switch(request.requestType) {
		case ADD_EMPLOYEE: return _employee_add(request.requestData);
		case GET_EMPLOYEES: return _get(request.requestData);
		//TODO
		default: return new Response(ResponseCode.UNKNOWN_REQUEST,
				request.requestType + " not implemented");
		}
		
	}

	private Response _get(Serializable requestData) {
		try {
			List<Employee> responseData = new ArrayList<>();
			employees.getAllEmployees().forEach(responseData::add);
			
			return new Response(ResponseCode.OK, (Serializable)responseData);
		} catch (Exception e) {
			return new Response(ResponseCode.WRONG_REQUEST_DATA, e.getMessage());
		}
	}

	private Response _employee_add(Serializable requestData) {
		try {
			Employee employee = (Employee) requestData;
			ReturnCode responseData = employees.addEmployee(employee);
			return new Response(ResponseCode.OK, responseData);
		} catch (Exception e) {
			return new Response(ResponseCode.WRONG_REQUEST_DATA, e.getMessage());
		}
	}

}
