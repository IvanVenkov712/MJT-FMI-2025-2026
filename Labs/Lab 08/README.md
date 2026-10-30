# Lab 08: Order Analysis

Analyze sales orders from CSV data using Java records, streams, grouping, and aggregation.

## Features

- Retrieve all orders or orders for a customer.
- Find the busiest date, most ordered products, and location with the most orders.
- Calculate revenue by category and identify suspicious customers.
- Find common payment methods and group orders by category and status.

The source package is `bg.sofia.uni.fmi.mjt.order`. `domain/` contains `Order` and its enums, `loader/OrderLoader` reads CSV rows, and `analyzer/OrderAnalyzerImpl` implements `OrderAnalyzer`.

## Data and usage

The included dataset is [amazon_sales_data 2025.csv](amazon_sales_data%202025.csv). `OrderLoader.load(Reader)` skips the header and parses the remaining rows into orders. Dates use `dd-MM-yy`; parsing assumes comma-separated fields without embedded quoted commas.

Use JDK 25 and the [shared compilation instructions](../../README.md#compile-from-powershell). Production code requires only the JDK. The following can be used in a driver program or JShell after compilation, with this lab as the working directory:

```java
import java.nio.file.Files;
import java.nio.file.Path;
import bg.sofia.uni.fmi.mjt.order.loader.OrderLoader;
import bg.sofia.uni.fmi.mjt.order.analyzer.OrderAnalyzerImpl;

try (var reader = Files.newBufferedReader(Path.of("amazon_sales_data 2025.csv"))) {
    var analyzer = new OrderAnalyzerImpl(OrderLoader.load(reader));
    System.out.println(analyzer.revenueByCategory());
}
```

## Tests

There is no console entry point. Mark `test/` as a test source root, configure JUnit Jupiter, and run the suites for order parsing, loading, and analysis in the IDE.

[Repository overview and dependency setup](../../README.md)
