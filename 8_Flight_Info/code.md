# Online Retail System using HBase and HiveQL

## 1. Create Hive Database

```sql
CREATE DATABASE OnlineRetailDB;
USE OnlineRetailDB;
```

---

# 2. Create Hive Table

```sql
CREATE TABLE OnlineRetail(
    InvoiceNo STRING,
    StockCode STRING,
    Description STRING,
    Quantity INT,
    InvoiceDate STRING,
    UnitPrice DOUBLE,
    CustomerID STRING,
    Country STRING
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
TBLPROPERTIES ("skip.header.line.count"="1");
```

## Explanation

- `FIELDS TERMINATED BY ','` → CSV file columns separated by comma
- `skip.header.line.count = 1` → skips column header row from dataset

---

# 3. Load Dataset into Hive

## Copy Dataset to HDFS

```bash
hdfs dfs -mkdir /input
hdfs dfs -put OnlineRetail.csv /input
```

## Load Data into Hive Table

```sql
LOAD DATA INPATH '/input/OnlineRetail.csv'
INTO TABLE OnlineRetail;
```

---

# 4. Display Records

```sql
SELECT * FROM OnlineRetail LIMIT 10;
```

---

# 5. Create Index on OnlineRetail Table

```sql
CREATE INDEX retail_index
ON TABLE OnlineRetail(CustomerID)
AS 'COMPACT'
WITH DEFERRED REBUILD;
```

## Build Index

```sql
ALTER INDEX retail_index
ON OnlineRetail
REBUILD;
```

---

# 6. Find Total Sales

```sql
SELECT SUM(Quantity * UnitPrice) AS Total_Sales
FROM OnlineRetail;
```

---

# 7. Find Average Sales

```sql
SELECT AVG(Quantity * UnitPrice) AS Average_Sales
FROM OnlineRetail;
```

---

# 8. Find Order Details with Maximum Cost

```sql
SELECT
    InvoiceNo,
    Description,
    Quantity,
    UnitPrice,
    (Quantity * UnitPrice) AS TotalCost
FROM OnlineRetail
ORDER BY TotalCost DESC
LIMIT 1;
```

---

# 9. Find Customer Details with Maximum Order Total

```sql
SELECT
    CustomerID,
    SUM(Quantity * UnitPrice) AS OrderTotal
FROM OnlineRetail
GROUP BY CustomerID
ORDER BY OrderTotal DESC
LIMIT 1;
```

---

# 10. Find Country with Maximum Sale

```sql
SELECT
    Country,
    SUM(Quantity * UnitPrice) AS TotalSale
FROM OnlineRetail
GROUP BY Country
ORDER BY TotalSale DESC
LIMIT 1;
```

---

# 11. Find Country with Minimum Sale

```sql
SELECT
    Country,
    SUM(Quantity * UnitPrice) AS TotalSale
FROM OnlineRetail
GROUP BY Country
ORDER BY TotalSale ASC
LIMIT 1;
```

---

# 12. Open HBase Shell

```bash
hbase shell
```

---

# 13. Create HBase Table

```bash
create 'online_retail',
'customer',
'product',
'orderinfo'
```

---

# 14. Insert Sample Data into HBase

```bash
put 'online_retail', '1', 'customer:CustomerID', '17850'
put 'online_retail', '1', 'customer:Country', 'United Kingdom'

put 'online_retail', '1', 'product:StockCode', '85123A'
put 'online_retail', '1', 'product:Description', 'WHITE HANGING HEART T-LIGHT HOLDER'

put 'online_retail', '1', 'orderinfo:Quantity', '6'
put 'online_retail', '1', 'orderinfo:UnitPrice', '2.55'
```

---

# 15. Create External Hive Table Connected to HBase

## Exit HBase Shell and Open Hive

```bash
hive
```

## Create External Hive Table

```sql
CREATE EXTERNAL TABLE hbase_online_retail(
    rowkey STRING,
    CustomerID STRING,
    Country STRING,
    StockCode STRING,
    Description STRING,
    Quantity STRING,
    UnitPrice STRING
)
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES (
    "hbase.columns.mapping" = "
    :key,
    customer:CustomerID,
    customer:Country,
    product:StockCode,
    product:Description,
    orderinfo:Quantity,
    orderinfo:UnitPrice"
)
TBLPROPERTIES ("hbase.table.name" = "online_retail");
```

---

# 16. Display Records from HBase

## Using HBase Shell

```bash
scan 'online_retail'
```

---

## Using Hive

```sql
SELECT * FROM hbase_online_retail;
```