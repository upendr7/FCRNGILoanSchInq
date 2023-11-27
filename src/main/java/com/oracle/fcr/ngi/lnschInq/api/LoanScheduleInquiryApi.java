package com.oracle.fcr.ngi.lnschInq.api;

import com.oracle.fcr.ngi.exception.GlobalException;
import com.oracle.fcr.ngi.lnschInq.payload.request.HeaderRequest;
import com.oracle.fcr.ngi.lnschInq.payload.request.LoanSchInqReq;
import com.oracle.fcr.ngi.lnschInq.service.LoanSchInqService;
import com.oracle.fcr.ngi.model.CustomResponse;
import com.oracle.fcr.ngi.payload.response.Response;
import com.oracle.fcr.ngi.util.CommonUtils;
import com.oracle.fcr.ngi.util.ResponseBuilder;
import com.oracle.fcr.ngi.util.SessionMap;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.Collections;

import static com.oracle.fcr.ngi.util.GlobalConstant.KEY_MAP_API_VERSION;
import static com.oracle.fcr.ngi.util.GlobalConstant.KEY_MAP_TRACE_ID;


@CrossOrigin
@RestController
@RequestMapping("/fcr/ngi/{version}/loan/schedule")
@Validated
@RequiredArgsConstructor
public class LoanScheduleInquiryApi {

    private static final Logger logger = LoggerFactory.getLogger(LoanScheduleInquiryApi.class);
    private final ResponseBuilder <Response> responseBuilder;
    private  final LoanSchInqService loanSchInqService;


    @PostMapping("/inquire/txn-key")
    public ResponseEntity<CustomResponse<String>> inquireTxnKey(@PathVariable("version") String version,
                                                                @Valid @RequestBody LoanSchInqReq requestBody,
                                                                HttpServletRequest httpServletRequest)
            throws GlobalException {
        logger.info("Entered in method-inquireTxnKey of class-LoanScheduleInquiryApi at {}", System.currentTimeMillis());
        logger.info("Request received for inquireTxnKey {}", requestBody);
        HeaderRequest header = new HeaderRequest();
        String txnKey = CommonUtils.generateSHA256Key(header.getTxnHeaderKeyString() + requestBody.getTxnBodyKey());
        SessionMap.setContext(KEY_MAP_API_VERSION, version);
        String traceId = (String) SessionMap.getValue(KEY_MAP_TRACE_ID);

        CustomResponse<String> response = new CustomResponse<>();
        response.setResponse(Collections.singletonList(txnKey));
        response.setErrors(Collections.emptyList());
        response.setTraceId(traceId);
        response.setSuccess(true);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/inquire")
    public ResponseEntity<CustomResponse<Response>> inquire(@PathVariable String version, @RequestBody @Valid LoanSchInqReq request) throws GlobalException {
        // Log the entry into the method
        logger.info("Entered in inquire of class-LoanScheduleInquiryApi at {}", System.currentTimeMillis());
        // Log the received request
        logger.info("Request received for Loan Schedule Inquiry {}", request);
        // Set API version in session context
        SessionMap.setContext(KEY_MAP_API_VERSION, version);
        Response vo = loanSchInqService.inquire(request);
        CustomResponse<Response> response = new CustomResponse<>();
        response.setResponse(Collections.singletonList(vo));
        response.setErrors(Collections.emptyList());
        return ResponseEntity.ok().body(responseBuilder.buildResponse(response));
    }


}