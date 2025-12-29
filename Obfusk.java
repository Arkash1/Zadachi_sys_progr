import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class Obfusk{

    public static void main(String[] args) {

        try {
            // Указываем файл, который будем менять
            File src = new File("TestFile.java");

            // Проверяем, есть ли он вообще
            if (!src.exists()) {
                System.out.println("Нет файла TestFile.java, нечего обрабатывать.");
                return;
            }

            // Читаем весь текст из файла
            String content = Files.readString(src.toPath());

            // Убираем комментарии и лишние пробелы
            content = clean(content);
            content = minify(content);

            // Ищем, как назывался класс раньше
            String oldName = getName(content);
            // Новое имя будет просто A
            String newName = "A";

            // Меняем имя класса в тексте
            content = content.replaceAll("\\b" + oldName + "\\b", newName);
            // Меняем названия переменных и методов
            content = renameVars(content, newName);

            // Создаем новый файл
            File resFile = new File(newName + ".java");
            Files.writeString(resFile.toPath(), content);

            System.out.println("Всё сделано! Новый файл: " + resFile.getName());

        } catch (IOException e) {
            System.out.println("Проблема с файлом: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Какая-то ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Метод удаляет все комментарии
    static String clean(String text) {
        // Убираем однострочные //
        text = text.replaceAll("//.*", "");
        // Убираем многострочные /* ... */
        text = text.replaceAll("(?s)/\\*.*?\\*/", "");
        return text;
    }

    // Метод удаляет лишние пробелы и переносы
    static String minify(String text) {
        // Схлопываем пробелы
        text = text.replaceAll("\\s+", " ");
        // Убираем пробелы вокруг скобок и знаков
        text = text.replaceAll("\\s*([{}();=,+-])\\s*", "$1");
        return text.trim();
    }

    // Ищем слово после class
    static String getName(String text) {
        Matcher m = Pattern.compile("class\\s+(\\w+)").matcher(text);
        if (m.find()) return m.group(1);
        return "UnknownClass";
    }

    // Главная логика замены имен
    static String renameVars(String text, String mainClass) {
        // Сюда сложим все найденные слова
        Set<String> words = new HashSet<>();

        // Ищем идентификаторы (слова)
        Matcher m = Pattern.compile("\\b[a-zA-Z_]\\w*\\b").matcher(text);
        while (m.find()) {
            String w = m.group();
            // Если это не имя класса и не ключевое слово Java, то запоминаем
            if (!w.equals(mainClass) &&
                    !List.of("class","public","private","protected","static","void","int",
                            "double","float","return","new","if","else","for","while").contains(w)) {
                words.add(w);
            }
        }

        // Генерируем короткие имена (a, b, c...)
        List<String> shortNames = makeList(words.size());

        int k = 0;
        // Заменяем старые слова на новые
        for (String old : words) {
            text = text.replaceAll("\\b" + old + "\\b", shortNames.get(k++));
        }

        return text;
    }

    // Создаем список нужного размера с короткими именами
    static List<String> makeList(int size) {
        List<String> res = new ArrayList<>();
        int i = 0;
        while (res.size() < size) {
            res.add(genName(i++));
        }
        return res;
    }

    // Генерируем имя по номеру: 0->a, 1->b ... 26->aa
    static String genName(int num) {
        StringBuilder sb = new StringBuilder();
        do {
            sb.append((char)('a' + (num % 26)));
            num /= 26;
        } while (num > 0);
        return sb.toString();
    }
}