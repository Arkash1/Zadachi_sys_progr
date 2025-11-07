import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Symbol_files {
    public static void main(String[] args) {
        try {
            // Читаем файл
            String content = Files.readString(Paths.get("24_demo.txt"));

            int maxLength = 1; // Минимальная длина - 1 символ
            int currentLength = 1;

            // Проходим по всей строке, начиная со второго символа
            for (int i = 1; i < content.length(); i++) {
                // Если текущий символ отличается от предыдущего
                if (content.charAt(i) != content.charAt(i - 1)) {
                    currentLength++;
                    maxLength = Math.max(maxLength, currentLength);
                } else {
                    // Сбрасываем счетчик, если символы одинаковые
                    currentLength = 1;
                }
            }

            System.out.println("Максимальная длина: " + maxLength);

        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}