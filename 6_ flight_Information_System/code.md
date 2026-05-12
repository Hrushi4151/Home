# Flight Information System using HBase and HiveQL

## Step 1: Start HBase and Hive

```bash
start-hbase.sh
hbase shell
```

Open another terminal:

```bash
hive
```

---

# a. Create Flight Info HBase Table

## HBase Table Creation

```bash
create 'flight_info', 'info', 'schedule', 'delay'
```

## Insert Sample Data

```bash
put 'flight_info', 'F101', 'info:flight_name', 'AirIndia'
put 'flight_info', 'F101', 'info:source', 'Delhi'
put 'flight_info', 'F101', 'info:destination', 'Mumbai'

put 'flight_info', 'F101', 'schedule:departure', '10:00'
put 'flight_info', 'F101', 'schedule:arrival', '12:00'

put 'flight_info', 'F101', 'delay:departure_delay', '20'
put 'flight_info', 'F101', 'delay:arrival_delay', '10'
```

## View Table Data

```bash
scan 'flight_info'
```

---

# b. Demonstrate Creating, Dropping, and Altering Tables in HBase

## Create Table

```bash
create 'flight_test', 'details'
```

## List Tables

```bash
list
```

## Alter Table

Add new column family:

```bash
alter 'flight_test', 'extra'
```

## Describe Table

```bash
describe 'flight_test'
```

## Disable and Drop Table

```bash
disable 'flight_test'
drop 'flight_test'
```

---

# c. Create External Hive Table Connected to HBase

## Open Hive Shell

```bash
hive
```

## Create Hive Database

```sql
CREATE DATABASE flightdb;
USE flightdb;
```

## Create External Hive Table Mapping HBase Table

```sql
CREATE EXTERNAL TABLE flight_hive(
    flight_id STRING,
    flight_name STRING,
    source STRING,
    destination STRING,
    departure STRING,
    arrival STRING,
    departure_delay INT,
    arrival_delay INT
)
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES (
    "hbase.columns.mapping" = "
    :key,
    info:flight_name,
    info:source,
    info:destination,
    schedule:departure,
    schedule:arrival,
    delay:departure_delay,
    delay:arrival_delay"
)
TBLPROPERTIES ("hbase.table.name" = "flight_info");
```

## View Data in Hive

```sql
SELECT * FROM flight_hive;
```

---

# d. Find Total Departure Delay in Hive

```sql
SELECT SUM(departure_delay) AS total_departure_delay
FROM flight_hive;
```

---

# e. Find Average Departure Delay in Hive

```sql
SELECT AVG(departure_delay) AS average_departure_delay
FROM flight_hive;
```

---

# f. Create Index on Flight Information Table

```sql
CREATE INDEX flight_index
ON TABLE flight_hive (flight_name)
AS 'COMPACT'
WITH DEFERRED REBUILD;
```

## Build the Index

```sql
ALTER INDEX flight_index ON flight_hive REBUILD;
```