package cn.sobne.dal;

import java.util.Date;
import java.util.UUID;

public class InvoiceBean{
    private UUID mId;
    private Date mDate;
    private String mTitleName;
    private String mCreditCode;
    private String mAddress;
    private String mTelephone;
    private String mBankAccount;

    public InvoiceBean(){
        this(UUID.randomUUID());
    }
    public  InvoiceBean(UUID id){
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getTitleName() {
        return mTitleName;
    }

    public void setTitleName(String titleName) {
        mTitleName = titleName;
    }

    public String getCreditCode() {
        return mCreditCode;
    }

    public void setCreditCode(String creditCode) {
        mCreditCode = creditCode;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getTelephone() {
        return mTelephone;
    }

    public void setTelephone(String telephone) {
        mTelephone = telephone;
    }

    public String getBankAccount() {
        return mBankAccount;
    }

    public void setBankAccount(String bankAccount) {
        mBankAccount = bankAccount;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
