package org.example;

import java.util.Scanner;

public class TestCalc {

    // Способ 1: умножение через сложение в цикле
    public static int simpleMult(int a, int b) {
        // Проверяем, будет ли минус (если знаки разные)
        boolean isNeg = (a < 0) ^ (b < 0);
        int x = Math.abs(a);
        int y = Math.abs(b);

        int res = 0;
        // Просто складываем x, y раз
        for (int i = 0; i < y; i++) {
            res += x;
        }
        // Если надо, возвращаем с минусом
        return isNeg ? -res : res;
    }

    // Способ 2: через рекурсию
    public static int recMult(int a, int b) {
        if (b == 0) return 0; // Базовый случай
        if (b > 0) return a + recMult(a, b - 1); // Складываем дальше
        return -recMult(a, -b); // Если b отрицательное
    }

    // Способ 3: метод русского крестьянина
    public static int rusMult(int a, int b) {
        if (a == 0 || b == 0) return 0;

        boolean isNeg = (a < 0) ^ (b < 0);
        int x = Math.abs(a);
        int y = Math.abs(b);

        int res = 0;
        while (y > 0) {
            // Если y нечетное, добавляем x в копилку
            if (y % 2 == 1) {
                res += x;
            }
            // Одно число удваиваем, второе делим пополам
            x = x * 2;
            y = y / 2;
        }
        return isNeg ? -res : res;
    }

    // Способ 4: метод удвоения (похож на предыдущий)
    public static int doubleMult(int a, int b) {
        if (a == 0 || b == 0) return 0;

        boolean isNeg = (a < 0) ^ (b < 0);
        int val1 = Math.abs(a);
        int val2 = Math.abs(b);

        int res = 0;
        while (val2 > 0) {
            // Если не делится на 2 без остатка
            if (val2 % 2 == 1) {
                res += val1;
            }
            // Увеличиваем первое
            val1 += val1;
            // Уменьшаем второе
            val2 /= 2;
        }
        return isNeg ? -res : res;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Первое число: ");
        int n1 = sc.nextInt();
        System.out.print("Второе число: ");
        int n2 = sc.nextInt();

        System.out.println("\n--- Результаты ---");
        System.out.println("Обычным циклом: " + simpleMult(n1, n2));
        System.out.println("Рекурсией: " + recMult(n1, n2));
        System.out.println("По-крестьянски: " + rusMult(n1, n2));
        System.out.println("Удвоением: " + doubleMult(n1, n2));

        // Проверка через обычное умножение
        System.out.println("Проверка (n1 * n2): " + (n1 * n2));
    }
}