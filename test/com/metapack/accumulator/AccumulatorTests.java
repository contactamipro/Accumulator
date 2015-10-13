package com.metapack.accumulator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

/**
 * Created by amits on 12/10/2015.
 */
public class AccumulatorTests {

    private static Accumulator accumulator;

    @BeforeClass
    public static void oneTimeSetUp() throws Exception {
        accumulator = AccumulatorImpl.getInstance(); //anti-patttern: breaking DI!
    }

    @AfterClass
    public static void oneTimeTearDown() throws Exception {

    }

    @Before
    public void setUp() throws Exception {
        accumulator.reset();

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testAccumulatorBasics() {
        assertEquals(90, accumulator.accumulate(10,20,60)); //expected 60
    }

    @Test
    public void testAccumulatorWithNegative() {
        assertEquals(-90, accumulator.accumulate(-10,-20,-60)); //expected 60
    }


    @Test
    public void testAccumulatorWithThreads() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 50; i++) {
            executor.execute(new Runnable() {
                public void run() {
                    accumulator.accumulate(5,5);//each thread adds 10
                }
            });
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Total: " + accumulator.getTotal());
        assertEquals(500, accumulator.getTotal());

    }

    @Test(expected = ArithmeticException.class)
    public void testAccumulatorWithOverflow() {
        assertEquals(2147483647, accumulator.accumulate(2147483640,7)); //expected highest int - 2147483647
        assertEquals(2147483647, accumulator.accumulate(2147483640, 8)); //arithmetic exception
    }
}
