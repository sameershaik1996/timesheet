package us.redshift.timesheet.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

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

    public static LocalDate calcEndDate(final LocalDate startDate, final Long estimatedDays) {


        if (estimatedDays < 1) {
            return startDate;
        }
        LocalDate endDate = startDate;
        int addedDays = 1;
        while (addedDays < estimatedDays) {
            endDate = endDate.plusDays(1);

            if (!isWeekEnd(endDate)) {
                addedDays++;
            }
        }
        return endDate;
    }

    private static boolean isWeekEnd(LocalDate date) {
        DayOfWeek dow = date.getDayOfWeek();
        return dow == SATURDAY || dow == SUNDAY;
    }
}
