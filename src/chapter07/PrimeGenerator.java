package chapter07;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;


public class PrimeGenerator implements Runnable {
    private final List<BigInteger> primes = new ArrayList<>();
    private volatile boolean cancelled;
    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;
        while(!cancelled){
            p = p.nextProbablePrime();
            synchronized (this) {
                primes.add(p);
            }
        }
    }

    public void cancel(){ cancelled=true;}

    public synchronized List<BigInteger> get(){
        return new ArrayList<>(primes);
    }

    List<BigInteger> aSecondOfPrimes() throws InterruptedException{
        PrimeGenerator primeGenerator = new PrimeGenerator();
        new Thread(primeGenerator).start();
        try {
            SECONDS.sleep(1);
        }finally{
            primeGenerator.cancel();
        }
        return primeGenerator.get();
    }
}
