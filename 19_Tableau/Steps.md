# Tableau 2026.1.1 — Retail Dataset Visualization Steps

## Using Tableau

---

# 1. Import Dataset

## Steps

1. Open Tableau
2. Connect to:
   - Excel file / CSV file
3. Load the Online Retail Dataset
4. Open a worksheet (Sheet 1)

---

# 2. Create Calculated Field — Total Sales

## Steps

Click:

```text
Analysis → Create Calculated Field
```

### Field Name

```text
Total Sales
```

### Formula

```tableau
[Quantity] * [Unit Price]
```

Click:

```text
OK
```

Now **Total Sales** appears under **Measures**.

---

# a. Find and Plot Top 10 Products Based on Total Sale

## Steps

Drag:

- Description → Rows
- Total Sales → Columns

Right-click Description →

```text
Filter
```

Open:

```text
Top
```

Select:

```text
Top 10 by SUM(Total Sales)
```

Click:

```text
OK
```

Click:

```text
Sort Descending
```

## Output

- Horizontal Bar Chart
- Top 10 products by sales

---

# b. Find and Plot Product Contribution to Total Sale

## Steps

Create a new worksheet.

Drag:

- Description → Label
- Total Sales → Size
- Total Sales → Color

In Show Me, select:

```text
Treemap
```

Right-click SUM(Total Sales) →

```text
Quick Table Calculation
→ Percent of Total
```

Enable:

```text
Show Mark Labels
```

## Output

- Treemap showing product contribution percentage

---

# c. Find and Plot Month Wise Sales in Year 2010 in Descending Order

## Steps

Create a new worksheet.

Drag:

- Invoice Date → Filters

Choose:

```text
Years
```

Select:

```text
2010
```

Drag:

- Invoice Date → Rows

From dropdown choose:

```text
Month (blue discrete)
```

Drag:

- Total Sales → Columns

Click:

```text
Sort Descending
```

Enable:

```text
Show Mark Labels
```

## Output

- Month-wise sales chart for 2010 sorted descending

---

# d. Find and Plot Most Loyal Customers Based on Purchase Order

## Steps

Create a new worksheet.

Drag:

- Customer ID → Rows
- Invoice No → Columns

Click dropdown on Invoice No →

```text
Measure → Count (Distinct)
```

Click:

```text
Sort Descending
```

Remove NULL:

Right-click NULL bar →

```text
Exclude
```

Optional:

```text
Filter Top 10 customers
```

## Output

- Loyal customers based on number of orders

---

# e. Find and Plot Yearly Sales Comparison

## Steps

Create a new worksheet.

Drag:

- Invoice Date → Columns

Select:

```text
Year (blue discrete)
```

Drag:

- Total Sales → Rows

From Show Me select:

```text
Line Chart
```

Enable:

```text
Show Mark Labels
```

## Output

- Year-wise sales comparison graph

---

# f. Find and Plot Country Wise Total Sales Price on Geospatial Graph

## Steps

Create a new worksheet.

Double-click:

```text
Country
```

Right-click Country →

```text
Geographic Role → Country/Region
```

On the Marks card:

- Drag Country → Detail
- Drag Country → Label
- Drag Total Sales → Color
- Drag Total Sales → Size

Change Marks type to:

```text
Map
```

Fix unknown countries if prompted:

| Unknown Value | Correct Match |
|---|---|
| EIRE | Ireland |
| RSA | South Africa |

## Output

- Country-wise geospatial sales map

---

# Final Dashboard (Optional)

## Steps

Click:

```text
New Dashboard
```

Drag all sheets into dashboard.

Arrange charts neatly.

Add dashboard title:

```text
Retail Sales Analysis Dashboard
```

---

# Suggested Dashboard Layout

| Top Row | Bottom Row |
|---|---|
| Monthly Sales | Country Map |
| Top Products | Yearly Comparison |

---

# Final Output

The dashboard includes:

- Top-selling products
- Product contribution treemap
- Monthly sales analysis
- Loyal customer analysis
- Yearly sales comparison
- Country-wise geospatial visualization