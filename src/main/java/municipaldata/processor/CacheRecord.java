package municipaldata.processor;

public final class CacheRecord {
    private long numRecords;
    private double total;

    public CacheRecord(long nr, double t) {
        numRecords = nr;
        total = t;
    }

    public long getCount() {
        return numRecords;
    }

    public double getTotal() {
        return total;
    }
}
