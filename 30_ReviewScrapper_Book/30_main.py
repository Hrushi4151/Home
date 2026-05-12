import requests
from bs4 import BeautifulSoup
import pandas as pd

# Empty list
data = []

# Loop through pages 1 to 5
for page in range(1, 2):

    # Page URL
    url = f"http://books.toscrape.com/catalogue/page-{page}.html"

    # Get webpage
    response = requests.get(url)

    # Parse HTML
    soup = BeautifulSoup(response.text, "html.parser")

    # Find all books
    books = soup.find_all("article", class_="product_pod")

    # Loop through books
    for book in books:

        # Book link
        link = book.h3.a["href"]

        # Full book URL
        book_url = (
            "http://books.toscrape.com/catalogue/" +
            link.replace("../", "")
        )

        # Open book page
        book_response = requests.get(book_url)

        # Parse book page
        book_soup = BeautifulSoup(
            book_response.text,
            "html.parser"
        )

        # Customer Name (Dummy)
        customer = "Anonymous User"

        # Title
        title = book_soup.find("h1").text

        # Rating
        rating = book_soup.find(
            "p",
            class_="star-rating"
        )["class"][1]

        # Price
        price = book_soup.find(
            "p",
            class_="price_color"
        ).text

        # Availability
        availability = book_soup.find(
            "p",
            class_="instock availability"
        ).text.strip()

        # Comment Tag
        category = book_soup.find_all("a")[3].text

        # Review / Comment
        review = book_soup.find(
            "meta",
            attrs={"name": "description"}
        )["content"].strip()

        # Store data
        data.append([
            customer,
            title,
            rating,
            price,
            availability,
            category,
            review
        ])

# Create DataFrame
df = pd.DataFrame(data, columns=[
    "Customer_Name",
    "Book_Title",
    "Rating",
    "Price",
    "Availability",
    "Comment_Tag",
    "Review"
])

# Save CSV file
df.to_csv("books_reviews.csv", index=False)

# Print DataFrame
print(df)

print("\nData saved to books_reviews.csv")