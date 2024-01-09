import java.util.Scanner;

public class Main {
    // Преобразует введенные данные в ответ
    public static void main(String[] args) throws Exception {
        // Считываем введенные данные и помещаем в строку
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите арифметическое выражение между двумя числами от 1 до 10 Римскими или Арабскими цифрами:");
        String equation = scanner.nextLine();
        // Используя пользовательский метод получаем ответ и выводим его
        String result = calc(equation);
        System.out.println("= " + result);
    }

    // Храним в enum все возможные Римские цифры, включая возможные результаты вычислений,
    // ZERO добавлено для совпадения индекса в перечислении и значения объекта
    enum RomanNumerals {
        ZERO, I, II, III, IV, V, VI, VII, VIII, IX, X,
        XI, XII, XIII, XIV, XV, XVI, XVII, XVIII, XIX, XX,
        XXI, XXII, XXIII, XXIV, XXV, XXVI, XXVII, XXVIII, XXIX,
        XXX, XXXI, XXXII, XXXIII, XXXIV, XXXV, XXXVI, XXXVII, XXXVIII, XXXIX,
        XL, XLI, XLII, XLIII, XLIV, XLV, XLVI, XLVII, XLVIII, XLIX,
        L, LI, LII, LIII, LIV, LV, LVI, LVII, LVIII, LIX,
        LX, LXI, LXII, LXIII, LXIV, LXV, LXVI, LXVII, LXVIII, LXIX,
        LXX, LXXI, LXXII, LXXIII, LXXIV, LXXV, LXXVI, LXXVII, LXXVIII, LXXIX,
        LXXX, LXXXI, LXXXII, LXXXIII, LXXXIV, LXXXV, LXXXVI, LXXXVII, LXXXVIII, LXXXIX,
        XC, XCI, XCII, XCIII, XCIV, XCV, XCVI, XCVII, XCVIII, XCIX, C

    }

    // Получаем массив enum всех значений из enum для дальнейшего преобразования в массив строк
    static RomanNumerals[] checkRomanNumbers = RomanNumerals.values();

    // Метод для получения массива строк со значениями массива Enum для дальнейшего сравнения с введенным значением
    static String[] enumsToStrings(RomanNumerals[] enums) {
        String[] romanNumbers = new String[enums.length];       // объявляем массив строк с длиной как у массива enum
        for (int i = 0; i < enums.length; i++) {                // для каждого индекса копируем имя enum в строку
            romanNumbers[i] = enums[i].name();
        }
        return romanNumbers;
    }

    static String[] romanNumerals = enumsToStrings(checkRomanNumbers);

    // Калькулятор, который обрабатывает введенную строку:
    // разделяет на оператор и числа, проверяет числа, считает результат и переводит его в строку
    public static String calc(String input) throws Exception {
        // инициализация внутренних переменных
        int solution = 0;       // численный ответ
        String calcResult = "wrong input";      // строковый ответ

        // поиск и определение символа арифметического оператора с помощью нового метода
        char[] arithmeticSymbols = {'+', '-', '*', '/'};
        char operator = findArithmeticSymbol(input, arithmeticSymbols);

        // разделение строки по найденному арифметическому оператору на два числа представленные строками
        String [] numbers = input.split("[" + operator + "]");         //("\\" + String.valueOf(operator));
        // если получилось разделить более чем на два числа - неверный ввод - исключение и прерывание
        if (numbers.length != 2) {
            throw new Exception("Ошибка ввода, выражение должно содержать два операнда и один оператор");
        }
        // инициализация строковых переменных для первого и второго числа, присвоение значения
        String numOne = numbers[0].replace(" ", "");
        String numTwo = numbers[1].replace(" ", "");

        // инициализация численных переменных для первого и второго числа
        int first = 0;
        int second = 0;

        // если оба числа являются Римскими (проверяет метод isRoman)
        if (isRoman(numOne) && isRoman(numTwo)) {
            //Оба числа Римские, переводим в арабские цифры, считаем и выводим ответ
            for (int i = 1; i<11; i++) {        // перевод введенных Римских в Арабские
                RomanNumerals checkRomanNumber = checkRomanNumbers[i];      // перебираем enum
                String checkRomanNum = checkRomanNumber.toString();         // перевод объекта enum в строку
                if (numOne.equals(checkRomanNum)) {                    // проверка и перевод первого числа
                    first = i;
                }
                if (numTwo.equals(checkRomanNum)) {                    // проверка и перевод второго числа
                    second = i;
                }
                if ((first != 0) && (second != 0)) {                   // если оба числа переведены прерываем цикл
                    break;
                }
            }

            // Проведение арифметической операции, получение ответа в int
            switch (operator) {
                case '+': {
                    solution = first + second;
                    break;
                }
                case '-': {
                    solution = first - second;
                    break;
                }
                case '*': {
                    solution = first * second;
                    break;
                }
                case '/': {
                    solution = first / second;
                    break;
                }
            }

            // Передача решения в строку с обратным переводом в Римскую систему
            if (solution < 1) {            // нельзя выводить значения меньше 1 в Римской системе
                throw new Exception("Ошибка, в римской системе нет отрицательных чисел и нуля");
            }
            calcResult = romanNumerals[(solution)];      // берем строку из массива с индексом равным решению
        }

        // если оба числа являются Арабскими (проверяет метод isArabic)
        if (isArabic(numOne) && isArabic(numTwo)) {
            // переводим из строки в число
            first = Integer.parseInt(numOne);
            second = Integer.parseInt(numTwo);

            // проведение операции, получение ответа в int
            switch (operator) {
                case '+': {
                    solution = first + second;
                    break;
                }
                case '-': {
                    solution = first - second;
                    break;
                }
                case '*': {
                    solution = first * second;
                    break;
                }
                case '/': {
                    solution = first / second;
                    break;
                }
            }
            calcResult = String.valueOf(solution);      // перевод ответа из числа в строку
        }

        // если предыдущие две проверки чисел провалены - ошибка ввода - исключение и прерывание
        if (calcResult.equals("wrong input")) {
            throw new Exception("Ошибка ввода, введите арифметическое выражение между двумя числами от 1 до 10 только Римскими или только Арабскими цифрами");
        }
        return calcResult;
    }

    // Поиск арифметического оператора. Допустимые операторы передаются из массива символов в методе calc.
    // Возвращает значение первого найденного арифметического оператора
    static char findArithmeticSymbol(String input, char[] symbols) throws Exception {
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

    // Проверка на запись числа Римскими цифрами
    static boolean isRoman(String num) {
        boolean checkRoman = false;                 // переменная результата проверки
        for (int i = 0; i<11; i++) {                // до 11 потому что X под индексом 10
            if (num.equals(romanNumerals[i])) {
                checkRoman = true;                  // если нашли Римское число отмечаем проверку успешной
                break;
            }
        }
        return checkRoman;
    }

    // Проверка на запись числа Арабскими цифрами
    static boolean isArabic(String num) {
        boolean checkArabic = false;        // переменная результата проверки
        int[] checkArabicNumbers = {1,2,3,4,5,6,7,8,9,10};  // массив доступных к вводу чисел
        for (int i = 0; i<10; i++) {
            int checkArabicNumber = checkArabicNumbers[i];              // перебираем числа
            String checkArabicNum = String.valueOf(checkArabicNumber);  // перевод числа в строку
            if (num.equals(checkArabicNum)) {                           // сравниваем строковые значения
                checkArabic = true;         // если нашли отмечаем проверку успешной и выходим из цикла
                break;
            }
        }
        return checkArabic;
    }
}
