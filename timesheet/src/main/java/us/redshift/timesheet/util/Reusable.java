package us.redshift.timesheet.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

public class Reusable {

    public static Pageable paginationSort(int page, int limit, String orderBy, String... fields) {
        Pageable pageable;
        if (page == 0 && limit == 0) {
            pageable = Pageable.unpaged();
            if (orderBy != null && fields != null) {
                pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.valueOf(orderBy), fields));
            }
        } else {
            pageable = PageRequest.of(page, limit);
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


    public static <T, U> Page<T> getPaginated(Page<U> page, List<T> contents) {
        Page<T> ts = new Page<T>() {
            @Override
            public int getTotalPages() {
                return page.getTotalPages();
            }

            @Override
            public long getTotalElements() {
                return page.getTotalElements();
            }

            @Override
            public <U> Page<U> map(Function<? super T, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return page.getNumber();
            }

            @Override
            public int getSize() {
                return page.getSize();
            }

            @Override
            public int getNumberOfElements() {
                return page.getNumberOfElements();
            }

            @Override
            public List<T> getContent() {
                return contents;
            }

            @Override
            public boolean hasContent() {
                return page.hasContent();
            }

            @Override
            public Sort getSort() {
                return page.getSort();
            }

            @Override
            public boolean isFirst() {
                return page.isFirst();
            }

            @Override
            public boolean isLast() {
                return page.isLast();
            }

            @Override
            public boolean hasNext() {
                return page.hasNext();
            }

            @Override
            public boolean hasPrevious() {
                return page.hasPrevious();
            }

            @Override
            public Pageable nextPageable() {
                return page.nextPageable();
            }

            @Override
            public Pageable previousPageable() {
                return page.previousPageable();
            }

            @NotNull
            @Override
            public Iterator<T> iterator() {
                return contents.iterator();
            }
        };

        return ts;
    }

}
