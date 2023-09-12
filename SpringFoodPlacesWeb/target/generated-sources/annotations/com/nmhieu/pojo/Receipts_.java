package com.nmhieu.pojo;

import com.nmhieu.pojo.ReceiptDetail;
import com.nmhieu.pojo.ReceiptStatus;
import com.nmhieu.pojo.Users;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-09-12T16:31:15")
@StaticMetamodel(Receipts.class)
public class Receipts_ { 

    public static volatile SingularAttribute<Receipts, BigDecimal> totalPayment;
    public static volatile SingularAttribute<Receipts, Date> receiptDate;
    public static volatile SingularAttribute<Receipts, Boolean> active;
    public static volatile SingularAttribute<Receipts, ReceiptStatus> statusReceiptId;
    public static volatile SetAttribute<Receipts, ReceiptDetail> receiptDetailSet;
    public static volatile SingularAttribute<Receipts, Integer> receiptId;
    public static volatile SingularAttribute<Receipts, Users> userId;

}