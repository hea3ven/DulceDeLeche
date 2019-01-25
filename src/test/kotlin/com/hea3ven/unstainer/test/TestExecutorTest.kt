package com.hea3ven.unstainer.test

import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Assert.assertThat
import org.junit.Test

class TestExecutorTest {
    @Test
    fun testCreate() {
        val exec = TestExecutor()

        assertThat(exec, not(nullValue()))
    }
}