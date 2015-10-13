package com.metapack.accumulator;

/**
 * Created by amits on 12/10/2015.
 */
public interface Accumulator {
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
    int accumulate(int... values);

    /**
     * <p>The current value of the total sum.</p>
     *
     * @return the total sum value
     */
    int getTotal();

    /**
     * <p>Resets the running value to 0.</p>
     */
    void reset();
}
