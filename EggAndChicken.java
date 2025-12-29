// Класс, который управляет очередностью (монитор)
class Dispute {
    // Флаг: true - очередь яйца, false - очередь курицы
    private boolean isEggTurn = true;

    public synchronized void printEgg() {
        for (int i = 0; i < 5; i++) {
            // Пока не моя очередь - жду
            while (!isEggTurn) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // Печатаем
            System.out.println("Яйцо");
            // Меняем флаг, чтобы теперь печатала курица
            isEggTurn = false;
            // Будим второй поток
            notify();
        }
    }

    public synchronized void printHen() {
        for (int i = 0; i < 5; i++) {
            // Если очередь яйца, то курица ждет
            while (isEggTurn) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Курица");
            // Возвращаем ход яйцу
            isEggTurn = true;
            notify();
        }
    }
}

// Поток для яйца
class ThreadEgg extends Thread {
    private final Dispute d;

    public ThreadEgg(Dispute d) {
        this.d = d;
    }

    @Override
    public void run() {
        d.printEgg();
    }
}

// Поток для курицы
class ThreadHen extends Thread {
    private final Dispute d;

    public ThreadHen(Dispute d) {
        this.d = d;
    }

    @Override
    public void run() {
        d.printHen();
    }
}

public class EggAndChicken {
    public static void main(String[] args) {
        // Создаем общий объект спора
        Dispute d = new Dispute();

        Thread t1 = new ThreadEgg(d);
        Thread t2 = new ThreadHen(d);

        // Запускаем
        t1.start();
        t2.start();

        try {
            // Ждем пока оба закончат
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Конец спора, победила дружба.");
    }
}