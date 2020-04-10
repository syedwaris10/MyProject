package com.incture.employee.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.employee.constants.BankDetailsTemplate;
import com.incture.employee.constants.EmployeePersonalDetailsTemplate;
import com.incture.employee.constants.EmploymentDetailsSheetTemplate;
import com.incture.employee.constants.IdentificationdetailsTemplate;
import com.incture.employee.dao.EmployeeStagingDaoLocal;
import com.incture.employee.dao.LookUpDaoLocal;
import com.incture.employee.dto.EmailNotificationDto;
import com.incture.employee.dto.EmployeeStagingDto;
import com.incture.employee.dto.LookUpDto;
import com.incture.employee.dto.MessageDto;
import com.incture.employee.exceptions.InvalidInputFault;
import com.incture.employee.util.CommonUtils;
import com.incture.employee.util.ServiceUtil;

@Service
public class EmployeeStaging implements EmployeeStagingLocal {

	@Autowired
	private EmployeeStagingDaoLocal employeeStagingDaoLocal;
	@Autowired
	private LookUpDaoLocal lookUpDaoLocal;
	@Autowired
	private EmailNotificationLocal emailNotificationLocal;

	/**
	 * @param employeedto
	 */
	@Override
	public MessageDto saveEmployeeReecords(EmployeeStagingDto employeedto) {

		if (!ServiceUtil.isEmpty(employeedto)) {
			int requestId = CommonUtils.getRandomNumber();
			Map<String, List<String>> lookupMap = new HashMap<>();
			List<EmployeeStagingDto> employeedtolist = new ArrayList<>();
			try {
				Workbook workbook = CommonUtils.getWorkbookObject(employeedto.getFileFormat(), employeedto.getBase64());
				if (ServiceUtil.isEmpty(workbook)) {
					throw new InvalidInputFault("Invalid template!");
				}
				Sheet employeePersonalDetailsSheet = workbook.getSheetAt(0);
				Sheet employmentDetailsSheet = workbook.getSheetAt(1);
				Sheet employeeIdentificationSheet = workbook.getSheetAt(2);
				Sheet employeeBankDetailsSheet = workbook.getSheetAt(3);

				boolean isEmployeePersonalDetailsSheetValid = this
						.validateEmployeePersonalDetailsSheetTemplate(employeePersonalDetailsSheet);

				boolean isEmploymentDetailsSheetValid = this
						.validateEmploymentDetailsSheetTemplate(employmentDetailsSheet);

				boolean isEmployeeIdentificationSheetValid = this
						.validateEmployeeIdentificationSheetTemplate(employeeIdentificationSheet);

				boolean isEmployeeBankDetailsSheetValid = this
						.validateEmployeeBankDetailsSheetTemplate(employeeBankDetailsSheet);

				if (isEmployeePersonalDetailsSheetValid && isEmploymentDetailsSheetValid
						&& isEmployeeIdentificationSheetValid && isEmployeeBankDetailsSheetValid) {

					// read look up table
					List<LookUpDto> lookupList = lookUpDaoLocal.getEmployeeLookUp();
					getLookUpMap(lookupMap, lookupList);

					setEmployeePersonalDataInStaging(employeePersonalDetailsSheet, employeedtolist, lookupMap);

					setEmploymentDetailsInStaging(employmentDetailsSheet, employeedtolist, lookupMap);

					setIdentificationDetailsInStaging(employeeIdentificationSheet, employeedtolist, lookupMap);

					setBankDetailsInStaging(employeeBankDetailsSheet, employeedtolist, lookupMap);

					for (EmployeeStagingDto dto : employeedtolist) {
						dto.setRequestId(requestId);
						employeeStagingDaoLocal.saveEmployee(dto);
					}

					// send Email
					sendEmail(requestId);
				} else {
					throw new InvalidInputFault("Invalid template!");
				}

			} catch (IOException | InvalidInputFault | ParseException | MessagingException e) {
				return CommonUtils.getFailedMessage(null, e.getMessage());
			}
		}
		return CommonUtils.getSuccessMessage(null);
	}

	/**
	 * @throws AddressException
	 * @throws MessagingException
	 */
	private void sendEmail(int requestId) throws AddressException, MessagingException {
		EmailNotificationDto emailDto = new EmailNotificationDto();
		emailDto.setBody("");
		emailDto.setSubject("Email Notification | Request Id - " + requestId);
		emailNotificationLocal.sendEmail(emailDto);
	}

	/**
	 * creates a map for lookup
	 * 
	 * @param lookupMap
	 * @param lookupList
	 */
	private void getLookUpMap(Map<String, List<String>> lookupMap, List<LookUpDto> lookupList) {
		if (!ServiceUtil.isEmpty(lookupList)) {
			for (LookUpDto dto : lookupList) {
				if (!ServiceUtil.isEmpty(dto)) {
					String key = dto.getLookUp();
					if (lookupMap.containsKey(key)) {
						List<String> valueList = lookupMap.get(key);
						valueList.add(dto.getLookUpValue());
						lookupMap.put(dto.getLookUp(), valueList);
					} else {
						List<String> tempList = new ArrayList<String>();
						tempList.add(dto.getLookUpValue());
						lookupMap.put(dto.getLookUp(), tempList);
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param employeeBankDetailsSheet
	 * @return
	 * @throws InvalidInputFault
	 */
	private boolean validateEmployeeBankDetailsSheetTemplate(Sheet employeeBankDetailsSheet) throws InvalidInputFault {

		if (!ServiceUtil.isEmpty(employeeBankDetailsSheet)) {
			Row row = employeeBankDetailsSheet.getRow(0);

			validateRow(row);

			Iterator<Cell> cell = row.cellIterator();
			validateCellIterator(cell);

			String cellValue = "";
			while (cell.hasNext()) {

				Cell nextCell = cell.next();
				validateCell(nextCell);
				int colIndex = nextCell.getColumnIndex();
				switch (colIndex) {
				case 0:
					setCellStringType(nextCell);
					cellValue = checkCellAndGetValue(nextCell);
					validateCellData(cellValue, BankDetailsTemplate.EmployeeCode.name());
					break;

				case 1:
					setCellStringType(nextCell);
					cellValue = checkCellAndGetValue(nextCell);
					validateCellData(cellValue, BankDetailsTemplate.AccountNumber.name());
					break;

				case 2:
					setCellStringType(nextCell);
					cellValue = checkCellAndGetValue(nextCell);
					validateCellData(cellValue, BankDetailsTemplate.BankName.name());
					break;

				case 3:
					setCellStringType(nextCell);
					cellValue = checkCellAndGetValue(nextCell);
					validateCellData(cellValue, BankDetailsTemplate.AccountType.name());
					break;

				}
			}
		}
		return true;

	}

	/**
	 * @param nextCell
	 * @throws InvalidInputFault
	 */
	private void validateCell(Cell nextCell) throws InvalidInputFault {
		if (ServiceUtil.isEmpty(nextCell)) {
			throw new InvalidInputFault("Invalid Template.");
		}
	}

	/**
	 * 
	 * @param employeeIdentificationSheet
	 * @return
	 * @throws InvalidInputFault
	 */
	private boolean validateEmployeeIdentificationSheetTemplate(Sheet employeeIdentificationSheet)
			throws InvalidInputFault {

		if (!ServiceUtil.isEmpty(employeeIdentificationSheet)) {
			Row row = employeeIdentificationSheet.getRow(0);

			validateRow(row);
			Iterator<Cell> cell = row.cellIterator();

			validateCellIterator(cell);
			String cellValue = "";
			while (cell.hasNext()) {
				Cell nextCell = cell.next();

				validateCell(nextCell);
				int colIndex = nextCell.getColumnIndex();
				switch (colIndex) {

				case 0:
					setCellStringType(nextCell);
					cellValue = checkCellAndGetValue(nextCell);
					validateCellData(cellValue, IdentificationdetailsTemplate.EmployeeCode.name());
					break;

				case 1:
					setCellStringType(nextCell);
					cellValue = checkCellAndGetValue(nextCell);
					validateCellData(cellValue, IdentificationdetailsTemplate.IdentificationType.name());
					break;

				case 2:
					setCellStringType(nextCell);
					cellValue = checkCellAndGetValue(nextCell);
					validateCellData(cellValue, IdentificationdetailsTemplate.IdentificationNumber.name());
					break;

				}
			}
		}
		return true;

	}

	/**
	 * @param cell
	 * @throws InvalidInputFault
	 */
	private void validateCellIterator(Iterator<Cell> cell) throws InvalidInputFault {
		if (ServiceUtil.isEmpty(cell)) {
			throw new InvalidInputFault("Invalid Template.");
		}
	}

	/**
	 * @param row
	 * @throws InvalidInputFault
	 */
	private void validateRow(Row row) throws InvalidInputFault {
		if (ServiceUtil.isEmpty(row)) {
			throw new InvalidInputFault("Invalid Template.");
		}
	}

	/**
	 * 
	 * @param employmentDetailsSheet
	 * @return
	 * @throws InvalidInputFault
	 */
	private boolean validateEmploymentDetailsSheetTemplate(Sheet employmentDetailsSheet) throws InvalidInputFault {

		if (!ServiceUtil.isEmpty(employmentDetailsSheet)) {
			Row row = employmentDetailsSheet.getRow(0);

			validateRow(row);
			Iterator<Cell> cell = row.cellIterator();

			validateCellIterator(cell);
			String cellValue = "";
			while (cell.hasNext()) {
				Cell nextCell = cell.next();
				validateCell(nextCell);
				int colIndex = nextCell.getColumnIndex();
				switch (colIndex) {

				case 0:
					setCellStringType(nextCell);
					cellValue = checkCellAndGetValue(nextCell);
					validateCellData(cellValue, EmploymentDetailsSheetTemplate.EmployeeCode.name());
					break;

				case 1:
					setCellStringType(nextCell);
					cellValue = checkCellAndGetValue(nextCell);
					validateCellData(cellValue, EmploymentDetailsSheetTemplate.Company.name());
					break;

				case 2:
					setCellStringType(nextCell);
					cellValue = checkCellAndGetValue(nextCell);
					validateCellData(cellValue, EmploymentDetailsSheetTemplate.Location.name());
					break;

				case 3:
					setCellStringType(nextCell);
					cellValue = checkCellAndGetValue(nextCell);
					validateCellData(cellValue, EmploymentDetailsSheetTemplate.Department.name());
					break;

				case 4:
					setCellStringType(nextCell);
					cellValue = checkCellAndGetValue(nextCell);
					validateCellData(cellValue, EmploymentDetailsSheetTemplate.EmploymentType.name());
					break;

				case 5:
					setCellStringType(nextCell);
					cellValue = checkCellAndGetValue(nextCell);
					validateCellData(cellValue, EmploymentDetailsSheetTemplate.Designation.name());
					break;

				case 6:
					setCellStringType(nextCell);
					cellValue = checkCellAndGetValue(nextCell);
					validateCellData(cellValue, EmploymentDetailsSheetTemplate.PFCode.name());
					break;
				}
			}
		}
		return true;
	}

	/**
	 * 
	 * @param employeedto
	 * @throws ParseException
	 * @throws InvalidInputFault
	 */
	private void setEmployeePersonalDataInStaging(Sheet sheet, List<EmployeeStagingDto> dtolist,
			Map<String, List<String>> lookupMap) throws ParseException, InvalidInputFault {

		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			int rowIndex = row.getRowNum();
			if (rowIndex > 0) {
				if (!ServiceUtil.isEmpty(row)) {
					String cellValue = "";
					if (rowIndex > 0) {
						EmployeeStagingDto dto = new EmployeeStagingDto();
						Iterator<Cell> cell = row.cellIterator();
						while (cell.hasNext()) {
							Cell nextCell = cell.next();
							int colIndex = nextCell.getColumnIndex();
							switch (colIndex) {

							case 0:
								setCellStringType(nextCell);
								cellValue = checkCellAndGetValue(nextCell);
								if (ServiceUtil.isEmpty(cellValue)) {
									cellValue = CommonUtils.createNewEmployeeCode();
								}
								dto.setEmployeeCode(cellValue);
								break;

							case 1:
								setCellStringType(nextCell);
								cellValue = checkCellAndGetValue(nextCell);
								dto.setName(cellValue);
								break;

							case 2:
								setCellStringType(nextCell);
								cellValue = checkCellAndGetValue(nextCell);
								dto.setStatus(cellValue);
								break;

							case 3:
								setCellStringType(nextCell);
								cellValue = checkCellAndGetValue(nextCell);
								if (!CommonUtils.isEmailValid(cellValue)) {
									throw new InvalidInputFault("Invalid email Id for row - " + rowIndex + 1);
								}
								dto.setEmail(cellValue);
								break;

							case 4:
								setCellStringType(nextCell);
								cellValue = checkCellAndGetValue(nextCell);
								dto.setHireDate(CommonUtils.getDate(cellValue));
								break;

							case 5:
								setCellStringType(nextCell);
								cellValue = checkCellAndGetValue(nextCell);
								dto.setDob(CommonUtils.getDate(cellValue));
								break;

							case 6:
								setCellStringType(nextCell);
								cellValue = checkCellAndGetValue(nextCell);
								validateWithlookUp(EmployeePersonalDetailsTemplate.Gender.name(), cellValue, lookupMap);
								dto.setGender(cellValue);
								break;

							case 7:
								setCellStringType(nextCell);
								cellValue = checkCellAndGetValue(nextCell);
								validateWithlookUp(EmployeePersonalDetailsTemplate.MaritalStatus.name(), cellValue,
										lookupMap);
								dto.setMaritalStatus(cellValue);
								break;

							case 8:
								setCellStringType(nextCell);
								cellValue = checkCellAndGetValue(nextCell);
								dto.setAge(Integer.parseInt(cellValue));
								break;
							}
						}
						dtolist.add(dto);
					}
				}
			}
		}
	}

	private void setEmploymentDetailsInStaging(Sheet sheet, List<EmployeeStagingDto> dtolist,
			Map<String, List<String>> lookupMap) throws InvalidInputFault {

		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			int rowIndex = row.getRowNum();
			if (rowIndex > 0) {
				if (!ServiceUtil.isEmpty(row)) {
					String cellValue = "";
					if (rowIndex > 0) {
						EmployeeStagingDto dto = dtolist.get(rowIndex - 1);
						Iterator<Cell> cell = row.cellIterator();
						while (cell.hasNext()) {
							Cell nextCell = cell.next();
							int colIndex = nextCell.getColumnIndex();
							switch (colIndex) {

							case 1:
								setCellStringType(nextCell);
								cellValue = checkCellAndGetValue(nextCell);
								dto.setCompany(cellValue);
								break;

							case 2:
								setCellStringType(nextCell);
								cellValue = checkCellAndGetValue(nextCell);
								dto.setLocation(cellValue);
								break;

							case 3:
								setCellStringType(nextCell);
								cellValue = checkCellAndGetValue(nextCell);
								validateWithlookUp(EmploymentDetailsSheetTemplate.Department.name(), cellValue,
										lookupMap);
								dto.setDepartment(cellValue);
								break;

							case 4:
								setCellStringType(nextCell);
								cellValue = checkCellAndGetValue(nextCell);
								validateWithlookUp(EmploymentDetailsSheetTemplate.EmploymentType.name(), cellValue,
										lookupMap);
								dto.setEmploymentType(cellValue);
								break;

							case 5:
								setCellStringType(nextCell);
								cellValue = checkCellAndGetValue(nextCell);
								validateWithlookUp(EmploymentDetailsSheetTemplate.Designation.name(), cellValue,
										lookupMap);
								dto.setDesignation(cellValue);
								break;

							case 6:
								setCellStringType(nextCell);
								cellValue = checkCellAndGetValue(nextCell);
								dto.setPfCode(cellValue);
								break;
							}

						}

					}
				}
			}
		}
	}

	private void setIdentificationDetailsInStaging(Sheet sheet, List<EmployeeStagingDto> dtolist,
			Map<String, List<String>> lookupMap) throws InvalidInputFault {

		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (!ServiceUtil.isEmpty(row)) {
				String cellValue = "";
				int rowIndex = row.getRowNum();
				if (rowIndex > 0) {
					Iterator<Cell> cell = row.cellIterator();
					EmployeeStagingDto dto = dtolist.get(rowIndex - 1);
					while (cell.hasNext()) {
						Cell nextCell = cell.next();
						int colIndex = nextCell.getColumnIndex();
						switch (colIndex) {

						case 1:
							setCellStringType(nextCell);
							cellValue = checkCellAndGetValue(nextCell);
							validateWithlookUp(IdentificationdetailsTemplate.IdentificationType.name(), cellValue,
									lookupMap);
							dto.setIdentificationType(cellValue);
							break;

						case 2:
							setCellStringType(nextCell);
							cellValue = checkCellAndGetValue(nextCell);
							dto.setIdentificationNumber(cellValue);
							break;
						}
					}
				}
			}
		}
	}

	private void setBankDetailsInStaging(Sheet sheet, List<EmployeeStagingDto> dtolist,
			Map<String, List<String>> lookupMap) throws InvalidInputFault {

		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (!ServiceUtil.isEmpty(row)) {
				String cellValue = "";
				int rowIndex = row.getRowNum();
				if (rowIndex > 0) {
					Iterator<Cell> cell = row.cellIterator();
					EmployeeStagingDto dto = dtolist.get(rowIndex - 1);
					while (cell.hasNext()) {
						Cell nextCell = cell.next();
						int colIndex = nextCell.getColumnIndex();
						switch (colIndex) {

						case 1:
							setCellStringType(nextCell);
							cellValue = checkCellAndGetValue(nextCell);
							dto.setAccountNumber(cellValue);
							break;

						case 2:
							setCellStringType(nextCell);
							cellValue = checkCellAndGetValue(nextCell);
							validateWithlookUp(BankDetailsTemplate.BankName.name(), cellValue, lookupMap);
							dto.setBankName(cellValue);
							break;

						case 3:
							setCellStringType(nextCell);
							cellValue = checkCellAndGetValue(nextCell);
							dto.setAccountType(cellValue);
							break;
						}
					}
				}
			}
		}
	}

	private void validateWithlookUp(String key, String cellValue, Map<String, List<String>> lookupMap)
			throws InvalidInputFault {
		if (!ServiceUtil.isEmpty(lookupMap) && lookupMap.containsKey(key)) {
			List<String> valueList = lookupMap.get(key);
			if (ServiceUtil.isEmpty(cellValue)) {
				throw new InvalidInputFault("Please enter correct value or maintain in look up table for" + key);
			}
			if (!ServiceUtil.isEmpty(cellValue) && !valueList.contains(cellValue.trim())) {
				throw new InvalidInputFault("Please enter correct value or maintain in look up table for" + key);
			}
		}
	}

	/**
	 * It validates employee personal data sheet template
	 * 
	 * @param sheet
	 * @return
	 * @throws InvalidInputFault
	 */
	private boolean validateEmployeePersonalDetailsSheetTemplate(Sheet sheet) throws InvalidInputFault {

		if (!ServiceUtil.isEmpty(sheet)) {
			Row row = sheet.getRow(0);

			validateRow(row);
			Iterator<Cell> cell = row.cellIterator();

			validateCellIterator(cell);
			String cellValue = "";
			while (cell.hasNext()) {
				Cell nextCell = cell.next();
				validateCell(nextCell);
				int colIndex = nextCell.getColumnIndex();
				switch (colIndex) {

				case 0:
					setCellStringType(nextCell);
					cellValue = checkCellAndGetValue(nextCell);
					validateCellData(cellValue, EmployeePersonalDetailsTemplate.EmployeeCode.name());
					break;

				case 1:
					setCellStringType(nextCell);
					cellValue = checkCellAndGetValue(nextCell);
					validateCellData(cellValue, EmployeePersonalDetailsTemplate.Name.name());
					break;

				case 2:
					setCellStringType(nextCell);
					cellValue = checkCellAndGetValue(nextCell);
					validateCellData(cellValue, EmployeePersonalDetailsTemplate.Status.name());
					break;

				case 3:
					setCellStringType(nextCell);
					cellValue = checkCellAndGetValue(nextCell);
					validateCellData(cellValue, EmployeePersonalDetailsTemplate.Email.name());
					break;

				case 4:
					setCellStringType(nextCell);
					cellValue = checkCellAndGetValue(nextCell);
					validateCellData(cellValue, EmployeePersonalDetailsTemplate.HireDate.name());
					break;

				case 5:
					setCellStringType(nextCell);
					cellValue = checkCellAndGetValue(nextCell);
					validateCellData(cellValue, EmployeePersonalDetailsTemplate.DOB.name());
					break;

				case 6:
					setCellStringType(nextCell);
					cellValue = checkCellAndGetValue(nextCell);
					validateCellData(cellValue, EmployeePersonalDetailsTemplate.Gender.name());
					break;

				case 7:
					setCellStringType(nextCell);
					cellValue = checkCellAndGetValue(nextCell);
					validateCellData(cellValue, EmployeePersonalDetailsTemplate.MaritalStatus.name());
					break;

				case 8:
					setCellStringType(nextCell);
					cellValue = checkCellAndGetValue(nextCell);
					validateCellData(cellValue, EmployeePersonalDetailsTemplate.Age.name());
					break;
				}
			}
		}
		return true;

	}

	/**
	 * It matches cell data to required data
	 * 
	 * @param cellValue
	 * @throws InvalidInputFault
	 */
	private void validateCellData(String cellValue, String requiredValue) throws InvalidInputFault {
		if (!cellValue.trim().equals(requiredValue)) {
			throw new InvalidInputFault("Invalid Template . Please correct " + requiredValue + ".");
		}
	}

	/**
	 * @param nextCell
	 */
	private String checkCellAndGetValue(Cell nextCell) {
		String cellValue = "";
		if (!ServiceUtil.isEmpty(nextCell.getStringCellValue())) {
			cellValue = nextCell.getStringCellValue();
		}
		return cellValue;
	}

	/**
	 * @param nextCell
	 */
	@SuppressWarnings("deprecation")
	private void setCellStringType(Cell nextCell) {
		nextCell.setCellType(Cell.CELL_TYPE_STRING);
	}

	/**
	 * @param reqId
	 */
	@Override
	public List<EmployeeStagingDto> getEmployeeData(int reqId) {

		List<EmployeeStagingDto> employeeStagingList = new ArrayList<EmployeeStagingDto>();
		if (!ServiceUtil.isEmpty(reqId)) {
			employeeStagingList = employeeStagingDaoLocal.fetchEmployeeStagingData(reqId);
		}
		return employeeStagingList;
	}

	/**
	 * deletes approved or rejected data from staging table
	 */
	@Override
	public MessageDto deleteStagingData(int reqId) {
		if (reqId > 0) {
			employeeStagingDaoLocal.deleteEmployeeStagingData(reqId);
			return CommonUtils.getSuccessMessage(null);
		} else {
			try {
				throw new InvalidInputFault("Please enter valid requestId!");
			} catch (InvalidInputFault e) {
				return CommonUtils.getFailedMessage(null, e.getMessage());
			}
		}
	}

	/**
	 * updates employee status to approved or rejected
	 * 
	 * @param employeedtolist
	 */
	@Override
	public MessageDto updateEmployeeData(List<EmployeeStagingDto> employeedtolist) {
		try {
			if (!ServiceUtil.isEmpty(employeedtolist)) {
				for (EmployeeStagingDto dto : employeedtolist) {
					if (!ServiceUtil.isEmpty(dto) && !ServiceUtil.isEmpty(dto.getEmployeeCode())) {
						employeeStagingDaoLocal.updateEmployeeStatus(dto);
					}
				}
			}
		} catch (InvalidInputFault e) {
			return CommonUtils.getFailedMessage(employeedtolist, e.getMessage());
		}
		return CommonUtils.getSuccessMessage(employeedtolist);
	}

	@Override
	public void putApprovedDataToMasterTable(List<EmployeeStagingDto> employeedtolist) {
		if (!ServiceUtil.isEmpty(employeedtolist)) {

		}

	}

}
