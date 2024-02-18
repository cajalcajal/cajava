public class Operand {
    String stringValue;
    int opLength;
    int isNumber;

    // параметры объявления нового объекта Operand
    Operand (String stringValues) {
        this.stringValue = stringValues;
        this.isNumber = isItNumber();
        this.opLength = stringValue.length();
    }

    // проверка второго операнда на числовое значение
    public int isItNumber() {
        try {
            int theNumber = Integer.parseInt(stringValue);
            if (theNumber >= 1 && theNumber <= 10) {
                return theNumber;       // получаем число для дальнейших вычислений
            }
            else
                return 0;               // введено число, но за пределами диапазона
        }
        catch (NumberFormatException e) {
            return -1;                  // введено не число
        }
    }

}
