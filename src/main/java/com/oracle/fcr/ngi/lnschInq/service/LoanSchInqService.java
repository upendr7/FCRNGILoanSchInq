package com.oracle.fcr.ngi.lnschInq.service;


import com.oracle.fcr.ngi.exception.GlobalException;
import com.oracle.fcr.ngi.lnschInq.payload.request.LoanSchInqReq;
import com.oracle.fcr.ngi.payload.response.Response;

public interface LoanSchInqService {

    Response inquire(LoanSchInqReq request) throws GlobalException;


}
