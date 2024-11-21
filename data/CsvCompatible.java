package data;

/**
 * This interface defines the contract for objects that can be converted into a CSV (Comma-Separated Values) format.
 * Any class implementing this interface must provide a method to convert its data into a CSV string representation.
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public interface CsvCompatible {
    /**
     * Converts the implementing object into a CSV string format.
     * The string should contain the data of the object, with fields separated by commas.
     *
     * @return a CSV string representation of the object
     */
    public abstract String toCsv();
}
