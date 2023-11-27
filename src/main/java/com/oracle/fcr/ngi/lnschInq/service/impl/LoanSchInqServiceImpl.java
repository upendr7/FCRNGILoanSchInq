package com.oracle.fcr.ngi.lnschInq.service.impl;

import com.oracle.fcr.ngi.exception.GlobalException;
import com.oracle.fcr.ngi.exception.NGISQLException;
import com.oracle.fcr.ngi.lnschInq.dbutil.ProcedureExecutorLoanSchInq;
import com.oracle.fcr.ngi.lnschInq.payload.request.HeaderRequest;
import com.oracle.fcr.ngi.lnschInq.payload.request.LoanSchInqReq;
import com.oracle.fcr.ngi.lnschInq.service.LoanSchInqService;
import com.oracle.fcr.ngi.payload.response.Response;
import com.oracle.fcr.ngi.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;


@Service
@RequiredArgsConstructor
public class LoanSchInqServiceImpl implements LoanSchInqService {

    private static final Logger logger = LoggerFactory.getLogger(LoanSchInqServiceImpl.class);

    private final CommonUtils commonUtils;

    private final ProcedureExecutorLoanSchInq procedureExecutorLoanSchInq;

    @Override
    public Response inquire(LoanSchInqReq request) throws GlobalException {

            logger.info("Entered in method-inquire of class-ChequeStatusInquiryImpl at {}", System.currentTimeMillis());
//        validating headers and txnkey
            HeaderRequest header = HeaderRequest.createAndValidate();
            commonUtils.validateTxnKey(header.getTxnKey(),
                    CommonUtils.generateSHA256Key(header.getTxnHeaderKeyString() + request.getTxnBodyKey()));
            try {
                return procedureExecutorLoanSchInq.executelnSchInqFunction(request);
            } catch (NGISQLException e) {
                throw new GlobalException(e.getErrors());
            }
        }
    }

