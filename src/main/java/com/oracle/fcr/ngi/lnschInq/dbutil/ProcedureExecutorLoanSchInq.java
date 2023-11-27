package com.oracle.fcr.ngi.lnschInq.dbutil;
import com.oracle.fcr.ngi.dbutil.Function;
import com.oracle.fcr.ngi.exception.ExceptionManager;
import com.oracle.fcr.ngi.exception.NGISQLException;
import com.oracle.fcr.ngi.lnschInq.payload.request.LoanSchInqReq;
import com.oracle.fcr.ngi.lnschInq.payload.response.LoanSchInqRec;
import com.oracle.fcr.ngi.lnschInq.payload.response.LoanschInqDetails;
import com.oracle.fcr.ngi.model.CustomError;
import com.oracle.fcr.ngi.util.GlobalConstant;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProcedureExecutorLoanSchInq {
    private static final String FUNC_ap_ln_acct_schedule = "pk_loan_schedule_inquery_ngi.ap_ln_acct_schedule";
    public static final String po_RESPONSE_MESSAGE = "po_RESPONSE_MESSAGE";
    public static final String po_RESPONSE_CODE = "po_RESPONSE_CODE";
    public static final String RETURN_VALUE = "RETURN_VALUE";
    private final JdbcTemplate mainJdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ProcedureExecutorLoanSchInq.class);

    @Value("LNOPER012")
    private String fcrMakerId;




    public LoanschInqDetails executelnSchInqFunction(LoanSchInqReq data) throws NGISQLException {
        Function procedure = new Function(mainJdbcTemplate, FUNC_ap_ln_acct_schedule);
        procedure.setParameters(getlnschInqParam());

        logger.info("Compiling procedure: {}", FUNC_ap_ln_acct_schedule);
        procedure.compile();

        logger.info("Executing procedure: {}", FUNC_ap_ln_acct_schedule);
        logger.info("Procedure({}) parameter's values: {}", FUNC_ap_ln_acct_schedule, data);

        Map<String, Object> outValues = null;
        try {
            // Execute the procedure with parameters
            outValues = procedure.execute( fcrMakerId ,
                    (data.getAccountNo())


            );
        } catch (Exception ex) {
            // Handle exceptions and throw NGISQLException
            logger.error("Error Track is:::-------", ex);
            ExceptionManager.throwNGISQLException(GlobalConstant.SOMETHING_WRONG_ERROR_CODE, ex.getCause().getMessage(),
                    GlobalConstant.SOMETHING_WRONG_ERROR_MESSAGE);
        }
        // Log the response from the procedure
        logger.info("Response from procedure ap_ln_acct_schedule: {}", outValues);
        logger.info("\n\n ----------------------------End procedure call: ap_ln_acct_schedule ------------------------------\n\n");

        // Check for procedure return value and handle accordingly
        assert outValues != null;
        if (!outValues.get(RETURN_VALUE).equals(0)) {
            String errCode = (String) outValues.get(po_RESPONSE_CODE);
            String errMessage = (String) outValues.get(po_RESPONSE_MESSAGE);
            CustomError error = new CustomError( errCode, errMessage, org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            ExceptionManager.throwNGISQLException(error);

        }
        List<LinkedCaseInsensitiveMap<Object>> recordList = (List<LinkedCaseInsensitiveMap<Object>>)  outValues.get("p_ln_Schedule_CUR");
        List<LoanSchInqRec> loanSchInqRecs = recordList.stream()
                .map(ProcedureExecutorLoanSchInq::mapLoanSchInqRec).collect(Collectors.toList());
        if((loanSchInqRecs.size()==0)) {
            CustomError error = new CustomError(
                    GlobalConstant.NO_RECORD_FOUND_CODE,
                    GlobalConstant.NO_RECORD_FOUND_MESSAGE,
                    org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
            );
            ExceptionManager.throwNGISQLException(error);
        }


        return new LoanschInqDetails(GlobalConstant.SUCCESS_CODE,GlobalConstant.SUCCESS_MESSAGE,loanSchInqRecs);

}
    private static LoanSchInqRec mapLoanSchInqRec(LinkedCaseInsensitiveMap<Object> recordMap) {
        LoanSchInqRec cursorRec = new LoanSchInqRec();
        cursorRec.setAccountNo((String) recordMap.get("COD_ACCT_NO"));
        cursorRec.setStageNo((BigDecimal) recordMap.get("CTR_STAGE_NO"));
        cursorRec.setStartDate((Date) recordMap.get("DAT_STAGE_START"));
        cursorRec.setRepaymentDate((Date) recordMap.get("DATE_INSTAL"));
        cursorRec.setInstallment((BigDecimal) recordMap.get("AMT_INSTAL_OUTST"));
        cursorRec.setAmortisedInterest((BigDecimal) recordMap.get("AMT_AMOR_INT"));
        cursorRec.setCharge((BigDecimal) recordMap.get("AMT_CHARGE_OUTST"));
        cursorRec.setCapitalizedInterest((BigDecimal) recordMap.get("AMT_CAPITALISED"));
        cursorRec.setPrincipal((BigDecimal) recordMap.get("AMT_PRINCIPAL"));
        cursorRec.setPremium((BigDecimal) recordMap.get("AMT_PREMIUM"));
        cursorRec.setDatePostponeTo((Date) recordMap.get("DAT_PPN_TO"));
        cursorRec.setOutstandingBalance((BigDecimal) recordMap.get("AMT_PRINC_BAL"));
        cursorRec.setDays((BigDecimal) recordMap.get("CTR_DAYS"));
        cursorRec.setInterest((BigDecimal) recordMap.get("AMT_INTEREST"));
        cursorRec.setInterestRate((BigDecimal) recordMap.get("RAT_INT"));
        cursorRec.setTotalInstallment((BigDecimal) recordMap.get("AMT_INSTAL_OUTST"));



        return cursorRec;
    }
    private SqlParameter[] getlnschInqParam() {
        logger.info("Preparing function parameters: ap_ln_acct_schedule");
        SqlParameter accountNo = new SqlParameter("P_cod_acct_no", Types.VARCHAR);
        SqlOutParameter cursorName = new SqlOutParameter("p_ln_Schedule_CUR", Types.REF_CURSOR);
        SqlOutParameter responseCode = new SqlOutParameter("po_RESPONSE_CODE", Types.INTEGER);
        SqlOutParameter responseMsg = new SqlOutParameter("po_RESPONSE_MESSAGE", Types.VARCHAR);

        SqlOutParameter returnValue = new SqlOutParameter(RETURN_VALUE, Types.INTEGER);

        SqlParameter[] parameters = {
                returnValue, accountNo,cursorName,responseCode,responseMsg
        };

        logger.info("Function parameters prepared: ap_ln_acct_schedule");
        return parameters;



    }


}

