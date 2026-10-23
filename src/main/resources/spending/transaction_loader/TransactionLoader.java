import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads the transaction export CSV and returns its rows as a Transaction[].
 *
 * Usage:
 *   Transaction[] txns = TransactionLoader.load("Transaction_Export_07-10-2026.csv");
 */
public final class TransactionLoader {

    private static final int EXPECTED_COLUMNS = 9;

    private TransactionLoader() { }

    public static Transaction[] load(String filePath) throws IOException {
        return load(Path.of(filePath));
    }

    public static Transaction[] load(Path path) throws IOException {
        List<Transaction> result = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line = reader.readLine();          // header row - skipped
            if (line == null) {
                return new Transaction[0];
            }
            int lineNo = 1;
            while ((line = reader.readLine()) != null) {
                lineNo++;
                if (line.isBlank()) {
                    continue;
                }
                List<String> f = parseLine(line);
                if (f.size() != EXPECTED_COLUMNS) {
                    throw new IOException("Line " + lineNo + ": expected " + EXPECTED_COLUMNS
                            + " columns but found " + f.size());
                }
                try {
                    result.add(new Transaction(
                            LocalDate.parse(f.get(0).trim()),
                            new BigDecimal(f.get(1).trim()),
                            f.get(2), f.get(3), f.get(4), f.get(5), f.get(6), f.get(7),
                            f.get(8).trim()));
                } catch (RuntimeException e) {
                    throw new IOException("Line " + lineNo + ": could not parse row - " + e.getMessage(), e);
                }
            }
        }
        return result.toArray(new Transaction[0]);
    }

    /** Splits one CSV line, handling quoted fields, commas inside quotes and "" escapes. */
    private static List<String> parseLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (inQuotes) {
                if (c == '"') {
                    if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        sb.append('"');               // escaped quote
                        i++;
                    } else {
                        inQuotes = false;
                    }
                } else {
                    sb.append(c);
                }
            } else if (c == '"') {
                inQuotes = true;
            } else if (c == ',') {
                fields.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        fields.add(sb.toString());
        return fields;
    }
}
