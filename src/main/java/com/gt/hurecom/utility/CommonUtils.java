package com.gt.hurecom.utility;

import com.gt.hurecom.exception.HurecomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtils {

    private static final Logger log = LoggerFactory.getLogger(CommonUtils.class);

    public static void throwBusinessException(String message) throws HurecomException {
        throw new HurecomException(message);
    }
}
