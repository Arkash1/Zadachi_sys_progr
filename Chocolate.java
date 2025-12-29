import java.util.Scanner;

public class Chocolate {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Сколько есть денег: ");
        int sum = sc.nextInt();

        System.out.print("Цена одной шоколадки: ");
        int price = sc.nextInt();

        System.out.print("Сколько фантиков нужно на обмен: ");
        int k = sc.nextInt();

        // Считаем результат
        int res = count(sum, price, k);
        System.out.println("Всего можно съесть: " + res);
    }

    // Метод для подсчета шоколадок
    public static int count(int money, int cost, int swapRate) {
        // Скупаем на все деньги
        int total = money / cost;
        // Сейчас у нас столько же фантиков, сколько купили
        int wrappers = total;

        // Пока фантиков хватает на обмен
        while (wrappers >= swapRate) {
            // Сколько новых дадут за фантики
            int newChoco = wrappers / swapRate;

            // Добавляем к общему количеству
            total += newChoco;

            // Считаем оставшиеся фантики: остаток от деления + фантики от новых шоколадок
            wrappers = (wrappers % swapRate) + newChoco;
        }

        return total;
    }
}