fun main() {
    print("Введите первое число: ")
    val num1 = readLine()?.toDoubleOrNull() ?: 0.0
    print("Введите второе число: ")
    val num2 = readLine()?.toDoubleOrNull() ?: 0.0
    print("Выберите операцию (+, -, *, /): ")
    val op = readLine()
    val result = when (op) {
        "+" -> num1 + num2
        "-" -> num1 - num2
        "*" -> num1 * num2
        "/" -> if (num2 != 0.0) num1 / num2 else "Ошибка деления на ноль"
        else -> "Неверная операция"
    }
    println("Результат: $result")
}
   