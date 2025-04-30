
public class VirtualThreads {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = Thread.ofVirtual().start(() -> {
            System.out.println("Executando em uma thread virtual: " + Thread.currentThread());
        });

        thread.join();
    }
}
