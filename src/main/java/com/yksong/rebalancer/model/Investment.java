package com.yksong.rebalancer.model;

import java.math.BigDecimal;

/**
 * Created by esong on 2015-10-22.
 */
public class Investment {
    String ticker;
    BigDecimal targetAllocation;
    BigDecimal actualAllocation;
    long sharesOwned;
    BigDecimal price;

    public Investment(String ticker, BigDecimal targetAllocation, BigDecimal actualAllocation, long sharesOwned,
            BigDecimal price) {
        this.ticker = ticker;
        this.targetAllocation = targetAllocation;
        this.actualAllocation = actualAllocation;
        this.sharesOwned = sharesOwned;
        this.price = price;
    }

    public String getTicker() {
        return ticker;
    }

    public BigDecimal getTargetAllocation() {
        return targetAllocation;
    }

    public long getSharesOwned() {
        return sharesOwned;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
