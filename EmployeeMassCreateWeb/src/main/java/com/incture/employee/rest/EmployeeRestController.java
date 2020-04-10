/**
 * 
 */
package com.incture.employee.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.incture.employee.dto.EmployeeStagingDto;
import com.incture.employee.dto.MessageDto;
import com.incture.employee.service.EmployeeStagingLocal;

@RestController
@RequestMapping(value = "/empployee", produces = "application/json")
public class EmployeeRestController {

	@Autowired
	private EmployeeStagingLocal employeeStagingLocal;

	@RequestMapping(value = "/uploadEmployeeData", method = RequestMethod.POST)
	public MessageDto uploadEmployeeStagingRecords(@RequestBody EmployeeStagingDto employeeStagingDto) {
		return employeeStagingLocal.saveEmployeeReecords(employeeStagingDto);
	}

	@RequestMapping(value = "/getData/{reqId}", method = RequestMethod.GET)
	public List<EmployeeStagingDto> getEmployeeStagingRecords(@PathVariable("reqId") int reqId) {
		return employeeStagingLocal.getEmployeeData(reqId);
	}

	@RequestMapping(value = "/updateStatus", method = RequestMethod.PUT)
	public MessageDto updateEmployeeStatus(List<EmployeeStagingDto> employeedtolist) {
		return employeeStagingLocal.updateEmployeeData(employeedtolist);
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		return "test";
	}

}
