package it.fabrick.bankaccount.rest.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.fabrick.bankaccount.RestProperties;
import it.fabrick.bankaccount.common.util.ProjectErrorMessage;
import it.fabrick.bankaccount.common.util.ProjectException;
import it.fabrick.bankaccount.common.util.ProjectUtil;
import it.fabrick.bankaccount.common.util.StandardErrorMessage;
import it.fabrick.bankaccount.constants.Constants;
import it.fabrick.bankaccount.db.entities.Transaction;
import it.fabrick.bankaccount.db.service.TransactionService;
import it.fabrick.bankaccount.dto.BankAccountDto;
import it.fabrick.bankaccount.dto.BankTransFerResponse;
import it.fabrick.bankaccount.dto.Esito;
import it.fabrick.bankaccount.dto.MoneyTransFerRequestDto;
import it.fabrick.bankaccount.dto.ReadTransactionResponseDto;

/**
 * 
 * @author Nouroudine Gueye
 *
 */

@RestController
@RequestMapping("/api/gbs/banking/v4.0/accounts/{accountId}")
public class BankAccountController {
	private static final Logger log = LoggerFactory.getLogger(BankAccountController.class);

	@Autowired
	private RestProperties restProperties;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	@Qualifier(value = "jsonMapperBean")
	private ObjectMapper objectMapper;

	// **************************************************************
	// readAccountBalance
	// **************************************************************
	@Operation(summary = "retrieveBalance", description = "Allows to retrieve the balance of a user bank account", tags = {})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The result 200 is returned whenever a call to the API, which does not have semantic and / or syntactic errors, is processed by the central system and a response is returned to the caller.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankAccountDto.class))),

			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))),

			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))),

			@ApiResponse(responseCode = "500", description = "Generic error / Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))) })

	@RequestMapping(value = "/balance", produces = { "application/json" }, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<BankAccountDto> readAccountBalance(
			@RequestHeader(name = "Auth-Schema", required = true) String authSchema,
			@RequestHeader(name = "Api-Key", required = true) String apiKey,
			@PathVariable("accountId") Long accountId) throws JsonMappingException, JsonProcessingException {
		
		// Set HttpHeaders
		HttpEntity<Object> entity = ProjectUtil.fillHeaders();
		log.info("Url account balance ....."+ProjectUtil.fillPathVariable(restProperties.getFabrick().getUrlBalance(), restProperties.getFabrick().getAccountId()));
		// Call external service
		ResponseEntity<String> response = restTemplate.exchange(
				ProjectUtil.fillPathVariable(restProperties.getFabrick().getUrlBalance(), restProperties.getFabrick().getAccountId()), HttpMethod.GET, entity, String.class);
		try {
			log.info("response:" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectMapper.readValue(response.getBody(), BankAccountDto.class)));
		} catch (JsonProcessingException e) {
			throw new ProjectException(new ProjectErrorMessage(StandardErrorMessage.JSON_MAPPING_EXCEPTION));
		}
		ProjectUtil.getResponseHeaders(entity.getHeaders());
		// Return the BankAccountDto info with the balance of this user;
		return new ResponseEntity<>(objectMapper.readValue(response.getBody(), BankAccountDto.class),ProjectUtil.getResponseHeaders(entity.getHeaders()), response.getStatusCode());
	}

	// **************************************************************
	// doMoneyTransfer
	// **************************************************************

	@Operation(summary = "moneyTransfer", description = "Allows a user to do a money transfer", tags = {})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The result 200 is returned whenever a call to the API, which does not have semantic and / or syntactic errors, is processed by the central system and a response is returned to the caller.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankTransFerResponse.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))),
			@ApiResponse(responseCode = "500", description = "Generic error / Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))) })
	@RequestMapping(value = "/payments/money-transfers", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	public ResponseEntity<Esito>  doMoneyTransfer(
			@RequestHeader(name = "X-Time-Zone", defaultValue = Constants.HEADERS.X_TIME_ZONE, required = true) String xTimeZone,
			@RequestHeader(name = "Auth-Schema", defaultValue = Constants.HEADERS.AUTH_SCHEMA, required = true) String authSchema,
			@RequestHeader(name = "Api-Key", defaultValue = Constants.HEADERS.API_KEY, required = true) String apiKey,
			@PathVariable("accountId") Long accountId, @RequestBody @Valid MoneyTransFerRequestDto moneyTransFerRequest) throws JsonMappingException, JsonProcessingException {
		
		HttpEntity<Object> entity = ProjectUtil.fillHeaders();
		Esito esito = null;
		log.info("Url moneyTransfer ....."+ProjectUtil.fillPathVariable(restProperties.getFabrick().getUrlMoneyTransfer(), restProperties.getFabrick().getAccountId()));/*Constants.RESTURL.URL_MONEY_TRANSFER*/
		try {
			restTemplate.exchange(ProjectUtil.fillPathVariable(restProperties.getFabrick().getUrlMoneyTransfer(), restProperties.getFabrick().getAccountId()),HttpMethod.POST, entity, String.class);
		} catch (Exception e) {
			esito = new Esito(Constants.API000, Constants.ERROR_TECNICO_MONEY_TRANSFER);
			return new ResponseEntity<Esito>(objectMapper.readValue(Constants.ERROR_MESSAGGE, Esito.class),ProjectUtil.getResponseHeaders(entity.getHeaders()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Esito>(esito, HttpStatus.OK);//Must return the real response but we must only to return an object Esito
	}

	// **************************************************************
	// readUserTransaction
	// **************************************************************

	@Operation(summary = "readUserTransaction", description = "Allows to retrieve the list of a user transaction", tags = {})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The result 200 is returned whenever a call to the API, which does not have semantic and / or syntactic errors, is processed by the central system and a response is returned to the caller.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReadTransactionResponseDto.class))),

			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))),

			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))),

			@ApiResponse(responseCode = "500", description = "Generic error / Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))) })

	@RequestMapping(value = "/transactions", produces = {
			"application/json" } , consumes = { "application/json" } , method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ReadTransactionResponseDto> readUserTransaction(
			@RequestHeader(name = "X-Time-Zone", required = true) String xTimeZone,
			@RequestHeader(name = "Auth-Schema", required = true) String authSchema,
			@RequestHeader(name = "Api-Key", required = true) String apiKey,
			@Parameter(in = ParameterIn.PATH, description = "AccountId of the user bank account", required = true, schema = @Schema()) @PathVariable("accountId") Long accountId,
			@RequestParam("fromAccountingDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromAccountingDate,
			@RequestParam("toAccountingDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toAccountingDate)
			throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException {
		
		// Fill HttpHeaders
		HttpEntity<Object> entity = ProjectUtil.fillHeaders();
		Map<String, String> urlParams = ProjectUtil.fillUrlParameters();
		log.info("Url transactions ....."+restProperties.getFabrick().getUrlTransaction());
		String url = ProjectUtil.fillPathVariable(restProperties.getFabrick().getUrlTransaction(), restProperties.getFabrick().getAccountId());
		log.info("url without query parameters is *********>" +url);
		Map<String, Object> queryParams = ProjectUtil.fillQueryParam(fromAccountingDate, toAccountingDate);
		log.info("queryParams ........" +queryParams.toString());
		// Builder and Query parameters
		UriComponentsBuilder builder = ProjectUtil.createBuilder(fromAccountingDate, toAccountingDate, url);
		System.out.println(builder.buildAndExpand(urlParams).toUri());
        ResponseEntity<String> result = restTemplate.exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.GET, entity, String.class);
        log.info("result ....."+ result);
        //Get and tranform the body response in ReadTransactionResponseDto class
        ReadTransactionResponseDto readTransactionResponseDto = objectMapper.readValue(result.getBody(), ReadTransactionResponseDto.class);
        //Get the list of transactions from the response dto
        //map it in entities list and Save those entities into Database
        transactionService.saveTransaction(readTransactionResponseDto.getPayload().getList()
        		.stream()
         		.map(t -> modelMapper.map(t, Transaction.class)).toList());
        //Return the response with headers
        return new ResponseEntity<>(readTransactionResponseDto,ProjectUtil.getResponseHeaders(entity.getHeaders()),result.getStatusCode());
	}


}
