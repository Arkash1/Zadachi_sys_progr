import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        try {
            // Читаем всё содержимое файла в строку
            String text = Files.readString(Paths.get("24_demo (1).txt"));

            // Задаем шаблон: ищем иксы (X), один или много подряд
            Pattern p = Pattern.compile("X+");
            Matcher m = p.matcher(text);

            int max = 0;
            
            // Пробегаем по всем найденным местам
            while (m.find()) {
                // Считаем длину (конец минус начало)
                int len = m.end() - m.start();
                
                // Если текущая цепочка больше рекорда, обновляем
                if (len > max) {
                    max = len;
                }
            }

            System.out.println("Максимальная цепочка X: " + max);

        } catch (IOException ex) {
            System.out.println("Что-то пошло не так с файлом: " + ex.getMessage());
        }
    }
}
