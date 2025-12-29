import java.util.Scanner;

public class razminka {

    // 1. Умножение через цикл сложения
    public static int multLoop(int n1, int n2) {
        // Проверяем, будет ли результат с минусом (если знаки разные)
        boolean isMinus = (n1 < 0) ^ (n2 < 0);
        int v1 = Math.abs(n1);
        int v2 = Math.abs(n2);

        int res = 0;
        // Просто прибавляем число v1, v2 раз
        for (int i = 0; i < v2; i++) {
            res += v1;
        }
        // Возвращаем с минусом или без
        return isMinus ? -res : res;
    }

    // 2. Умножение рекурсией
    public static int multRec(int n1, int n2) {
        if (n2 == 0) return 0; // Если умножаем на 0, то всё 0
        if (n2 > 0) return n1 + multRec(n1, n2 - 1); // Складываем дальше
        return -multRec(n1, -n2); // Если второе число отрицательное
    }

    // 3. Метод русского крестьянина (через битовые операции)
    public static int multRus(int n1, int n2) {
        boolean isMinus = (n1 < 0) ^ (n2 < 0);
        // Берем long, чтобы не вылететь за границы при вычислениях
        long v1 = Math.abs((long)n1);
        long v2 = Math.abs((long)n2);

        long res = 0;
        while (v2 > 0) {
            // Если число нечетное (последний бит 1)
            if ((v2 & 1) == 1) {
                res += v1;
            }
            // Сдвигаем биты: v1 умножаем на 2, v2 делим на 2
            v1 <<= 1;
            v2 >>= 1;
        }

        // Ставим знак
        if (isMinus) res = -res;

        // Проверяем, влезет ли результат обратно в int
        if (res > Integer.MAX_VALUE || res < Integer.MIN_VALUE) {
            throw new ArithmeticException("Слишком большое число получилось!");
        }
        return (int) res;
    }

    // 4. Умножение удвоением (арифметический вариант)
    public static int multDouble(int n1, int n2) {
        boolean isMinus = (n1 < 0) ^ (n2 < 0);
        int v1 = Math.abs(n1);
        int v2 = Math.abs(n2);

        int res = 0;
        int cur = v1;

        while (v2 > 0) {
            // Если нечетное
            if (v2 % 2 == 1) {
                res += cur;
            }
            // Удваиваем текущее
            cur += cur;
            // Делим счетчик пополам
            v2 /= 2;
        }
        return isMinus ? -res : res;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Первое число: ");
        int a = sc.nextInt();
        System.out.print("Второе число: ");
        int b = sc.nextInt();

        System.out.println("\n--- Итоги ---");
        System.out.println("Циклом:       " + multLoop(a, b));
        System.out.println("Рекурсией:    " + multRec(a, b));
        System.out.println("По-русски:    " + multRus(a, b));
        System.out.println("Удвоением:    " + multDouble(a, b));
    }
}
