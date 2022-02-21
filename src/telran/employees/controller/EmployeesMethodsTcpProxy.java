package telran.employees.controller;

import telran.employees.dto.Employee;
import telran.employees.dto.ReturnCode;
import telran.employees.services.EmployeesMethods;
import telran.net.Sender;
import static telran.employees.net.dto.ApiConstants.*;
public class EmployeesMethodsTcpProxy implements EmployeesMethods {
private Sender sender;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmployeesMethodsTcpProxy(Sender sender) {
		this.sender = sender;
	}

	@Override
	public ReturnCode addEmployee(Employee empl) {
		
		return sender.send(ADD_EMPLOYEE, empl);
	}

	@Override
	public ReturnCode removeEmployee(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Employee> getAllEmployees() {
		
		return sender.send(GET_EMPLOYEES, "");
	}

	@Override
	public Employee getEmployee(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Employee> getEmployeesByAge(int ageFrom, int ageTo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Employee> getEmployeesByDepartment(String department) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Employee> getEmployeesByDepartmentAndSalary(String department, int salaryFrom, int salaryTo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnCode updateSalary(long id, int newSalary) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnCode updateDepartment(long id, String newDepartment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void restore() {
		// TODO Auto-generated method stub

	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

}
