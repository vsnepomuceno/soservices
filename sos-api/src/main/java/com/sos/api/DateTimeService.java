package com.sos.api;

import java.util.Date;

/**
 * Simple date service that provides actual time and date info.
 *
 * @author Marko Asplund (marko.asplund at yahoo.com)
 */
public class DateTimeService {

    /**
     * Get current date and time.
     *
     * @return current date.
     */
    public Date getDateTime() {
        return new Date();
    }
}
