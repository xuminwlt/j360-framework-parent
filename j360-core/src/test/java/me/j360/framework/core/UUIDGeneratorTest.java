package me.j360.framework.core;

import me.j360.framework.core.kit.id.UUIDGenerator;
import org.junit.Test;

/**
 * @author: min_xu
 * @date: 2019/1/16 2:30 PM
 * 说明：
 */
public class UUIDGeneratorTest {

    @Test
    public void id() {
        UUIDGenerator.init(1);

        for (int i=1; i<= 1000; i++) {
            System.out.println(UUIDGenerator.generateUUID());
        }
    }
}
