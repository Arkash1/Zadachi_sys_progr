import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Класс счета с защитой от одновременного доступа
class Account {
    private double balance;
    // Создаем лок (замок) для синхронизации
    private final Lock lock = new ReentrantLock();

    public Account(double start) {
        this.balance = start;
    }

    // Метод пополнения
    public void deposit(double sum) {
        lock.lock(); // Закрываем доступ другим
        try {
            balance += sum;
            System.out.println("Баланс пополнен: +" + sum + " | Итого: " + balance);
        } finally {
            lock.unlock(); // Обязательно открываем
        }
    }

    // Метод снятия
    public void withdraw(double sum) {
        lock.lock();
        try {
            if (balance >= sum) {
                balance -= sum;
                System.out.println("Сняли со счета: -" + sum + " | Остаток: " + balance);
            } else {
                System.out.println("Ошибка: не хватает денег (" + balance + " < " + sum + ")");
            }
        } finally {
            lock.unlock();
        }
    }

    // Просто узнать баланс
    public double getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }
}

// Поток, который будет кидать деньги на счет
class Worker extends Thread {
    private final Account acc;
    private final Random rnd = new Random();
    private final int cycles; // сколько раз пополнять

    public Worker(Account acc, int cycles) {
        this.acc = acc;
        this.cycles = cycles;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < cycles; i++) {
                // Случайная сумма от 100 до 1000
                double val = 100 + rnd.nextInt(901);
                acc.deposit(val);
                // Спим случайное время
                Thread.sleep(500 + rnd.nextInt(1000));
            }
            System.out.println("Поток пополнения закончил работу.");
        } catch (InterruptedException e) {
            System.out.println("Поток был прерван.");
            Thread.currentThread().interrupt();
        }
    }
}

public class Bank {
    public static void main(String[] args) {
        Account myAcc = new Account(500);
        double goal = 3000; // Цель накопления

        // Создаем поток, который пополнит счет 10 раз
        Worker w = new Worker(myAcc, 10);

        System.out.println("Начало. Баланс: " + myAcc.getBalance());
        System.out.println("Цель: накопить и снять " + goal);

        w.start(); // Поехали

        try {
            // Главный поток ждет, пока воркер закончит
            w.join();

            double total = myAcc.getBalance();
            System.out.println("Итого после всех пополнений: " + total);

            // Проверяем, хватило ли денег
            if (total >= goal) {
                myAcc.withdraw(goal);
                System.out.println("Успех! Сняли нужную сумму.");

                // Если что-то осталось, снимаем под ноль
                double rest = myAcc.getBalance();
                if (rest > 0) {
                    myAcc.withdraw(rest);
                    System.out.println("Забрали остаток: " + rest);
                }
            } else {
                System.out.println("Не накопили нужную сумму (" + goal + "). Снимаем всё что есть.");
                myAcc.withdraw(total);
            }

            System.out.println("Конец работы. Баланс: " + myAcc.getBalance());

        } catch (InterruptedException e) {
            System.out.println("Ошибка в главном потоке.");
        }
    }
}