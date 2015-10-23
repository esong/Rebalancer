package com.yksong.rebalancer.test;

import com.yksong.rebalancer.Rebalancer;
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
public class RebalancerTests {
    @Test
    public void testNullPortfolio() {
        Rebalancer.rebalance(null);
    }

    @Test
    public void testEmptyPortfolio() {
        try {
            Rebalancer.rebalance(new Portfolio(new ArrayList<Investment>()));
        } catch (Portfolio.PortfolioException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOneInvestment() {
        List<Investment> investments = new ArrayList<Investment>();
        investments.add(new Investment("GOOG", new BigDecimal("1.0"), new BigDecimal("0.5"), 1, new BigDecimal("1")));

        try {
            Rebalancer.RebalanceReport report = Rebalancer.rebalance(new Portfolio(investments));
            List<Rebalancer.RebalanceReport.ReportItem> reportItems = report.getItems();
            Assert.assertEquals(1, reportItems.size());

            Rebalancer.RebalanceReport.ReportItem item = reportItems.get(0);
            Assert.assertEquals(item.getAction(), Rebalancer.RebalanceReport.Action.buy);
            Assert.assertEquals(item.getTicker(), "GOOG");
            Assert.assertEquals(item.getShares(), 1);

        } catch (Portfolio.PortfolioException e) {
            e.printStackTrace();
        }
    }
}
