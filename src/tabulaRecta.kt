fun main() {
    val alpha = CharArray(32)
    for (i in 'А'..'Я')
        alpha[i.code - 'А'.code] = i
    val table = Array(32) { Array(32) { ' ' } }
    println("Виберите\n1-Использовать типовую таблицу\n2-Сгенерировать новую")
    when (readln().toIntOrNull()) {
        2 -> alpha.shuffle()
        null -> println("Введено неверное число")
    }
    create(alpha, table)
    for (i in table.indices) {
        for (j in 0..<table[0].size) {
            print("${table[i][j]}\t")
        }
        println()
    }
    while (true) {
        println("Выберите тип операции\n1-Кодировать\n2-Расшифровать\n3-Завершить работу")
        var readA = readln().toIntOrNull()
        if (readA == 3) break
        println("Введите текст")
        val text = readln()
        println("Введите ключ")
        val key = readln()
        var tested = test(text, key)
        if (tested == true) {
            when (readA) {
                1 -> encode(table, text, key)
                2 -> decode(key, text, table)
                else -> {
                    println("Неверный тип операции")
                    break
                }
            }
        } else println("Введены неверные данные")
    }
}

fun encode(table: Array<Array<Char>>, text: String, key: String) {
    var keyEn = mutableListOf<Char>()
    var count = 0
    while (keyEn.size != text.toCharArray().size) {
        keyEn.add(key[count])
        count++
        if (count == key.toCharArray().size) count = 0
    }
    println("Исходное сообщение: ${text}")
    print("Ключ: ")
    for (i in keyEn.indices) print(keyEn[i])
    print("\nИтог: ")
    var str = ""
    for (i in keyEn.indices) {
        str += table[keyEn[i].code - 'А'.code][text[i].code - 'А'.code]
    }
    println(str)
}

fun create(alpha: CharArray, table: Array<Array<Char>>): Array<Array<Char>> {
    var count = 0
    for (i in table.indices) {
        var countArr = 0
        for (j in 0..<table[0].size) {
            table[i][j] = alpha[countArr + count]
            countArr++
            if (countArr + count == alpha.size) {
                countArr = 0
                count = 0
            }
        }
        count = i + 1
    }
    return table
}

fun sizeKey(key: String, text: String): List<Char> {
    var keyEn = mutableListOf<Char>()
    var count = 0
    while (keyEn.size != text.toCharArray().size) {
        keyEn.add(key[count])
        count++
        if (count == key.toCharArray().size) count = 0
    }
    return keyEn
}

fun decode(key: String, text: String, table: Array<Array<Char>>) {
    var str = ""
    var keyEn = sizeKey(key, text)
    for (i in keyEn.toCharArray().indices) {
        for (j in table.indices) {
            for (f in table[0].indices) {
                if (table[0][j] == keyEn[i] && table[f][j] == text[i])
                    str += table[f][0]
            }
        }
    }
    println("Расшифровка: ${str}\n")
}

fun test(text: String, key: String): Boolean {
    var tested = true
    var keyEn = sizeKey(key, text)
    for (i in 0..<keyEn.size) {
        if (keyEn[i] as? Int != null || keyEn[i] !in 'А'..'Я' || text[i] as? Int != null || text[i] !in 'А'..'Я') {
            tested = false
            break
        }
    }
    return tested
}