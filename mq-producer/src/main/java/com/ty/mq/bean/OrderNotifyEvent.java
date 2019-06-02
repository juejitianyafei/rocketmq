package com.ty.mq.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderNotifyEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderId;

    private BigDecimal payMoney;
}
