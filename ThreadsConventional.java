
public class ThreadsConventional extends Thread {

    public static void main (String[] args) {    
        Thread thread = new Thread(() -> {
            System.out.println("Open thread conventional!");
        });
        thread.start();
    }
}