package com.oracle.fcr.ngi.lnschInq.payload.response;

import com.oracle.fcr.ngi.payload.response.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanschInqDetails implements Response, Serializable {
    private String responseCode;
    private String responseMessage;
    private List<LoanSchInqRec> loanSchInqRecs;

    @Override
    public String toString() {
        return "LoanschInqDetails{" +
                "responseCode='" + responseCode + '\'' +
                ", responseMessage='" + responseMessage + '\'' +
                ", loanSchInqRecs=" + loanSchInqRecs +
                '}';
    }
}
