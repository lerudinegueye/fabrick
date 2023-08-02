package it.fabrick.bankaccount.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.fabrick.bankaccount.dto.CommonResponseModel;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	@Qualifier(value = "jsonMapperBean")
	private ObjectMapper objectMapper;
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = { EmptyInputException.class }) // We can add more instance of exception type here to handle
	@ResponseBody
	public ResponseEntity<CommonResponseModel> handleEmptyInputException(EmptyInputException ex) {
		CommonResponseModel commonResponseModel = new CommonResponseModel();
		commonResponseModel.setCode(ex.getCode());
		commonResponseModel.setErrorCode(ex.getErrorCode());
		commonResponseModel.setErrorMessage(ex.getErrorMessage());
		log.info(commonResponseModel.toString());
		return new ResponseEntity<CommonResponseModel>(commonResponseModel ,HttpStatus.BAD_REQUEST);
	}
	
}
