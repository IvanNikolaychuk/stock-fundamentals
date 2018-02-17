package com.entity;

public enum PropertyType {
    DATA("datekey"), TICKER("ticker"),

    // income statement
    NUMBER_OF_SHARES("shareswa"), DIVIDENDS_PER_SHARE("dps"), DIVIDEND_YIELD("divyeild"), EPS("epsusd"),
    REVENUE("revenue"), NET_INCOME("netinc"), LAST_PE("lastpe"), AVG_5_YEAR_PE("avgpe"),

    // balance sheet
    TOTAL_DEBT_TO_EQUITY("DE"), TANGIBLE_BOOK_VALUE_PER_SHARE("tbvps"), WORKING_CAPITAL("workingcapital"),
    EQUITY("EQUITYUSD"), LT_DEBT("DEBTNC"), LT_DEBT_TO_EQUITY("ltde"),

    // cash flow
    FREE_CASH_FLOW("fcf"), FREE_CASH_FLOW_PER_SHARE("fcfps");

    private String shortName;

    PropertyType(String shortName) {
        this.shortName = shortName;
    }

    public static PropertyType find(String shortName) {
        for(PropertyType propertyType : PropertyType.values()) {
            if (propertyType.shortName.equalsIgnoreCase(shortName)) return propertyType;
        }

        throw new IllegalArgumentException("No property type for " + shortName + " found");
    }
}
