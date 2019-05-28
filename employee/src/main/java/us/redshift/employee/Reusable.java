package us.redshift.employee;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

}
