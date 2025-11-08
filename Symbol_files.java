import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Symbol_files {
    public static void main(String[] args) {
        String fileName = "24_demo.txt";

        try {
            String content = readFile(fileName);

            // Проверяем, что файл содержит только символы X, Y, Z
            if (!isValidContent(content)) {
                throw new IllegalArgumentException("Файл содержит недопустимые символы. Разрешены только X, Y, Z.");
            }

            Result result = findMaxConsecutiveDifferentCharsDetailed(content);

            System.out.println("Максимальная длина последовательности: " + result.maxLength);
            System.out.println("Начальная позиция: " + result.startIndex);
            System.out.println("Последовательность: " +
                    (result.maxLength <= 100 ?
                            content.substring(result.startIndex, result.startIndex + result.maxLength) :
                            "слишком длинная для отображения"));
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Произошла непредвиденная ошибка: " + e.getMessage());
        }
    }

    private static String readFile(String fileName) throws IOException {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }

        if (content.length() == 0) {
            throw new IOException("Файл пуст");
        }

        return content.toString();
    }

    private static boolean isValidContent(String content) {
        return content.matches("[XYZ]+");
    }

    private static Result findMaxConsecutiveDifferentCharsDetailed(String s) {
        if (s == null || s.isEmpty()) {
            return new Result(0, 0);
        }

        int maxLength = 1;
        int currentLength = 1;
        int maxStart = 0;
        int currentStart = 0;

        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) != s.charAt(i - 1)) {
                currentLength++;
                if (currentLength > maxLength) {
                    maxLength = currentLength;
                    maxStart = currentStart;
                }
            } else {
                currentLength = 1;
                currentStart = i;
            }
        }

        return new Result(maxLength, maxStart);
    }

    static class Result {
        int maxLength;
        int startIndex;

        Result(int maxLength, int startIndex) {
            this.maxLength = maxLength;
            this.startIndex = startIndex;
        }
    }
}