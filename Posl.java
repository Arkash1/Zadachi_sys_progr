import java.util.Random;

public class Posl {
    public static void main(String[] args) {
        Random r = new Random();

        // Количество чисел
        final int COUNT = 1000;

        // Переменные для хранения минимумов
        // Изначально ставим максимум, чтобы любое число было меньше
        int x3 = Integer.MAX_VALUE;
        int x7 = Integer.MAX_VALUE;
        int x21 = Integer.MAX_VALUE;
        int xAll = Integer.MAX_VALUE;

        // Генерируем и проверяем числа
        for (int i = 0; i < COUNT; i++) {
            int val = r.nextInt(10001);

            // Если кратно 3 и меньше текущего минимума
            if (val % 3 == 0 && val < x3) x3 = val;
            // Если кратно 7
            if (val % 7 == 0 && val < x7) x7 = val;
            // Если кратно 21
            if (val % 21 == 0 && val < x21) x21 = val;
            // Просто минимальное среди всех
            if (val < xAll) xAll = val;
        }

        // Тут будем хранить ответ
        long res = Long.MAX_VALUE;

        // Первый случай: берем мин. кратное 3 и мин. кратное 7
        if (x3 != Integer.MAX_VALUE && x7 != Integer.MAX_VALUE) {
            // Проверяем, что это не одно и то же число (если оно кратно 21)
            if (!(x3 == x7 && x3 == x21)) {
                long temp = 1L * x3 * x7;
                if (temp < res) res = temp;
            }
        }

        // Второй случай: берем мин. кратное 21 и любое минимальное
        if (x21 != Integer.MAX_VALUE && xAll != Integer.MAX_VALUE) {
            // Главное, чтобы это были разные числа
            if (x21 != xAll) {
                long temp = 1L * x21 * xAll;
                if (temp < res) res = temp;
            }
        }

        // Выводим результат
        if (res == Long.MAX_VALUE) {
            System.out.println("R = -1");
        } else {
            System.out.println("R = " + res);
        }
    }
}