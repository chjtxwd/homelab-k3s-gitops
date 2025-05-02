import json
from influxdb_client import InfluxDBClient, Point
from influxdb_client.client.write_api import SYNCHRONOUS

def main(
    json: object,  # JSON input as a string
    influx_token: str,
    influx_bucket: str,
):
    """
    Send JSON data to InfluxDB.
    
    :param json_str: JSON input as a string
    :param influx_url: InfluxDB URL
    :param influx_token: InfluxDB authentication token
    :param influx_org: InfluxDB organization
    :param influx_bucket: InfluxDB bucket
    """
    # Parse the JSON string into a Python dictionary
    print(json)

    # Initialize InfluxDB client
    influx_url = 'https://influxdb.haijin666.top'
    influx_org = '1d067ea7d9bff576'
    client = InfluxDBClient(url=influx_url, token=influx_token, org=influx_org)
    write_api = client.write_api(write_options=SYNCHRONOUS)

    # Process JSON data and write to InfluxDB
    for entry in json:
        point = Point("real_estate_prices")
        for key, value in entry.items():
            if key == "price":
                point.field(key, float(value))  # Ensure price is a float
            else:
                point.tag(key, str(value))
        write_api.write(bucket=influx_bucket, org=influx_org, record=point)

    # Close the client
    client.close()

    # Return success message
    return {"status": "success", "message": "Data written to InfluxDB"}