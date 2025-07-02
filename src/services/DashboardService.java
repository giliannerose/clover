package services;

import java.util.HashMap;
import java.util.Map;

public class DashboardService {

    public static Map<String, String> getDashboardMetrics() {
        Map<String, String> metrics = new HashMap<>();
        metrics.put("Total Revenue", "₱1,000,000");
        metrics.put("Total Expenses", "₱500,000");
        metrics.put("Net Profit", "₱500,000");
        metrics.put("Active Clients", "25");
        return metrics;
    }
}
