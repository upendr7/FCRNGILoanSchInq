package com.oracle.fcr.ngi.lnschInq.payload.request;

import com.oracle.fcr.ngi.exception.ExceptionManager;
import com.oracle.fcr.ngi.exception.GlobalException;
import com.oracle.fcr.ngi.util.GlobalConstant;
import com.oracle.fcr.ngi.util.SessionMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.validation.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class HeaderRequest implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(HeaderRequest.class);

    @NotBlank
    @Size(max = 40)
    private String svcRqId;

    @Size(min = 14, max = 14, message = "TxnDate  size must be 14 characters")
    @Pattern(regexp = "^(19|20|21)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[1-2][\\d]|3[0-1])([01][\\d]|2[0-3])([0-5][\\d])([0-5][\\d])$", message = "Date format must be yyyyMMddHHmmss")
    @NotBlank(message = "txnDate Is Mandatory")
    private String txnDate;

    private String transactionBranch;

    private String txnKey;

    @NotBlank(message = "channelDirect Is Mandatory Input")
    @Pattern(regexp = "\\b[YN]\\b", message = "only Y or N are allowed.")
    @Size(max = 1, message = "channelDirect must be 1 character")
    private String channelDirect;

    public HeaderRequest() {
        this.svcRqId = (String) SessionMap.getValue(GlobalConstant.KEY_MAP_SVC_RQ_ID);
        this.txnDate = (String) SessionMap.getValue(GlobalConstant.KEY_MAP_TXN_DATE);
        this.transactionBranch = (String) SessionMap.getValue(GlobalConstant.KEY_MAP_TRANSACTION_BRANCH);
        this.txnKey = (String) SessionMap.getValue(GlobalConstant.KEY_MAP_TXN_KEY);
        this.channelDirect = (String) SessionMap.getValue(GlobalConstant.KEY_MAP_CHANNEL_DIRECT);
    }

    public static HeaderRequest createAndValidate() {
        HeaderRequest header = new HeaderRequest();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<HeaderRequest>> violations = validator.validate(header);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return header;

    }

    @Override
    public String toString() {
        return "HeaderRequest{" +
                "svcRqId='" + svcRqId + '\'' +
                ", txnDate='" + txnDate + '\'' +
                ", transactionBranch='" + transactionBranch + '\'' +
                ", txnKey='" + txnKey + '\'' +
                ", channelDirect='" + channelDirect + '\'' +
                '}';
    }

    public String getTxnHeaderKeyString() throws GlobalException {
        String txnHeaderKeyString = "";
        try {
            txnHeaderKeyString =  svcRqId + txnDate + channelDirect;
            logger.info("TxnHeader key: {}", txnHeaderKeyString);
        } catch (Exception e) {
            ExceptionManager.throwGlobalException(
                    GlobalConstant.SOMETHING_WRONG_ERROR_CODE,
                    "An exception occurred while creating txnHeaderKey",
                    GlobalConstant.SOMETHING_WRONG_ERROR_TYPE);
        }
        return txnHeaderKeyString;
    }



}
