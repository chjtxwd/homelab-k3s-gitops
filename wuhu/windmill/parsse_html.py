from bs4 import BeautifulSoup

def main(html: str, city: str):
    # 使用 BeautifulSoup 解析 HTML
    soup = BeautifulSoup(html, 'html.parser')

    # 提取区域和价格信息
    try:
        items = soup.select('.rate-item')
        data = []
        for item in items:
            name = item.select_one('.name').text.strip()
            price = item.select_one('.price-num').text.strip()
            data.append({"city": city,"area": name, "price": price})
    except Exception as e:
        return {"error": f"Error extracting content: {str(e)}"}

    # 返回 JSON 格式数据
    return {"data": data}