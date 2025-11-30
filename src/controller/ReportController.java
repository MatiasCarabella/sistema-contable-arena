package controller;

import model.Expense;
import model.Income;
import model.Transaction;
import service.ReportService;
import java.time.LocalDate;
import java.util.List;

/**
 * @deprecated Use {@link ReportService} directly instead
 */
@Deprecated(since = "2.0", forRemoval = true)
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    public List<Income> getIncomesByClient(int clientId) {
        return reportService.getIncomesByClient(clientId);
    }

    public List<Expense> getExpensesBySupplier(int supplierId) {
        return reportService.getExpensesBySupplier(supplierId);
    }

    public List<Transaction> getTransactionsBetween(LocalDate from, LocalDate to) {
        return reportService.getTransactionsBetween(from, to);
    }
}
