import requests
import json
from datetime import datetime

# 요청 URL
url = "http://163.180.142.196:9090/today_api/2024-10-18"

# 데이터 요청
response = requests.get(url)
data = response.json()

# STACKS 필터링: 2023~2025년 날짜 데이터만 추출
filtered_stacks = [
    stack for stack in data["STACKS"]
    if "2023" <= stack[1][:4] <= "2025"
]

# 필터링된 데이터를 새로운 JSON 구조로 재구성
filtered_data = {
    "generated_time": data["GENERATED_TIME"],
    "stack_number": len(filtered_stacks),
    "stacks": [
        {
            "id": item[0],
            "timestamp": item[1],
            "title": item[2],
            "url": item[3]
        }
        for item in filtered_stacks
    ]
}

# JSON 저장 경로
output_path = "./mnt/data/Connect_API.json"

# 저장
with open(output_path, "w", encoding="utf-8") as f:
    json.dump(filtered_data, f, ensure_ascii=False, indent=4)

output_path