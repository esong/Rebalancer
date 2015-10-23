package com.yksong.rebalancer;

import com.yksong.rebalancer.model.Investment;
import com.yksong.rebalancer.model.Portfolio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by esong on 2015-10-23.
 */
public class Rebalancer {
    public static RebalanceReport rebalance(Portfolio portfolio) {
        if (portfolio == null) {
            return null;
        }
        BigDecimal totalAmount = portfolio.getTotalAmount();

        RebalanceReport rebalanceReport = new RebalanceReport();
        for (Investment investment : portfolio.getInvestments()) {
            // Target = total * targetAllocation %
            BigDecimal targetAmount = totalAmount.multiply(investment.getTargetAllocation());
            // Diff = targetAmount - (shares * price)
            BigDecimal diff = targetAmount.subtract(
                    new BigDecimal(investment.getSharesOwned()).multiply(investment.getPrice()));
            // Share = diff / price
            long shareDiff = diff.divide(investment.getPrice(), RoundingMode.DOWN).longValue();

            RebalanceReport.Action action = null;

            if (shareDiff > 0) {
                action = RebalanceReport.Action.buy;
            } else if (shareDiff < 0) {
                action = RebalanceReport.Action.sell;
                shareDiff *= -1;
            }

            if (action != null) {
                rebalanceReport.addItem(new RebalanceReport.ReportItem(action, shareDiff, investment.getTicker()));
            }
        }

        return rebalanceReport;
    }

    public static class RebalanceReport {
        List<ReportItem> items = new ArrayList<ReportItem>();

        public enum Action {
            buy, sell;
        }

        public static class ReportItem {
            private Action action;
            private long shares;
            private String ticker;

            public ReportItem(Action action, long shares, String ticker) {
                this.action = action;
                this.shares = shares;
                this.ticker = ticker;
            }

            public Action getAction() {
                return action;
            }

            public long getShares() {
                return shares;
            }

            public String getTicker() {
                return ticker;
            }

        }

        public String toString() {
            StringBuilder sb = new StringBuilder();

            if (items != null) {
                for (ReportItem item : items) {
                    switch (item.action) {
                        case buy: {
                            sb.append("buy ");
                            break;
                        }
                        case sell: {
                            sb.append("sell ");
                            break;
                        }
                    }

                    sb.append(String.format("%1$d shares of %2$s", item.shares, item.ticker));
                    sb.append("\n");
                }
            }

            // Remove the trailing newline
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }

        public void addItem(ReportItem item) {
            items.add(item);
        }

        public List<ReportItem> getItems() {
            return items;
        }
    }
}
