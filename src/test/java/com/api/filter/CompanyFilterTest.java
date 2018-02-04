package com.api.filter;

import com.api.dto.CompanyDto;
import org.junit.Test;

import java.util.List;

import static com.api.Constants.DELISTED_EXCHANGE;
import static com.api.filter.CompanyFilter.filter;
import static com.api.filter.CompanyFilter.ONLY_LISTED;
import static java.util.Arrays.asList;
import static junit.framework.Assert.*;

public class CompanyFilterTest {

    @Test
    public void delistedCompaniesAreFiltered() {
        CompanyDto delisted = new CompanyDto();
        delisted.exchange = DELISTED_EXCHANGE;

        CompanyDto listed = new CompanyDto();
        listed.exchange = "NYSE";

        List<CompanyDto> listedCompanies = filter(asList(listed, delisted), ONLY_LISTED);

        assertEquals(listedCompanies.size(), 1);
        assertEquals(listedCompanies.get(0), listed);
    }
}
