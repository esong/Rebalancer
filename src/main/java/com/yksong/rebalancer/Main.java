package com.yksong.rebalancer;

import com.yksong.rebalancer.model.Investment;
import com.yksong.rebalancer.model.Portfolio;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by esong on 2015-10-22.
 */
public class Main {

    private static final String sTicker = "Ticker";
    private static final String sTarget = "Target allocation";
    private static final String sActual = "Actual allocation";
    private static final String sSharesOwn = "Shares owned";
    private static final String sSharesPrice = "Share price";


    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }

        // Parsing and creating portfolio from a csv file.
        Reader in = null;
        Portfolio portfolio = null;
        try {
            in = new FileReader(args[0]);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);

            NumberFormat percentageFormat = NumberFormat.getPercentInstance();
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

            List<Investment> investments = new ArrayList<Investment>();
            for (CSVRecord record : records) {
                String ticker = record.get(sTicker);
                // Use BigDecimal to avoid precision problems
                BigDecimal targetAllocation = new BigDecimal(percentageFormat.parse(record.get(sTarget)).toString());
                BigDecimal actualAllocation = new BigDecimal(percentageFormat.parse(record.get(sActual)).toString());

                long sharesOwn = Long.parseLong(record.get(sSharesOwn));
                BigDecimal price = new BigDecimal(currencyFormat.parse(record.get(sSharesPrice)).toString());

                investments.add(new Investment(ticker, targetAllocation, actualAllocation, sharesOwn, price));
            }

            portfolio = new Portfolio(investments);
        } catch (FileNotFoundException e) {
            System.err.println("Error: file " + args[0] + " doesn't exist");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Portfolio.PortfolioException e) {
            System.err.println(e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Rebalancing
        if (portfolio != null) {
            System.out.println(
                    Rebalancer.rebalance(portfolio).toString());
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java -jar Rebalancer-1.0.jar [csv filename]");
    }
}
