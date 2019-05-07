package us.redshift.timesheet.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;

public class Reusable {

    public static Pageable paginationSort(int page, int limit, String orderBy, String... fields) {
        Pageable pageable = PageRequest.of(page, limit);
        if (page == 0 && limit == 1) {
            pageable = Pageable.unpaged();
            if (orderBy != null && fields != null) {
                pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.valueOf(orderBy), fields));
            }
        } else {
            if (orderBy != null && fields != null) {
                pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.valueOf(orderBy), fields));
            }
        }
        return pageable;
    }

    public static BigDecimal multiplyRates(BigDecimal hours, BigDecimal ratePerHours) {
        return hours.multiply(ratePerHours);
    }
}
