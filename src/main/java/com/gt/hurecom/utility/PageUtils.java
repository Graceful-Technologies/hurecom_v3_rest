package com.gt.hurecom.utility;

import com.gt.hurecom.dto.common.PageResponse;
import org.springframework.data.domain.Page;

import java.util.function.Function;

public class PageUtils {

    public static <T, R> PageResponse<R> convertToPageResponse(Page<T> page, Function<T, R> mapper) {
        PageResponse<R> response = new PageResponse<>();
        response.setContent(page.getContent().stream().map(mapper).toList());
        response.setTotalElements(page.getTotalElements());
        return response;
    }
}
