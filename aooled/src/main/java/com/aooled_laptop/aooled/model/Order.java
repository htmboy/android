package com.aooled_laptop.aooled.model;

public class Order {
    // 基本信息
    private int id; // 订单id
    private boolean isDistribution; // 是否是经销
    private boolean isAssurance; //是否是质保单
    private boolean isSpecialOffer; // 是否申请特价
    private int fillDate; // 填单日期
    private String orderNumber; // 订单号
    private boolean isSimpleOrder; // 是否是样品订单
    private String contractNumber; // 合同号
    private int orderStatus; // 订单状态
    private boolean isConstruction; // 是否是工程单
    private boolean isBorrowData; // 是否借资质
    private boolean isCancel; // 是否撤销
    private int goodsNumber; // 货物数量
    // 发货信息
    private String method; // 发货方式
    private String expressPrice; // 运费;
    private boolean isayer; // 是否我方付运费
    private String customerCompany; // 公司名称
    private boolean isAlterReciept; // 是否代收款
    private float alterAmount; // 代收金额
    private boolean isNoticeDelivery; // 是否通知发货
    private String sendTo; // 收货地址
    private String contact; // 收货人
    private String contactTel; // 联系电话
    // 收款信息
    private String recieptBank; // 收款银行
    private boolean isContainTax; // 是否含税
    private float contractAmount; // 合同金额;
    private float deposit; // 定金
    private float assurance; // 质保金
    private int assuranceDate; // 质保金时间
    private float tail; // 尾款
    private int tailDate; // 尾款收取时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getFillDate() {
        return fillDate;
    }

    public void setFillDate(int fillDate) {
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

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
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

    public int getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        this.goodsNumber = goodsNumber;
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

    public boolean isIsayer() {
        return isayer;
    }

    public void setIsayer(boolean isayer) {
        this.isayer = isayer;
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

    public float getAlterAmount() {
        return alterAmount;
    }

    public void setAlterAmount(float alterAmount) {
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

    public float getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(float contractAmount) {
        this.contractAmount = contractAmount;
    }

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }

    public float getAssurance() {
        return assurance;
    }

    public void setAssurance(float assurance) {
        this.assurance = assurance;
    }

    public int getAssuranceDate() {
        return assuranceDate;
    }

    public void setAssuranceDate(int assuranceDate) {
        this.assuranceDate = assuranceDate;
    }

    public float getTail() {
        return tail;
    }

    public void setTail(float tail) {
        this.tail = tail;
    }

    public int getTailDate() {
        return tailDate;
    }

    public void setTailDate(int tailDate) {
        this.tailDate = tailDate;
    }
}
