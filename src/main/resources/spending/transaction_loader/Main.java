public class Main {
    public static void main(String[] args) throws Exception {
        Transaction[] txns = TransactionLoader.load(args.length > 0 ? args[0] : "Transaction_Export_07-10-2026.csv");
        System.out.println("Loaded " + txns.length + " transactions");
        System.out.println(txns[0]);
        System.out.println(txns[txns.length - 1]);
    }
}
