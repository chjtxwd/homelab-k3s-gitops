def main(html: str,province: str,vegetable: str):
    # 提取区域和价格信息
    data = []
    try:
        # Find all rows in the table (assuming each line is a row)
        rows = html.strip().split('\n')
        # Skip the first row (header) by starting from index 1
        for row in rows[1:]:
            if row.strip():  # Skip empty lines
                # Split the row into columns (assuming columns are separated by spaces)
                columns = row.split()
                if len(columns) >= 4:  # Ensure the row has enough columns
                    variety = columns[1]
                    origin = columns[2]  # 所在产地
                    price = columns[3].replace('元/斤', '')  # 去掉价格中的单位
                    data.append({"province": province,"vegetable": vegetable,"variety": variety, "origin": origin, "price": price})
    except Exception as e:
        return {"error": f"Error extracting content: {str(e)}"}

    # 返回 JSON 格式数据
    return {"data": data}