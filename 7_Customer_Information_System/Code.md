# Customer Information System using HBase and HiveQL

## a) Create Tables in Hive

Start Hive:

```bash
hive
```

## Create Database

```sql
CREATE DATABASE customerdb;
USE customerdb;
```

## Create Customer_info Table

```sql
CREATE TABLE Customer_info(
    Cust_ID INT,
    Cust_Name STRING,
    OrderID INT
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ',';
```

## Create order_info Table

```sql
CREATE TABLE order_info(
    OrderID INT,
    ItemID INT,
    Quantity INT
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ',';
```

## Create item_info Table

```sql
CREATE TABLE item_info(
    Item_ID INT,
    Item_Name STRING,
    ItemPrice DOUBLE
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ',';
```

---

# b) Load Data from Local Storage

## Create Sample Files

### customer.txt

```text
1,Rahul,101
2,Amit,102
3,Priya,103
```

### order.txt

```text
101,1001,2
102,1002,3
103,1003,1
```

### item.txt

```text
1001,Laptop,50000
1002,Mouse,500
1003,Keyboard,1000
```

## Move Files to HDFS

```bash
hdfs dfs -mkdir /customerdata

hdfs dfs -put customer.txt /customerdata
hdfs dfs -put order.txt /customerdata
hdfs dfs -put item.txt /customerdata
```

## Load Data into Hive Tables

```sql
LOAD DATA INPATH '/customerdata/customer.txt'
INTO TABLE Customer_info;

LOAD DATA INPATH '/customerdata/order.txt'
INTO TABLE order_info;

LOAD DATA INPATH '/customerdata/item.txt'
INTO TABLE item_info;
```

## Check Data

```sql
SELECT * FROM Customer_info;
SELECT * FROM order_info;
SELECT * FROM item_info;
```

---

# c) Perform Join Tables in Hive

```sql
SELECT
    c.Cust_ID,
    c.Cust_Name,
    o.OrderID,
    i.Item_Name,
    o.Quantity,
    (i.ItemPrice * o.Quantity) AS Total_Cost
FROM Customer_info c
JOIN order_info o
ON c.OrderID = o.OrderID
JOIN item_info i
ON o.ItemID = i.Item_ID;
```

---

# d) Create Index on Customer Information System

```sql
CREATE INDEX cust_index
ON TABLE Customer_info(Cust_Name)
AS 'COMPACT'
WITH DEFERRED REBUILD;
```

## Build Index

```sql
ALTER INDEX cust_index
ON Customer_info
REBUILD;
```

## Show Indexes

```sql
SHOW INDEXES ON Customer_info;
```

---

# e) Find Total and Average Sales

## Total Sales

```sql
SELECT
    SUM(i.ItemPrice * o.Quantity) AS Total_Sales
FROM order_info o
JOIN item_info i
ON o.ItemID = i.Item_ID;
```

## Average Sales

```sql
SELECT
    AVG(i.ItemPrice * o.Quantity) AS Average_Sales
FROM order_info o
JOIN item_info i
ON o.ItemID = i.Item_ID;
```

---

# f) Find Order Details with Maximum Cost

```sql
SELECT
    o.OrderID,
    i.Item_Name,
    o.Quantity,
    (i.ItemPrice * o.Quantity) AS Total_Cost
FROM order_info o
JOIN item_info i
ON o.ItemID = i.Item_ID
ORDER BY Total_Cost DESC
LIMIT 1;
```

---

# g) Create External Hive Table Connected to HBase

## Start HBase

```bash
hbase shell
```

## Create HBase Table

```bash
create 'Customer_hbase', 'info'
```

## Insert Data into HBase

```bash
put 'Customer_hbase', '1', 'info:name', 'Rahul'
put 'Customer_hbase', '1', 'info:order', '101'

put 'Customer_hbase', '2', 'info:name', 'Amit'
put 'Customer_hbase', '2', 'info:order', '102'
```

## Exit HBase Shell

```bash
exit
```

## Open Hive

```bash
hive
```

## Create External Hive Table for HBase

```sql
CREATE EXTERNAL TABLE customer_hive(
    key STRING,
    name STRING,
    orderid STRING
)
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES(
    "hbase.columns.mapping" = ":key,info:name,info:order"
)
TBLPROPERTIES("hbase.table.name" = "Customer_hbase");
```

---

# h) Display Records of Customer Information Table in HBase

## Open HBase Shell

```bash
hbase shell
```

## Display Records

```bash
scan 'Customer_hbase'
```

## Expected Output

```text
ROW   COLUMN+CELL
1     column=info:name, value=Rahul
1     column=info:order, value=101
2     column=info:name, value=Amit
2     column=info:order, value=102
```