package com.aooled_laptop.aooled.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Order {
    // 基本信息

    private String id; // 订单id
    private String code; // 代号
    private boolean isDistribution; // 是否是经销
    private boolean isAssurance; //是否是质保单
    private boolean isSpecialOffer; // 是否申请特价
    private String fillDate; // 填单日期
    private String orderNumber; // 订单号
    private boolean isSimpleOrder; // 是否是样品订单
    private String contractNumber; // 合同号
    private String orderStatus; // 订单状态
    private boolean isConstruction; // 是否是工程单
    private boolean isBorrowData; // 是否借资质
    private boolean isCancel; // 是否撤销
    private String goodsCount; // 货物数量
    // 发货信息
    private String method; // 发货方式
    private String expressPrice; // 运费;
    private String payer; // 是否我方付运费
    private String customerCompany; // 公司名称
    private boolean isAlterReciept; // 是否代收款
    private String alterAmount; // 代收金额
    private boolean isNoticeDelivery; // 是否通知发货
    private String sendTo; // 收货地址
    private String contact; // 收货人
    private String contactTel; // 联系电话
    // 收款信息
    private String recieptBank; // 收款银行
    private boolean isContainTax; // 是否含税
    private String contractAmount; // 合同金额;
    private String deposit; // 定金
    private String assurance; // 质保金
    private String assuranceDate; // 质保金时间
    private String tail; // 尾款
    private String tailDate; // 尾款收取时间
    private String constructionAmount; // 施工款
    private String constructionAccount; // 施工款账号

    public String getConstructionAmount() {
        return constructionAmount;
    }

    public void setConstructionAmount(String constructionAmount) {
        this.constructionAmount = constructionAmount;
    }

    public String getConstructionAccount() {
        return constructionAccount;
    }

    public void setConstructionAccount(String constructionAccount) {
        this.constructionAccount = constructionAccount;
    }

    public String getId() {

        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDistribution() {
        return isDistribution;
    }

    public void setDistribution(boolean distribution) {
        isDistribution = distribution;
    }

    public boolean isAssurance() {
        return isAssurance;
    }

    public void setAssurance(boolean assurance) {
        isAssurance = assurance;
    }

    public boolean isSpecialOffer() {
        return isSpecialOffer;
    }

    public void setSpecialOffer(boolean specialOffer) {
        isSpecialOffer = specialOffer;
    }

    public String getFillDate() {
        return fillDate;
    }

    public void setFillDate(String fillDate) {
        this.fillDate = fillDate;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public boolean isSimpleOrder() {
        return isSimpleOrder;
    }

    public void setSimpleOrder(boolean simpleOrder) {
        isSimpleOrder = simpleOrder;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public boolean isConstruction() {
        return isConstruction;
    }

    public void setConstruction(boolean construction) {
        isConstruction = construction;
    }

    public boolean isBorrowData() {
        return isBorrowData;
    }

    public void setBorrowData(boolean borrowData) {
        isBorrowData = borrowData;
    }

    public boolean isCancel() {
        return isCancel;
    }

    public void setCancel(boolean cancel) {
        isCancel = cancel;
    }

    public String getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(String goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getExpressPrice() {
        return expressPrice;
    }

    public void setExpressPrice(String expressPrice) {
        this.expressPrice = expressPrice;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getCustomerCompany() {
        return customerCompany;
    }

    public void setCustomerCompany(String customerCompany) {
        this.customerCompany = customerCompany;
    }

    public boolean isAlterReciept() {
        return isAlterReciept;
    }

    public void setAlterReciept(boolean alterReciept) {
        isAlterReciept = alterReciept;
    }

    public String getAlterAmount() {
        return alterAmount;
    }

    public void setAlterAmount(String alterAmount) {
        this.alterAmount = alterAmount;
    }

    public boolean isNoticeDelivery() {
        return isNoticeDelivery;
    }

    public void setNoticeDelivery(boolean noticeDelivery) {
        isNoticeDelivery = noticeDelivery;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getRecieptBank() {
        return recieptBank;
    }

    public void setRecieptBank(String recieptBank) {
        this.recieptBank = recieptBank;
    }

    public boolean isContainTax() {
        return isContainTax;
    }

    public void setContainTax(boolean containTax) {
        isContainTax = containTax;
    }

    public String getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getAssurance() {
        return assurance;
    }

    public void setAssurance(String assurance) {
        this.assurance = assurance;
    }

    public String getAssuranceDate() {
        return assuranceDate;
    }

    public void setAssuranceDate(String assuranceDate) {
        this.assuranceDate = assuranceDate;
    }

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

    public String getTailDate() {
        return tailDate;
    }

    public void setTailDate(String tailDate) {
        this.tailDate = tailDate;
    }

    public JSONObject toJson(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("code", code);
            jsonObject.put("isDistribution", isDistribution);
            jsonObject.put("isAssurance", isAssurance);
            jsonObject.put("isSpecialOffer", isSpecialOffer);
            jsonObject.put("fillDate", fillDate);
            jsonObject.put("orderNumber", orderNumber);
            jsonObject.put("isSimpleOrder", isSimpleOrder);
            jsonObject.put("contractNumber", contractNumber);
            jsonObject.put("orderStatus", orderStatus);
            jsonObject.put("isConstruction", isConstruction);
            jsonObject.put("isBorrowData", isBorrowData);
            jsonObject.put("isCancel", isCancel);
            jsonObject.put("goodsCount", goodsCount);
            jsonObject.put("method", method);
            jsonObject.put("expressPrice", expressPrice);
            jsonObject.put("payer", payer);
            jsonObject.put("customerCompany", customerCompany);
            jsonObject.put("isAlterReciept", isAlterReciept);
            jsonObject.put("alterAmount", alterAmount);
            jsonObject.put("isNoticeDelivery", isNoticeDelivery);
            jsonObject.put("sendTo", sendTo);
            jsonObject.put("contact", contact);
            jsonObject.put("contactTel", contactTel);
            jsonObject.put("recieptBank", recieptBank);
            jsonObject.put("isContainTax", isContainTax);
            jsonObject.put("contractAmount", contractAmount);
            jsonObject.put("deposit", deposit);
            jsonObject.put("assurance", assurance);
            jsonObject.put("assuranceDate", assuranceDate);
            jsonObject.put("tail", tail);
            jsonObject.put("tailDate", tailDate);
            jsonObject.put("constructionAmount", constructionAmount);
            jsonObject.put("constructionAccount", constructionAccount);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public String toString(){
        return "{" + "\"id\":\"" + id + "\",\"isDistribution\":\"" + isDistribution + "\",\"isAssurance\":\"" + isAssurance
                + "\",\"isSpecialOffer\":\"" + isSpecialOffer + "\",\"fillDate\":\"" +  fillDate
                + "\",\"orderNumber\":\"" + orderNumber + "\",\"isSimpleOrder\":\"" + isSimpleOrder + "\",\"contractNumber\":\"" + contractNumber
                + "\",\"orderStatus\":\"" + orderStatus + "\",\"isConstruction\":\"" + isConstruction + "\",\"isBorrowData\":\""+ isBorrowData
                + "\",\"isCancel\":\"" + isCancel + "\",\"goodsCount\":\"" + goodsCount + "\",\"method\":\"" + method
                + "\",\"expressPrice\":\"" + expressPrice + "\",\"payer\":\"" + payer + "\",\"customerCompany\":\"" + customerCompany
                + "\",\"isAlterReciept\":\"" + isAlterReciept + "\",\"alterAmount\":\"" + alterAmount + "\",\"isNoticeDelivery\":\"" + isNoticeDelivery
                + "\",\"sendTo\":\"" + sendTo + "\",\"contact\":\"" + contact + "\",\"contactTel\":\"" + contactTel
                + "\",\"recieptBank\":\"" + recieptBank + "\",\"isContainTax\":\"" + isContainTax + "\",\"contractAmount\":\"" + contractAmount
                + "\",\"deposit\":\"" + deposit + "\",assurance\":\"" + assurance + "\",\"assuranceDate\":\"" + assuranceDate
                + "\",\"tail\":\"" + tail + "\",\"tailDate\":\"" + tailDate + "\"  ,\"constructionAmount\":\"" + constructionAmount + "\",\"constructionAccount\":\"" + constructionAccount + "\"}";

    }


}
