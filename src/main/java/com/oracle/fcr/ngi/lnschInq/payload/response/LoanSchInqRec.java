package com.oracle.fcr.ngi.lnschInq.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanSchInqRec implements Serializable {
    private String accountNo;

    private BigDecimal stageNo;
    private Date startDate;
    private Date repaymentDate;
    private BigDecimal interestRate;
    private BigDecimal Principal;
    private BigDecimal interest;
    private BigDecimal charge;
    private BigDecimal premium;
    private BigDecimal amortisedInterest;
    private BigDecimal capitalizedInterest;
    private BigDecimal installment;
    private BigDecimal outstandingBalance;
    private BigDecimal totalInstallment;
    private BigDecimal days;
    private Date datePostponeTo ;

    @Override
    public String toString() {
        return "LoanSchInqRec{" +
                "accountNo='" + accountNo + '\'' +
                ", recNo=" +
                ", stageNo=" + stageNo +
                ", startDate=" + startDate +
                ", repaymentDate=" + repaymentDate +
                ", interestRate=" + interestRate +
                ", Principal=" + Principal +
                ", interest=" + interest +
                ", charge=" + charge +
                ", premium=" + premium +
                ", amortisedInterest=" + amortisedInterest +
                ", capitalizedInterest=" + capitalizedInterest +
                ", installment=" + installment +
                ", outstandingBalance=" + outstandingBalance +
                ", totalInstallment=" + totalInstallment +
                ", days=" + days +
                ", datePostponeTo=" + datePostponeTo +
                '}';
    }
}

