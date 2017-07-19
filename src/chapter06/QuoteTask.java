package chapter06;

import java.util.*;
import java.util.concurrent.*;

public class QuoteTask implements Callable<TravelQuote> {
    private final ExecutorService exec = Executors.newFixedThreadPool(100);
    private final TravelCompany company;
    private final TravelInfo travelInfo;

    public QuoteTask(TravelCompany company, TravelInfo travelInfo) {
        this.company = company;
        this.travelInfo = travelInfo;
    }

    @Override
    public TravelQuote call() throws Exception {
        return company.solicitQuote(travelInfo);
    }

    TravelQuote getFailureQuote(Throwable cause) {
        return null;
    }

    TravelQuote getTimeoutQuote(CancellationException e) {
        return null;
    }

    public List<TravelQuote> getRankedTravelQuote(
            TravelInfo travelInfo, Set<TravelCompany> companies,
            Comparator<TravelQuote> ranking, long time, TimeUnit unit
    )throws InterruptedException{
        ArrayList<QuoteTask> tasks = new ArrayList<>();
        for(TravelCompany company:companies){
            tasks.add(new QuoteTask(company, travelInfo));
        }
        List<Future<TravelQuote>> futures = exec.invokeAll(tasks, time, unit);
        List<TravelQuote> quotes = new ArrayList<>(tasks.size());
        Iterator<QuoteTask> iterator = tasks.iterator();
        for(Future<TravelQuote> f:futures){
            QuoteTask task = iterator.next();
            try {
                quotes.add(f.get());
            } catch (ExecutionException e) {
                quotes.add(task.getFailureQuote(e.getCause()));
            } catch (CancellationException e){
                quotes.add(task.getTimeoutQuote(e));
            }
        }
        Collections.sort(quotes, ranking);
        return quotes;
    }
}


class TravelInfo {}

class TravelCompany {
    TravelQuote solicitQuote(TravelInfo info) {
        return null;
    }
}

class TravelQuote {}
