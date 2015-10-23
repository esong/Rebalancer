package com.yksong.rebalancer.test;

import com.yksong.rebalancer.model.Investment;
import com.yksong.rebalancer.model.Portfolio;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by esong on 2015-10-23.
 */
public class PortfolioTests {
    @Test
    public void testNullInvestment() {
        try {
            new Portfolio(null);
        } catch (Exception e) {
            Assert.assertNotNull(e.getMessage());
        }
    }

    @Test
    public void testEmptyInvestment() {
        try {
            new Portfolio(new ArrayList<Investment>());
        } catch (Exception e) {
            Assert.assertNotNull(e.getMessage());
        }
    }

    @Test
    public void testInvalidAllocation() {
        List<Investment> investments = new ArrayList<Investment>();
        investments.add(new Investment("test", new BigDecimal("1.1"), new BigDecimal("0.2"),
                5000, new BigDecimal("20")));

        try {
            new Portfolio(investments);
        } catch (Portfolio.PortfolioException e) {
            Assert.assertNotNull(e.getMessage());
        }

        investments.remove(0);

        investments.add(new Investment("test", new BigDecimal("0.5"), new BigDecimal("20"), 5000, new BigDecimal("20")));

        try {
            new Portfolio(investments);
        } catch (Portfolio.PortfolioException e) {
            Assert.assertNotNull(e.getMessage());
        }

        investments.remove(0);
        investments.add(new Investment("test", new BigDecimal("0.5"), new BigDecimal("0.6"),
                5000, new BigDecimal("20")));
        investments.add(new Investment("test", new BigDecimal("0.6"), new BigDecimal("0.5"),
                5000, new BigDecimal("20")));

        try {
            new Portfolio(investments);
        } catch (Portfolio.PortfolioException e) {
            Assert.assertNotNull(e.getMessage());
        }
    }

    @Test
    public void testWrongAllocation() {
        List<Investment> investments = new ArrayList<Investment>();
        investments.add(new Investment("test", new BigDecimal("0.6"), new BigDecimal("0.4"),
                500000, new BigDecimal("20")));
        investments.add(new Investment("test1", new BigDecimal("0.4"), new BigDecimal("0.6"),
                1, new BigDecimal("20")));

        try {
            new Portfolio(investments);
        } catch (Portfolio.PortfolioException e) {
            Assert.assertNotNull(e.getMessage());
        }
    }
}
