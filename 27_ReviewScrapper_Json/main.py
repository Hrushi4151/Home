import requests
import pandas as pd

# Steam CS2 / CSGO App ID = 730
url = "https://store.steampowered.com/appreviews/730?json=1"

# Fetch data from Steam API
response = requests.get(url)

# Convert response into JSON
data = response.json()

# Get all reviews
reviews = data["reviews"]

# Empty list to store review data
review_data = []

print(reviews)
# Loop through reviews
for review in reviews:

    # Customer Name (Steam ID)
    customer_name = review["author"]["personaname"]

    # Review Comment
    comment = review["review"]

    # Rating (Recommended or Not)
    if review["voted_up"]:
        rating = "Positive"
    else:
        rating = "Negative"

    # Comment Tags
    tags = review.get("tags", [])

    # Store data in list
    review_data.append({
        "Customer Name": customer_name,
        "Review": comment,
        "Rating": rating,
        "Tags": ", ".join(tags)
    })

# Convert into DataFrame
df = pd.DataFrame(review_data)

# Save into CSV file
df.to_csv("steam_reviews.csv", index=False)

print("Reviews saved successfully in steam_reviews.csv")