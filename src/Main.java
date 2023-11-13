import java.util.Random;

class Pintura {
    private int rojo;
    private int verde;
    private int azul;

    public Pintura(int rojo, int verde, int azul) {
        this.rojo = rojo;
        this.verde = verde;
        this.azul = azul;
    }

    public synchronized void tomarPintura() {
        Random rand = new Random();
        int cantidadRojo = rand.nextInt(2) + 1;
        int cantidadVerde = rand.nextInt(2) + 1;
        int cantidadAzul = rand.nextInt(2) + 1;

        if (rojo >= cantidadRojo && verde >= cantidadVerde && azul >= cantidadAzul) {
            rojo -= cantidadRojo;
            verde -= cantidadVerde;
            azul -= cantidadAzul;
            System.out.println(Thread.currentThread().getName() + " tomó pintura. Estado actual: Rojo = " + rojo + ", Verde = " + verde + ", Azul = " + azul);
        } else {
            System.out.println(Thread.currentThread().getName() + " intentó tomar pintura, pero no hay suficiente. Esperando a que se rellene.");
        }

    }

    public synchronized void rellenarPintura() {
        rojo += 10;
        verde += 10;
        azul += 10;
        System.out.println("El peón rellenó los botes de pintura. Estado actual: Rojo = " + rojo + ", Verde = " + verde + ", Azul = " + azul);
    }
}

class Pintor implements Runnable {
    private Pintura pintura;

    public Pintor(Pintura pintura) {
        this.pintura = pintura;
    }

    @Override
    public void run() {
        try {
            while (true) {
                pintura.tomarPintura();
                Thread.sleep((long) (Math.random() * 120 + 60)); // Entre 1 y 2 horas de trabajo
                System.out.println(Thread.currentThread().getName() + " está descansando.");
                Thread.sleep((long) (Math.random() * 300 + 900)); // Entre 15 y 20 minutos de descanso
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Peon implements Runnable {
    private Pintura pintura;

    public Peon(Pintura pintura) {
        this.pintura = pintura;
    }

    @Override
    public void run() {
        try {
            while (true) {
                pintura.rellenarPintura();
                Thread.sleep(2000); // Cada 2 horas rellenar pintura
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Pintura pintura = new Pintura(30, 30, 30); // Inicialmente, 3 botes de 10 litros cada uno

        Thread peonThread = new Thread(new Peon(pintura));
        peonThread.start();

        for (int i = 1; i <= 10; i++) {
            Thread pintorThread = new Thread(new Pintor(pintura), "Pintor " + i);
            pintorThread.start();
        }
    }
}