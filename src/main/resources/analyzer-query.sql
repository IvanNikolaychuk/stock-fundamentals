SELECT *
FROM potentially_undervalued_company undervalued
WHERE ticker IN (
  SELECT DISTINCT ticker
  FROM analyze_summary
  WHERE property = 'EPS' AND trend IN ('STABLE_GROWTH', 'UNSTABLE_GROWTH', 'FLAT')
) AND ticker IN (
    SELECT DISTINCT ticker
    FROM analyze_summary
    WHERE property = 'DIVIDENDS_PER_SHARE' AND trend IN ('STABLE_GROWTH', 'UNSTABLE_GROWTH', 'FLAT')
) AND ticker IN (
    SELECT DISTINCT ticker
    FROM analyze_summary
    WHERE property = 'FREE_CASH_FLOW' AND trend IN ('STABLE_GROWTH', 'UNSTABLE_GROWTH', 'FLAT')
) AND ticker IN (
    SELECT DISTINCT ticker
    FROM analyze_summary
    WHERE property = 'REVENUE' AND trend IN ('STABLE_GROWTH', 'UNSTABLE_GROWTH', 'FLAT')
) AND ticker IN (
    SELECT DISTINCT ticker
    FROM analyze_summary
    WHERE property = 'TANGIBLE_BOOK_VALUE_PER_SHARE' AND trend IN ('STABLE_GROWTH', 'UNSTABLE_GROWTH', 'FLAT')
);
