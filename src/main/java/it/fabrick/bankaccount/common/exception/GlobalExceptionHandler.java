package it.fabrick.bankaccount.common.exception;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.fabrick.bankaccount.common.util.ProjectException;
import it.fabrick.bankaccount.common.util.ProjectUtil;
import it.fabrick.bankaccount.common.util.StandardErrorMessage;
import it.fabrick.bankaccount.constants.Constants;
import it.fabrick.bankaccount.dto.CommonResponseModel;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	@Qualifier(value = "jsonMapperBean")
	private ObjectMapper objectMapper;
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


	public ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Iterator<String> headerNames = request.getHeaderNames();
		java.util.List<String> listHeadersRequired = new ArrayList<String>();
		while (headerNames.hasNext()) {
			String header = headerNames.next();
			listHeadersRequired.add(header);
		}
		String missingHeader = null;
		if (!listHeadersRequired.contains(Constants.HeaderList.AUTH_SCHEMA.getValue())) {
			missingHeader = "Auth-Schema";
		}
		if (!listHeadersRequired.contains(Constants.HeaderList.API_KEY.getValue())) {
			missingHeader = "Api-Key";
		}
		if (!listHeadersRequired.contains(Constants.HeaderList.CONTENT_TYPE.getValue())) {
			missingHeader = "Content-Type";
		}

		CommonResponseModel commonResponseModel = new CommonResponseModel();
		commonResponseModel.setCode(StandardErrorMessage.FIELD_MISSING.code);
		commonResponseModel
				.setMessage(ProjectUtil.fillErrorData(StandardErrorMessage.FIELD_MISSING, missingHeader).getMessage());
		log.error(ExceptionUtils.getStackTrace(ex));
		return handleExceptionInternal(ex, commonResponseModel, new HttpHeaders(),
				HttpStatus.valueOf(StandardErrorMessage.FIELD_MISSING.httpCode), request);
	}

	@ExceptionHandler(value = { Exception.class })
	@ResponseBody
	public ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) throws JsonProcessingException {
		if (ex instanceof ProjectException) {
			return this.handleApplicationException((ProjectException) ex, request);
		}
		return handleUnknowException(ex, request);
	}

	private ResponseEntity<Object> handleUnknowException(Exception ex, WebRequest request)
			throws JsonProcessingException {
		CommonResponseModel commonResponseModel = new CommonResponseModel();
		commonResponseModel.setCode(StandardErrorMessage.ERROR_TECNICO_MONEY_TRANSFER.code);
		commonResponseModel.setMessage(StandardErrorMessage.ERROR_TECNICO_MONEY_TRANSFER.message);
		log.error(ExceptionUtils.getStackTrace(ex));

		try {
			log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(commonResponseModel));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return handleExceptionInternal(ex, commonResponseModel, new HttpHeaders(),
				HttpStatus.valueOf(StandardErrorMessage.GENERIC_ERROR.httpCode), request);
	}

	private ResponseEntity<Object> handleApplicationException(ProjectException ex, WebRequest request)
			throws JsonProcessingException {
		CommonResponseModel commonResponseModel = new CommonResponseModel();
		commonResponseModel.setCode(ex.getProjectErrorMessage().getCode());
		commonResponseModel.setMessage(ex.getProjectErrorMessage().getMessage());
		log.error(ExceptionUtils.getStackTrace(ex));
		try {
			log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(commonResponseModel));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return handleExceptionInternal(ex, commonResponseModel, new HttpHeaders(),
				HttpStatus.valueOf(ex.getProjectErrorMessage().getHttpCode()), request);
	}

}
