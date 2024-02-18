import java.util.Scanner;

public class StringCalc {
    public static void main(String[] args) throws Exception {
        // Считываем введенные данные и помещаем в строку
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите арифметическое выражение между двумя строками:");
        String equation = scanner.nextLine();
        // Используя пользовательский метод получаем ответ и выводим его
        String result = calc(equation);
        System.out.println("\"" + result + "\"");
    }
    public static String calc(String input) throws Exception {
        String calcResult = "решение не найдено";
        if (input.charAt(0) != '\"') {
            throw new Exception("Ошибка ввода, первый операнд должен быть строкой");
        }
        // поиск и определение символа арифметического оператора с помощью пользовательского метода
        char[] arithmeticSymbols = {'+', '-', '*', '/'};
        char operator = findArithmeticSymbol(input, arithmeticSymbols);
        // создание массива операндов из двух элементов с помощью пользовательского метода
        Operand [] operands12;
        if (operator == arithmeticSymbols[0] || operator == arithmeticSymbols[1]) {
            operands12 = inputSeparated1(input);
        } else {
            operands12 = inputSeparated2(input, operator);
        }
        // проверка длины строки операндов
        if (operands12[0].opLength > 10 || operands12[1].opLength > 10) {
            throw new Exception("Ошибка ввода, превышение длины строки");
        }
        // выполнение вычислений в зависимости от оператора
        switch (operator) {
            case '+': {
                calcResult = operands12[0].stringValue+operands12[1].stringValue;
                break;
            }
            case '-': {
                if (operands12[0].stringValue.contains(operands12[1].stringValue)) {
                    calcResult = operands12[0].stringValue.replace(operands12[1].stringValue, "");
                } else {
                    calcResult = operands12[0].stringValue;
                }
                break;
            }
            case '*': {
                if (operands12[1].isItNumber() >= 1 && operands12[1].isItNumber() <= 10) {
                    int i = 1;
                    calcResult = operands12[0].stringValue;
                    while (i < operands12[1].isItNumber()) {
                        calcResult = calcResult + operands12[0].stringValue;
                        i++;
                    }
                } else {
                    if (operands12[1].isItNumber() == 0) {
                        throw new Exception("Ошибка ввода, превышение диапазона множителя");
                    } else {
                        throw new Exception("Ошибка ввода, множитель должен быть числом");
                    }
                }
                break;
            }
            case '/': {
                if (operands12[1].isItNumber() >= 1 && operands12[1].isItNumber() <= 10) {
                    int newLength = operands12[0].opLength / operands12[1].isItNumber();
                    calcResult = operands12[0].stringValue.substring(0, newLength);
                } else {
                    if (operands12[1].isItNumber() == 0) {
                        throw new Exception("Ошибка ввода, превышение диапазона делителя");
                    } else {
                        throw new Exception("Ошибка ввода, делитель должен быть числом");
                    }
                }
            }
        }
        if (calcResult.length() <= 40) {
            return calcResult;
        } else {
            return calcResult.substring(0, 40) + "...";
        }
    }
    // разделение по кавычкам на отдельные операнды для сложения/вычитания
    public static Operand[] inputSeparated1(String input) throws Exception {
        // удаление пробелов из строки и разделение строки по кавычкам
        String inputNoSpaces = input.replaceAll(" ", "");
        String [] operands = inputNoSpaces.split("\"");   // почему-то делит на 4, а не 3 строки
        // если получилось разделить более чем на два числа - неверный ввод - исключение и прерывание
        if (operands.length != 4) {
            throw new Exception("Ошибка ввода, выражение сложения/вычитания должно содержать два операнда в кавычках и один оператор");
        }
        // создание операндов
        Operand firstOp = new Operand(operands[1]);
        Operand secondOp = new Operand(operands[3]);
        return new Operand[]{firstOp, secondOp};
    }
    // разделение по оператору на отдельные операторы для умножения/вычитания
    public static Operand[] inputSeparated2(String input, char operator) throws Exception {
        // разделение строки по найденному арифметическому оператору на два числа представленные строками
        String inputNoSpaces = input.replaceAll(" ", "");
        inputNoSpaces = inputNoSpaces.replaceAll("\"", "");
        String [] operands = inputNoSpaces.split("[" + operator + "]");
        // если получилось разделить более чем на два числа - неверный ввод - исключение и прерывание
        if (operands.length != 2) {
            throw new Exception("Ошибка ввода, выражение должно содержать два операнда и один оператор");
        }
        // создание операндов
        Operand firstOp = new Operand(operands[0]);
        Operand secondOp = new Operand(operands[1]);
        return new Operand[]{firstOp, secondOp};
    }

    // проверка наличия арифметического оператора и его обнаружение
    public static char findArithmeticSymbol(String input, char[] symbols) throws Exception {
        int index = -2;
        for (char symbol : symbols) {               // перебираем по всем символам массива символов с операторами
            index = input.indexOf(symbol);
            if (index > (-1)) {         // если символ найден - прерываем поиск
                break;
            }
        }
        if (index < 0) {                    // если символ не найден - ошибка ввода
            throw new Exception("Ошибка ввода, отсутствует арифметический оператор");
        }
        return input.charAt(index);                 // возвращаем найденный арифметический оператор
    }
}