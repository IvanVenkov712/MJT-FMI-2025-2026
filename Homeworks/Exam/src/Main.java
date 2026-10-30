public class Main {
    final Object mutex = new Object();
    static int i;
    static void main() {
        this.foo();
        mutex.toString();
        Main m = new Main();
        m.foo();
    }



    public void foo() {
        synchronized (mutex) {
            System.out.println("Hello, world");
            notifyAll();
        }
    }
}
