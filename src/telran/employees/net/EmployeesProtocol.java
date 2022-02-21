package telran.employees.net;

import telran.employees.dto.Employee;
import telran.employees.dto.ReturnCode;
import telran.employees.services.EmployeesMethods;
import telran.net.ApplProtocol;
import telran.net.dto.Request;
import telran.net.dto.Response;
import telran.net.dto.ResponseCode;
import java.util.*;
import static telran.employees.net.dto.ApiConstants.*;

import java.io.Serializable;

public class EmployeesProtocol implements ApplProtocol {
	public EmployeesProtocol(EmployeesMethods employees) {
		this.employees = employees;
	}

	private EmployeesMethods employees;

	@Override
	public Response getResponse(Request request) {
		switch (request.requestType) {
		case ADD_EMPLOYEE:
			return _employee_add(request.requestData);
		case GET_EMPLOYEES:
			return _get(request.requestData);
		case REMOVE_EMPLOYEE:
			return _employee_remove(request.requestData);
		case UPDATE_SALARY:
			return _employee_update_salary(request.requestData);
		case UPDATE_DEPARTMENT:
			return _employee_update_department(request.requestData);
		case GET_EMPLOYEE:
			return _employee_get(request.requestData);
		case GET_EMPLOYEE_BY_AGE:
			return _employee_get_filter_age(request.requestData);
		case GET_EMPLOYEE_BY_SALARY:
			return _employee_get_filter_salary(request.requestData);
		case GET_EMPLOYEE_BY_DEPARTMENT:
			return _employee_get_filter_department(request.requestData);
		case GET_EMPLOYEE_BY_SALARY_DEPARTMENT:
			return _employee_get_filter_salary_department(request.requestData);

		default:
			return new Response(ResponseCode.UNKNOWN_REQUEST, request.requestType + " not implemented");
		}

	}

	private Response _employee_remove(Serializable requestData) {
		try {
			ReturnCode responseData = employees.removeEmployee((Long) requestData);
			return new Response(ResponseCode.OK, responseData);
		} catch (Exception e) {
			return new Response(ResponseCode.WRONG_REQUEST_DATA, e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private Response _employee_update_salary(Serializable requestData) {
		try {
			Long id = Long.parseLong(((HashMap<String, String>) requestData).get("id"));
			int newSalary = Integer.parseInt(((HashMap<String, String>) requestData).get("newSalary"));
			ReturnCode responseData = employees.updateSalary(id, newSalary);
			return new Response(ResponseCode.OK, responseData);
		} catch (Exception e) {
			return new Response(ResponseCode.WRONG_REQUEST_DATA, e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private Response _employee_update_department(Serializable requestData) {
		try {
			Long id = Long.parseLong(((HashMap<String, String>) requestData).get("id"));
			String newDepartment = ((HashMap<String, String>) requestData).get("newDepartment");
			ReturnCode responseData = employees.updateDepartment(id, newDepartment);
			return new Response(ResponseCode.OK, responseData);
		} catch (Exception e) {
			return new Response(ResponseCode.WRONG_REQUEST_DATA, e.getMessage());
		}
	}

	private Response _employee_get(Serializable requestData) {
		try {
			Long id = (Long) requestData;
			Employee responseData = employees.getEmployee(id);
			return new Response(ResponseCode.OK, responseData);
		} catch (Exception e) {
			return new Response(ResponseCode.WRONG_REQUEST_DATA, e.getMessage());
		}
	}

	private Response _employee_get_filter_age(Serializable requestData) {
		try {
			int ageFrom = ((int[]) requestData)[0];
			int ageTo = ((int[]) requestData)[1];
			List<Employee> responseData = new ArrayList<>();
			employees.getEmployeesByAge(ageFrom, ageTo).forEach(responseData::add);
			return new Response(ResponseCode.OK, (Serializable) responseData);
		} catch (Exception e) {
			return new Response(ResponseCode.WRONG_REQUEST_DATA, e.getMessage());
		}
	}

	private Response _employee_get_filter_salary(Serializable requestData) {
		try {
			int salaryFrom = ((int[]) requestData)[0];
			int salaryTo = ((int[]) requestData)[1];
			List<Employee> responseData = new ArrayList<>();
			employees.getEmployeesBySalary(salaryFrom, salaryTo).forEach(responseData::add);
			return new Response(ResponseCode.OK, (Serializable) responseData);
		} catch (Exception e) {
			return new Response(ResponseCode.WRONG_REQUEST_DATA, e.getMessage());
		}
	}

	private Response _employee_get_filter_department(Serializable requestData) {
		try {
			String department = (String) requestData;
			List<Employee> responseData = new ArrayList<>();
			employees.getEmployeesByDepartment(department).forEach(responseData::add);
			return new Response(ResponseCode.OK, (Serializable) responseData);
		} catch (Exception e) {
			return new Response(ResponseCode.WRONG_REQUEST_DATA, e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private Response _employee_get_filter_salary_department(Serializable requestData) {
		try {
			String department = ((HashMap<String, String>) requestData).get("department");
			int salaryFrom = Integer.parseInt(((HashMap<String, String>) requestData).get("salaryFrom"));
			int salaryTo = Integer.parseInt(((HashMap<String, String>) requestData).get("salaryTo"));
			List<Employee> responseData = new ArrayList<>();
			employees.getEmployeesByDepartmentAndSalary(department, salaryFrom, salaryTo).forEach(responseData::add);
			return new Response(ResponseCode.OK, (Serializable) responseData);
		} catch (Exception e) {
			return new Response(ResponseCode.WRONG_REQUEST_DATA, e.getMessage());
		}
	}

	private Response _get(Serializable requestData) {
		try {
			List<Employee> responseData = new ArrayList<>();
			employees.getAllEmployees().forEach(responseData::add);

			return new Response(ResponseCode.OK, (Serializable) responseData);
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
