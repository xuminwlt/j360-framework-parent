package me.j360.framework.core.kit.id;


import com.vip.vjtools.vjkit.base.ExceptionUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class UUIDGenerator {

    private static AtomicLong UUID = new AtomicLong(1000);

    private static int UUID_INTERNAL = 2000000000;

    public static long generateUUID() {
        long id = UUID.incrementAndGet();
        if (id > UUID_INTERNAL) {
            synchronized (UUID) {
                if (UUID.get() >= id) {
                    id -= UUID_INTERNAL;
                    UUID.set(id);
                }
            }
        }
        return id;
    }

    public static void init(int serverNodeId) {

        UUID.set(UUID_INTERNAL * serverNodeId);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date date = null;
        try {
            date = format.parse("2019-01-01");
        } catch (ParseException e) {
            ExceptionUtil.unchecked(e);
        }
        cal.setTime(date);
        long base = cal.getTimeInMillis();
        long current = System.currentTimeMillis();
        UUID.addAndGet((current - base) / 1000);

    }
}
