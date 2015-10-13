package com.metapack.accumulator;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;
import java.util.stream.IntStream;

/**
 * This class was written as a test for Metapack
 * Created by amits on 12/10/2015.
 */
public class AccumulatorImpl implements Accumulator {

    private static int ZERO = 0;
    private IntBinaryOperator operationFn = (x,y) -> x + y; //declare the addn lambda
    private static AccumulatorImpl instance = new AccumulatorImpl(); //eager object creation
    private AtomicInteger accumulatedValue; //Atomic so no side effects from concurrent threads

    private  AccumulatorImpl() {
        accumulatedValue = new AtomicInteger();
    }

    public static AccumulatorImpl getInstance() {
        return instance;
    }

    /**
     * <p>Adds one or more values to the running sum.</p>
     *
     * <p>This method calculates the sum of the given arguments first, then updates the total value
     * with this sum.</p>
     *
     * @param values
     *     the item or items to add to the running total
     * @return the sum of the arguments passed to the method
     */
    public int accumulate(int... values) {
        int sum= IntStream.of(values).reduce(0, Math::addExact); //sum of all values
        return accumulatedValue.accumulateAndGet(sum,operationFn);
    }

    /**
     * <p>The current value of the total sum.</p>
     *
     * @return the total sum value
     */
    public int getTotal() {
        return accumulatedValue.get();
    }

    /**
     * <p>Resets the running value to 0.</p>
     */
    public void reset() {
        accumulatedValue.set(ZERO);

    }
}
