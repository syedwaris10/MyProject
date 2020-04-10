package com.incture.employee.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.apache.http.HttpStatus;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.incture.employee.constants.Messages;
import com.incture.employee.dto.MessageDto;
import com.incture.employee.exceptions.InvalidInputFault;

public class CommonUtils {

	/**
	 * decodes string from encoded base64
	 * 
	 * @param encoded
	 * @return
	 */
	public static String decodeBase64(String encoded) {

		String decoded = "";
		if (!ServiceUtil.isEmpty(encoded)) {
			byte[] bytearray = Base64.getDecoder().decode(encoded);
			decoded = bytearray.toString();
		}
		return decoded;
	}

	/**
	 * encodes string to base64
	 * 
	 * @param str
	 * @return
	 */
	public static String encodeToBase64(String str) {
		String encoded = "";
		if (!ServiceUtil.isEmpty(str)) {
			byte[] bytearray = Base64.getEncoder().encode(str.getBytes());
			encoded = bytearray.toString();
		}
		return encoded;
	}

	/**
	 * 
	 * @param base64
	 * @return
	 */
	public static InputStream getInputStreamFromEncodedFile(String base64) {
		byte[] fileByteArray = null;
		InputStream inputStream = null;
		try {
			fileByteArray = Base64.getDecoder().decode(base64);
			inputStream = new ByteArrayInputStream(fileByteArray);
		} catch (Exception e) {
			throw e;
		}
		return inputStream;
	}

	/**
	 * 
	 * @param excelFileFormat
	 * @param base64
	 * @return
	 * @throws IOException
	 * @throws InvalidInputFault
	 */
	public static Workbook getWorkbookObject(String excelFileFormat, String base64)
			throws IOException, InvalidInputFault {

		Workbook workbook = null;
		if (!ServiceUtil.isEmpty(excelFileFormat) && !ServiceUtil.isEmpty(base64)) {
			InputStream inputStream = CommonUtils.getInputStreamFromEncodedFile(base64);
			if (excelFileFormat.equalsIgnoreCase("xlsx")) {
				workbook = new XSSFWorkbook(inputStream);
			} else if (excelFileFormat.equalsIgnoreCase("xls")) {
				workbook = new HSSFWorkbook(inputStream);
			} else {
				throw new InvalidInputFault("Please upload file of xlsx or xls format only.");
			}
		}
		return workbook;
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static MessageDto getSuccessMessage(Object data) {
		MessageDto dto = new MessageDto();
		dto.setStatusCode(HttpStatus.SC_OK);
		dto.setData(data);
		dto.setMessage(Messages.success);
		return dto;
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static MessageDto getFailedMessage(Object data, String msg) {
		MessageDto dto = new MessageDto();
		dto.setStatusCode(HttpStatus.SC_BAD_REQUEST);
		dto.setData(data);
		dto.setMessage(msg);
		return dto;
	}

	/**
	 * get Date in dd-MM-yyyy format
	 * 
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(String strDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		if (!ServiceUtil.isEmpty(strDate)) {
			return sdf.parse(strDate);
		}
		return null;
	}

	/**
	 * generates a integer random number
	 * 
	 * @return
	 */
	public static int getRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(10000);
	}

	/**
	 * return unique 36 char uuid unique value
	 * 
	 * @return
	 */
	public static String getRandomUUIDKey() {
		UUID uid = UUID.randomUUID();
		return uid.toString();
	}

	/**
	 * creates a employee Id
	 * 
	 * @return
	 */
	public static String createNewEmployeeCode() {
		String id;
		id = "EMP" + CommonUtils.getRandomNumber();
		return id;
	}

	/**
	 * validates proper expected date format
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static boolean dateFormatCheck(Date date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		sdf.parse(date.toString());
		return true;
	}

	/**
	 * validates if data can be updated
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkForDataToUpdate(String str) {
		String regex = "[@#$%&^!]+";
		if (ServiceUtil.isEmpty(str)) {
			return false;
		} else if (!ServiceUtil.isEmpty(str) && str.matches(regex)) {
			return false;
		}
		return true;
	}

	/**
	 * validates email id
	 * 
	 * @param emailId
	 * @return
	 */
	public static boolean isEmailValid(String emailId) {
		if (!ServiceUtil.isEmpty(emailId)) {
			String regex = "^[aA-zZ0-9]+@[aA-zZ0-9]+$.com";
			if (emailId.trim().matches(regex)) {
				return true;
			}
		}
		return false;
	}
}
