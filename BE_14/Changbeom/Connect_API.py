import requests
import os
import json
from datetime import datetime, timedelta

def IdToMajor(id):
    with open("./세모톤/BE_14/Changbeom/departments.json", "r", encoding="utf-8") as f: ###############상대경로##################
        departments = json.load(f)

    # name 필드 복원
    for dept in departments:
        if id != dept["id"]:
            continue
        else:
            return dept["name"]

url_basic = "http://163.180.142.196:9090/today_api/"
date = datetime.strptime("2023-01-01", "%Y-%m-%d")
tomorrow = (datetime.today() + timedelta(days=1)).strftime("%Y-%m-%d")[:10]

while (date.strftime("%Y-%m-%d")[:10] != tomorrow):
    date_str = str(date)[:10]

    # 요청 URL
    url = url_basic + date_str

    # 데이터 요청
    response = requests.get(url)
    data = response.json()

    # STACKS 필터링
    try:
        filtered_stacks = [
            stack for stack in data["STACKS"]
        ]
        # 필터링된 데이터를 새로운 JSON 구조로 재구성
        filtered_data = {
            "generated_time": data["GENERATED_TIME"],
            "stack_number": len(filtered_stacks),
            "stacks": [
                {
                    "id": item[0],
                    "department": IdToMajor(item[0]),
                    "timestamp": item[1],
                    "title": item[2],
                    "url": item[3]
                }
                for item in filtered_stacks
            ]
        }

        # 디렉토리 없으면 생성
        output_dir = f"./세모톤/BE_14/Changbeom/mnt/data/{date_str[:4]}" ###############상대경로###############
        os.makedirs(output_dir, exist_ok=True)

        output_path = f"{output_dir}/{date_str}.json"

        # 저장
        with open(output_path, "w", encoding="utf-8") as f:
            json.dump(filtered_data, f, ensure_ascii=False, indent=4)

        output_path
    except:
        pass

    date += timedelta(days=1)