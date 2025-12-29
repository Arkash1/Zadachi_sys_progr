import java.util.*;

public class MinPosl {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Random r = new Random();

        System.out.print("Сколько чисел генерировать? ");
        int size = in.nextInt();

        // Проверка, чтобы было из чего пары делать
        if (size < 2) {
            System.out.println("Нужно хотя бы 2 числа!");
            return;
        }

        int[] arr = new int[size];
        // Заполняем массив случайными числами
        for (int i = 0; i < size; i++) {
            arr[i] = r.nextInt(10000) + 1;
        }

        System.out.println("Получившийся массив:");
        System.out.println(Arrays.toString(arr));

        // Сначала ищем самое маленькое число
        int min = Integer.MAX_VALUE;
        for (int x : arr) {
            if (x < min) {
                min = x;
            }
        }

        int k = 0; // счетчик пар
        int mx = -100000; // для хранения максимальной суммы

        // Проходим по массиву и смотрим пары (текущий + следующий)
        for (int i = 0; i < arr.length - 1; i++) {
            int first = arr[i];
            int second = arr[i + 1];

            // Проверяем условие задачи
            if (first % 16 == min || second % 16 == min) {
                k++;
                int sum = first + second;
                // Обновляем максимум, если нашли сумму больше
                if (sum > mx) {
                    mx = sum;
                }
            }
        }

        // Вывод результата
        if (k == 0) {
            System.out.println("Ничего не нашли: 0 0");
        } else {
            System.out.println("Найдено пар: " + k);
            System.out.println("Макс. сумма: " + mx);
        }
    }
}