# Tableau Data Visualization on Retail Dataset

## Step 1 — Load Dataset

1. Open **Tableau**
2. Click:

   - **Text File** or **Excel**
   - Select the **Retail dataset**
   - Open a **New Worksheet**

---

# Step 2 — Create Calculated Field for Total Sales

Go to:

**Analysis → Create Calculated Field**

### Name

```text
total sales
```

### Formula

```tableau
[Quantity] * [Unit Price]
```

Click **OK**.

---

# a. Find and Plot Country Wise Popular Product

## Objective

Find the most popular product in each country based on quantity sold.

---

## Steps

### 1. Drag Fields

| Shelf   | Field         |
| ------- | ------------- |
| Rows    | Country       |
| Rows    | Description   |
| Columns | SUM(Quantity) |

---

### 2. Create Rank Field

Go to:

**Analysis → Create Calculated Field**

### Name

```text
Product Rank
```

### Formula

```tableau
RANK(SUM([Quantity]))
```

---

### 3. Apply Rank Filter

Drag:

```text
Product Rank → Filters
```

Select:

```text
At Most = 1
```

---

### 4. Edit Table Calculation

Right-click:

```text
Product Rank → Edit Table Calculation
```

Choose:

```text
Specific Dimensions
```

### Settings

| Field       | Selection |
| ----------- | --------- |
| Country     | ❌ Unchecked |
| Description | ✅ Checked |

---

## Result

Displays the **top-selling product for each country**.

---

# b. Find and Plot Bottom 10 Products Based on Total Sale

## Objective

Find products with the lowest revenue.

---

## Steps

### 1. Drag Fields

| Shelf   | Field            |
| ------- | ---------------- |
| Rows    | Description      |
| Columns | SUM(total sales) |

---

### 2. Apply Bottom Filter

Right-click:

```text
Description → Filter → Top Tab
```

Select:

```text
Bottom 10 by SUM(total sales)
```

---

### 3. Remove Non-Product Entries

Exclude the following:

```text
AMAZON FEE
Manual
Discount
POSTAGE
DOTCOM POSTAGE
Bank Charges
```

---

## Result

Shows the **bottom 10 products based on revenue**.

---

# c. Find and Plot Top 5 Purchase Order

## Objective

Find highest-value purchase orders.

---

## Important

Convert **Invoice No** to Dimension:

Right-click:

```text
Invoice No → Convert to Dimension
```

---

## Steps

### 1. Drag Fields

| Shelf   | Field            |
| ------- | ---------------- |
| Rows    | Invoice No       |
| Columns | SUM(total sales) |

---

### 2. Apply Top Filter

Right-click:

```text
Invoice No → Filter → Top Tab
```

Select:

```text
Top 5 by SUM(total sales)
```

---

### 3. Sort Descending

Click the **Descending Sort** icon.

---

## Result

Displays the **top 5 purchase orders/invoices**.

---

# d. Find and Plot Most Popular Products Based on Sales

## Objective

Find products generating the highest revenue.

---

## Steps

### 1. Drag Fields

| Shelf   | Field            |
| ------- | ---------------- |
| Rows    | Description      |
| Columns | SUM(total sales) |

---

### 2. Apply Top Filter

Right-click:

```text
Description → Filter → Top Tab
```

Select:

```text
Top 10 by SUM(total sales)
```

---

### 3. Remove Non-Product Entries

Exclude:

```text
POSTAGE
DOTCOM POSTAGE
Manual
Discount
```

---

## Result

Displays the **most profitable products**.

---

# e. Find and Plot Half Yearly Sales for the Year 2011

## Objective

Compare H1 and H2 sales for 2011.

---

## Step 1 — Create Half Year Field

Go to:

```text
Analysis → Create Calculated Field
```

### Name

```text
Half Year
```

### Formula

```tableau
IF DATEPART('month',[Invoice Date]) <= 6 THEN
"H1"
ELSE
"H2"
END
```

---

## Step 2 — Filter Year 2011

Drag:

```text
Invoice Date
```

to **Filters**.

Choose:

```text
Years → 2011
```

---

## Step 3 — Build Chart

| Shelf   | Field            |
| ------- | ---------------- |
| Columns | Half Year        |
| Rows    | SUM(total sales) |

---

## Result

Compares **first-half and second-half sales of 2011**.

---

# f. Find and Plot Country Wise Total Sales Quantity on Geospatial Graph

## Objective

Display country-wise sales quantity on a map.

---

## Steps

### 1. Create Map

Double-click:

```text
Country
```

Tableau automatically generates a **world map**.

---

### 2. Add Quantity

| Field         | Marks Card |
| ------------- | ---------- |
| SUM(Quantity) | Color      |
| SUM(Quantity) | Size       |
| Country       | Label      |

---

### 3. Choose Map Type

Use either:

```text
Filled Map
```

or

```text
Symbol Map
```

---

## Result

Displays **country-wise sales quantity geographically**.

---