import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A single row from the transaction export CSV.
 * Requires Java 16+ (records). See the note in TransactionLoader if you are on an older version.
 */
public record Transaction(
        LocalDate date,
        BigDecimal amount,
        String description,
        String category,
        String categoryGroup,
        String account,
        String toAccount,
        String project,
        String notes) {
}
