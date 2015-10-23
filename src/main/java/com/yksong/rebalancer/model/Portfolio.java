package com.yksong.rebalancer.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Created by esong on 2015-10-22.
 */
public class Portfolio {
    private List<Investment> mInvestments;
    private BigDecimal mTotalAmount;

    public Portfolio(List<Investment> investments) throws PortfolioException {
        if (investments == null) {
            throw new PortfolioException("Investment is null");
        }
        mInvestments = investments;

        BigDecimal totalAmount = null;
        BigDecimal totalPercentage = new BigDecimal("0");
        BigDecimal one = new BigDecimal(1);
        for (Investment investment : investments) {
            if (investment.actualAllocation.compareTo(one) > 0 || investment.targetAllocation.compareTo(one) > 0) {
                throw new PortfolioException("Error: allocation > 100%");
            }

            BigDecimal curPercentage = investment.actualAllocation;
            totalPercentage = totalPercentage.add(curPercentage);
            BigDecimal curAmount = (investment.price.multiply(new BigDecimal(investment.sharesOwned))
                    .divide(curPercentage, RoundingMode.HALF_EVEN));

            // Make sure the totalAmount calculated from each investment matches
            if (totalAmount != null) {
                // Allow tiny error caused by input csv file.
                if (Math.abs(totalAmount.compareTo(curAmount)) > totalAmount.intValue() * 0.001) {
                    throw new PortfolioException("Error: portfolio allocation doesn't match");
                }
            } else {
                totalAmount = curAmount;
            }
        }

        if (totalPercentage.compareTo(one) > 0 ) {
            throw new PortfolioException("Error: total allocation > 1");
        }

        mTotalAmount = totalAmount;
    }

    public List<Investment> getInvestments() {
        return mInvestments;
    }

    public BigDecimal getTotalAmount() {
        return mTotalAmount;
    }

    public class PortfolioException extends Exception {
        public PortfolioException(String msg) {
            super(msg);
        }
    }
}
