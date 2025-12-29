import java.util.Scanner;

public class MajNumber {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Сколько будет чисел? ");
        int size = sc.nextInt();

        int[] a = new int[size];
        System.out.println("Пиши числа:");
        // Заполняем массивчик
        for (int i = 0; i < size; i++) {
            a[i] = sc.nextInt();
        }

        // Пробуем найти число
        Integer res = getResult(a);

        if (res != null) {
            System.out.println("Главное число: " + res);
        } else {
            System.out.println("Такого числа нет.");
        }
    }

    // Метод перебора
    public static Integer getResult(int[] mas) {
        // Берем каждый элемент по очереди
        for (int i = 0; i < mas.length; i++) {
            int k = 0; // счетчик совпадений

            // Пробегаем по всему массиву и считаем, сколько раз встретилось число
            for (int j = 0; j < mas.length; j++) {
                if (mas[i] == mas[j]) {
                    k++;
                }
            }

            // Если число встречается чаще, чем в половине случаев
            if (k > mas.length / 2) {
                return mas[i];
            }
        }
        // Если ничего не подошло
        return null;
    }
}