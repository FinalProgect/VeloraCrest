package model;



public class UnitConverter {

    // Unit categories
    private static final int CATEGORY_GENERAL = 1;
    private static final int CATEGORY_VOLUME = 2;
    private static final int CATEGORY_WEIGHT = 3;
    private static final int CATEGORY_LENGTH = 4;

    // Get the category of a unit
    private static int getUnitCategory(int unit) {
        switch (unit) {
            case 1:
            case 2:
            case 3:
                return CATEGORY_GENERAL;
            case 4:
            case 5:
                return CATEGORY_VOLUME;
            case 6:
            case 7:
                return CATEGORY_WEIGHT;
            case 8:
            case 9:
                return CATEGORY_LENGTH;
            default:
                throw new IllegalArgumentException("Unknown unit: " + unit);
        }
    }

    // Convert quantity to base unit
    private static double convertToBase(double quantity, int unit) {
        switch (unit) {
            case 1:
            case 2:
            case 3:
                return quantity;
            case 4:
                return quantity * 1000; // Liter to milliliter
            case 5:
                return quantity;
            case 6:
                return quantity * 1000; // Gram to milligram
            case 7:
                return quantity;
            case 8:
                return quantity * 100; // Meter to centimeter
            case 9:
                return quantity;
            default:
                throw new IllegalArgumentException("Unknown unit: " + unit);
        }
    }

    // Convert base quantity back to original unit
    private static double convertFromBase(double baseQuantity, int targetUnit) {
        switch (targetUnit) {
            case 1:
            case 2:
            case 3:
                return baseQuantity;
            case 4:
                return baseQuantity / 1000.0; // milliliter to liter
            case 5:
                return baseQuantity;
            case 6:
                return baseQuantity / 1000.0; // milligram to gram
            case 7:
                return baseQuantity;
            case 8:
                return baseQuantity / 100.0; // centimeter to meter
            case 9:
                return baseQuantity;
            default:
                throw new IllegalArgumentException("Unknown unit: " + targetUnit);
        }
    }

    /**
     * Converts and sums two quantities, and returns: [0] = total converted
     * quantity in unit1 [1] = unit1 as int
     */
    public static Object[] convertAndSum(double quantity1, int unit1, double quantity2, int unit2) {
        int category1 = getUnitCategory(unit1);
        int category2 = getUnitCategory(unit2);

        if (category1 != category2) {
            throw new IllegalArgumentException("Incompatible units: Cannot combine unit " + unit1 + " with unit " + unit2);
        }

        double base1 = convertToBase(quantity1, unit1);
        double base2 = convertToBase(quantity2, unit2);

        double totalInBase = base1 + base2;
        double totalInOriginalUnit = convertFromBase(totalInBase, unit1);

        // Optional rounding to 3 decimals
        totalInOriginalUnit = Math.round(totalInOriginalUnit * 1000.0) / 1000.0;

        return new Object[]{totalInOriginalUnit, unit1};
    }

}
