import json

def IdToMajor(id):
    with open("./세모톤/BE_14/Changbeom/departments.json", "r", encoding="utf-8") as f: ###############상대경로##################
        departments = json.load(f)

    # name 필드 복원
    for dept in departments:
        if id != dept["id"]:
            continue
        else:
            return dept["name"]

def keyfiltering(item):
    i = 0
    keywords = []

    if (item[2].count("학생설계전공")):
        keywords += ["학생설계전공"]
        i += 1

    if (item[2].count("국제교류")):
        keywords += ["국제교류"]
        i += 1

    if (item[2].count("졸업")):
        keywords += ["졸업"]
        i += 1
    
    if (IdToMajor(item[0]).count("채용") or IdToMajor(item[0]).count("취업") or item[2].count("채용") or item[2].count("취업")):
        keywords += ["취업"]
        i += 1

    if (item[2].count("수강 신청") or item[2].count("수강신청") or item[2].count("희망과목담기") or item[2].count("증원") or item[2].count("폐강")):
        keywords += ["수강신청"]
        i += 1

    if (item[2].count("계절학기")):
        keywords += ["계절학기"]
        i += 1

    if (item[2].count("대회") or item[2].count("공모전")):
        keywords += ["공모전"]
        i += 1

    if (IdToMajor(item[0]).count("장학") or item[2].count("장학") or item[2].count("근로장려금")):
        keywords += ["장학"]
        i += 1

    if (item[2].count("논문")):
        keywords += ["논문"]
        i += 1

    if (IdToMajor(item[0]).count("우정원") or IdToMajor(item[0]).count("세화원") or IdToMajor(item[0]).count("행복기숙사") or IdToMajor(item[0]).count("제2기숙사") or item[2].count("생활관") or item[2].count("기숙사")):
        keywords += ["기숙사"]
        i += 1

    if (item[2].count("모집")):
        keywords += ["모집"]
        i += 1

    if (item[2].count("등록금")):
        keywords += ["등록금"]
        i += 1

    if (item[2].count("연수")):
        keywords += ["연수"]
        i += 1
        
    if (item[2].count("자격증")):
        keywords += ["자격증"]
        i += 1

    if (item[2].count("학점교류")):
        keywords += ["학점교류"]
        i += 1
        
    if (item[2].count("스터디")):
        keywords += ["스터디"]
        i += 1

    if (item[2].count("온라인")):
        keywords += ["온라인"]
        i += 1

    if (item[2].count("성적")):
        if (item[2].count("성적평가")):
            keywords += ["성적평가"]
        else:
            keywords += ["성적"]
        i += 1

    if (item[2].count("행사")):
        keywords += ["행사"]
        i += 1

    if (item[2].count("이벤트")):
        keywords += ["이벤트"]
        i += 1

    if (item[2].count("워크숍")):
        keywords += ["워크숍"]
        i += 1

    if (item[2].count("동아리")):
        keywords += ["동아리"]
        i += 1

    if (item[2].count("강연") or item[2].count("설명회") or item[2].count("특강")):
        keywords += ["강연"]
        i += 1
    
    if (item[2].count("세미나")):
        keywords += ["세미나"]
        i += 1

    if (item[2].count("셔틀버스")):
        keywords += ["설국버스"]
        i += 1

    if (item[2].count("예비군")):
        keywords += ["예비군"]
        i += 1

    if (item[2].count("창업")):
        keywords += ["창업"]
        i += 1

    if (item[2].count("학생회")):
        keywords += ["학생회"]
        i += 1

    if (item[2].count("재수강")):
        keywords += ["재수강"]
        i += 1

    if (item[2].count("복수전공") or item[2].count("다전공") or item[2].count("부전공")):
        keywords += ["다·부전공"]
        i += 1

    if (item[2].count("오리엔테이션")):
        keywords += ["오리엔테이션"]
        i += 1

    if (item[2].count("휴·복학") or item[2].count("휴학") or item[2].count("복학")):
        keywords += ["휴·복학"]
        i += 1

    if (item[2].count("주간메뉴") or item[2].count("주간식단")):
        keywords += ["학식"]
        i += 1

    if (i == 0):
        keywords += ["기타"]

    keywords.sort()

    return keywords

def allkeywords(item):
        keywords = []
        keywords += IdToMajor(item[0]).split(".")[:1]
        keywords.append(item[1][:10])
        keywords += keyfiltering(item)
        return keywords

all = [
    "학생설계전공",
    "국제교류",
    "졸업",
    "취업",
    "수강신청",
    "계절학기",
    "공모전",
    "장학",
    "논문",
    "기숙사",
    "모집",
    "등록금",
    "연수",
    "자격증",
    "학점교류",
    "스터디",
    "온라인",
    "성적평가",
    "성적",
    "행사",
    "이벤트",
    "워크숍",
    "동아리",
    "강연",
    "세미나",
    "설국버스",
    "예비군",
    "창업",
    "학생회",
    "재수강",
    "다·부전공",
    "오리엔테이션",
    "휴·복학",
    "학식",
    "기타"
]

all.sort()
print(all)