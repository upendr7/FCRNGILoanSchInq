package com.oracle.fcr.ngi.lnschInq.payload.request;

import com.oracle.fcr.ngi.exception.ExceptionManager;
import com.oracle.fcr.ngi.exception.GlobalException;
import com.oracle.fcr.ngi.util.GlobalConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class LoanSchInqReq implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(LoanSchInqReq.class);

    @NotBlank
    @Size(min = 12, max = 12, message = "AccountNo  size must be 12 characters")
    @Pattern(regexp = "^\\d*$", message = "AccountNo must be numeric.")
    private String accountNo;


    public String getTxnBodyKey() throws GlobalException {
        String txnKeyString = "";
        try {
            txnKeyString = accountNo;
            logger.info("txnKeyString  is : {}", txnKeyString);
        }catch (Exception e){
            ExceptionManager.throwGlobalException(
                    GlobalConstant.SOMETHING_WRONG_ERROR_CODE,
                    "An exception occurred while creating txnHeaderKey",
                    GlobalConstant.SOMETHING_WRONG_ERROR_TYPE);
        }
        return txnKeyString;
    }

}
