package com.github.abigail830.ecommerce.ordercontext;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class BadSmellTests {

    @Test
    void should_sleep() throws InterruptedException {
        Thread.sleep(100);
    }

    @Test
    void should_print_without_assert() throws InterruptedException {
        System.out.println("Only print");
    }

    @Disabled
    void should_be_disabled() throws InterruptedException {
        System.out.println("Disabled");
    }
}
