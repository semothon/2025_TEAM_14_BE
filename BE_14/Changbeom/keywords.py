import json
import os
import keyfilter as kf

with open("BE_14\Changbeom\departments.json", "r", encoding="utf-8") as f: ###############상대경로##################
    departments = json.load(f)

# 학과 관련 키워드
keywords_dept = set()
for dept in departments:
    keywords_dept.add(dept["name"].split(".")[0])

# 공지 제목 관련 키워드
keywords_title = kf.fromtitles()

# 키워드 분류
only_keywords = {
    "keywords_dept_number": len(keywords_dept),
    "keywords_dept": list(keywords_dept),
    "keywords_title_number": len(keywords_title),
    "keywords_title": keywords_title
}

# 디렉토리 없으면 생성
output_dir = "BE_14\Changbeom\mnt\data" ###############상대경로###############
os.makedirs(output_dir, exist_ok=True)

output_path = f"{output_dir}/only_keywords.json"

# 저장
with open(output_path, "w", encoding="utf-8") as f:
    json.dump(only_keywords, f, ensure_ascii=False, indent=4)
        
output_path