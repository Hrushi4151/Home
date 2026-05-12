import requests
from bs4 import BeautifulSoup
import pandas as pd

# Website URL
url = "https://webscraper.io/test-sites/e-commerce/static"

# Get webpage
response = requests.get(url)

# Parse HTML
soup = BeautifulSoup(response.text, "html.parser")

# Find all products
products = soup.find_all("div", class_="thumbnail")

# Empty list
data = []

# Loop through products
for product in products:

    # Product name
    name = product.find("a", class_="title").text.strip()

    # Price
    price = product.find("h4", class_="price").text.strip()

    # Reviews count
    reviews = product.find(
        "p",
        class_="pull-right"
    ).text.strip()

    # Rating stars
    rating = len(product.find_all("span", class_="glyphicon-star"))

    # Description / Comment
    comment = product.find(
        "p",
        class_="description"
    ).text.strip()

    # Comment Tag
    tag = "Electronics"

    # Dummy customer name
    customer = "Anonymous User"

    # Store data
    data.append([
        customer,
        name,
        price,
        reviews,
        rating,
        tag,
        comment
    ])

# Create DataFrame
df = pd.DataFrame(data, columns=[
    "Customer_Name",
    "Product_Name",
    "Price",
    "Reviews_Count",
    "Rating",
    "Comment_Tag",
    "Comment"
])

# Print data
print(df)

# Save CSV
df.to_csv("ecommerce_reviews.csv", index=False)

print("\nData saved to ecommerce_reviews.csv")