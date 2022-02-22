package telran.employees.controller;

import telran.employees.dto.Employee;
import telran.employees.dto.ReturnCode;
import telran.employees.services.EmployeesMethods;
import telran.net.Sender;
import static telran.employees.net.dto.ApiConstants.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
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
		
		return sender.send(REMOVE_EMPLOYEE, id);
	}

	@Override
	public Iterable<Employee> getAllEmployees() {
		
		return sender.send(GET_EMPLOYEES, "");
	}

	@Override
	public Employee getEmployee(long id) {
		
		return sender.send(GET_EMPLOYEE, id);
	}

	@Override
	public Iterable<Employee> getEmployeesByAge(int ageFrom, int ageTo) {
		int[] age = {ageFrom, ageTo};
		return sender.send(GET_EMPLOYEE_BY_AGE, age);
	}

	@Override
	public Iterable<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {
		int[] salary = {salaryFrom, salaryTo};
		return sender.send(GET_EMPLOYEE_BY_SALARY, salary);
	}

	@Override
	public Iterable<Employee> getEmployeesByDepartment(String department) {
		
		return sender.send(GET_EMPLOYEE_BY_DEPARTMENT, department);
	}

	@Override
	public Iterable<Employee> getEmployeesByDepartmentAndSalary(String department, int salaryFrom, int salaryTo) {
		/* V.R. 
		HashMap<String, Integer[]> employeesBySalaryInDepInfo = new HashMap<>();
		Integer[] salaryInterval = {salaryFrom, salaryTo};
		employeesBySalaryInDepInfo.put(department, salaryInterval);
		return sender.send(GET_EMPLOYEES_BY_DEPARTMENT_AND_SALARY, employeesBySalaryInDepInfo);
		 * 
		 */
		Map<String, String> map = new HashMap<>();
		map.put("department", department);
		map.put("salaryFrom", String.valueOf(salaryFrom));
		map.put("salaryTo", String.valueOf(salaryTo));
		return sender.send(GET_EMPLOYEE_BY_SALARY_DEPARTMENT, (Serializable) map);
	}

	@Override
	public ReturnCode updateSalary(long id, int newSalary) {
		Map<String, String> map = new HashMap<>();
		map.put("id", String.valueOf(id));
		map.put("newSalary", String.valueOf(newSalary));
		return sender.send(UPDATE_SALARY, (Serializable) map);
	}

	@Override
	public ReturnCode updateDepartment(long id, String newDepartment) {
		Map<String, String> map = new HashMap<>();
		map.put("id", String.valueOf(id));
		map.put("newDepartment", String.valueOf(newDepartment));
		return sender.send(UPDATE_DEPARTMENT, (Serializable) map);
	}

	@Override
	public void restore() {
		

	}

	@Override
	public void save() {
		

	}
/* V.R. The following exception appears if to select 11 (Exit) in
 * Main menu. Can you explain why and repair the problem?
java.net.SocketException: Connection reset
	at java.base/sun.nio.ch.NioSocketImpl.implRead(NioSocketImpl.java:323)
	at java.base/sun.nio.ch.NioSocketImpl.read(NioSocketImpl.java:350)
	at java.base/sun.nio.ch.NioSocketImpl$1.read(NioSocketImpl.java:803)
	at java.base/java.net.Socket$SocketInputStream.read(Socket.java:966)
	at java.base/java.net.Socket$SocketInputStream.read(Socket.java:961)
	at java.base/java.io.ObjectInputStream$PeekInputStream.peek(ObjectInputStream.java:2853)
	at java.base/java.io.ObjectInputStream$BlockDataInputStream.peek(ObjectInputStream.java:3180)
	at java.base/java.io.ObjectInputStream$BlockDataInputStream.peekByte(ObjectInputStream.java:3190)
	at java.base/java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1693)
	at java.base/java.io.ObjectInputStream.readObject(ObjectInputStream.java:514)
	at java.base/java.io.ObjectInputStream.readObject(ObjectInputStream.java:472)
	at telran.net.TcpClientServer.run(TcpClientServer.java:21)
	at telran.net.TcpServer.run(TcpServer.java:21)
	at telran.employees.net.EmployeesServerAppl.main(EmployeesServerAppl.java:11)
* 
 */
}
