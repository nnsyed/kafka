import requests

base_url = "http://localhost:8081/orders"

# Optional headers (uncomment if needed)
headers = {
    # "Content-Type": "application/json",
    # "Authorization": "Bearer YOUR_TOKEN_HERE",
}

# Using a Session reuses the underlying TCP connection, making the loop much faster
with requests.Session() as session:
    session.headers.update(headers)

    for order_id in range(100, 141):  # 141 is excluded, so this runs 100 through 140
        url = f"{base_url}/{order_id}"
        
        # If your endpoint expects a JSON body, update this dictionary
        payload = None
        # payload = {"orderId": order_id, "status": "processed"}

        try:
            response = session.post(url, json=payload)
            response.raise_for_status()
            print(f"[{order_id}] Success ({response.status_code}): {response.text}")

        except requests.exceptions.RequestException as e:
            print(f"[{order_id}] Failed: {e}")
