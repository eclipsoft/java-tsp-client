package com.eclipsoft.tsp.trx;

import java.util.Date;

public class Transaction {

    private String code;

    private String beneficiaryId;

    private String beneficiaryName;

    private String cellphone;

    private Double amount;

    private Date paymentDate;

    private String atm;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getAtm() {
        return atm;
    }

    public void setAtm(String atm) {
        this.atm = atm;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "code='" + code + '\'' +
                ", beneficiaryId='" + beneficiaryId + '\'' +
                ", beneficiaryName='" + beneficiaryName + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", atm='" + atm + '\'' +
                '}';
    }
}
