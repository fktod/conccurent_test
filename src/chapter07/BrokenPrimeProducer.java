package chapter07;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BrokenPrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;
    private volatile boolean cancelled = false;

    BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void run() {
        BigInteger p = BigInteger.ONE;
        try {
            while (!cancelled) {
                queue.put(p = p.nextProbablePrime());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void cancel() {this.cancelled = true;}

    void consumePrimes() throws InterruptedException{
        BlockingQueue<BigInteger> queue=new LinkedBlockingQueue<>();
        BrokenPrimeProducer producer = new BrokenPrimeProducer(queue);
        producer.start();
        try {
            while (needMorePrimes()) {
                consume(queue.take());
            }
        }finally {
            producer.cancel();
        }
    }

    boolean needMorePrimes(){ return true;}

    void consume(BigInteger prime){}
}
