import json

def IdToDept(id):
    with open("./BE_14/Changbeom/departments.json", "r", encoding="utf-8") as f: ###############상대경로##################
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

    if (item[2].count("국제교류") or item[2].count("교환학생")):
        keywords += ["국제교류"]
        i += 1

    if (item[2].count("학생증")):
        keywords += ["학생증"]
        i += 1

    if (item[2].count("졸업")):
        keywords += ["졸업"]
        i += 1

    if (item[2].count("캠페인")):
        keywords += ["캠페인"]
        i += 1

    if (item[2].count("학사제도") or item[2].count("학사 제도")):
        keywords += ["학사제도"]
        i += 1

    if (item[2].count("신입생") or item[2].count("신·편입생") or item[2].count("입학")):
        keywords += ["신입"]
        i += 1

    if (item[2].count("편입") or item[2].count("신·편입생")):
        keywords += ["편입"]
        i += 1

    if (item[2].count("유학")):
        keywords += ["유학"]
        i += 1

    if (item[2].count("재입학")):
        keywords += ["재입학"]
        i += 1
    
    if (IdToDept(item[0]).count("채용") or IdToDept(item[0]).count("취업") or item[2].count("채용") or item[2].count("취업") or item[2].count("공채")):
        keywords += ["취업"]
        i += 1

    if (item[2].count("수강 신청") or item[2].count("수강신청") or item[2].count("희망과목") or item[2].count("증원") or item[2].count("폐강")):
        keywords += ["수강신청"]
        i += 1

    if (item[2].count("계절학기") or item[2].count("여름학기") or item[2].count("겨울학기")):
        keywords += ["계절학기"]
        i += 1

    if (item[2].count("대회") or item[2].count("콘테스트") or item[2].count("ontest") or item[2].count("CONTEST")):
        keywords += ["대회"]
        i += 1

    if (item[2].count("출결")):
        keywords += ["출결"]
        i += 1

    if (item[2].count("공모전")):
        keywords += ["공모전"]
        i += 1

    if (IdToDept(item[0]).count("장학") or item[2].count("장학") or item[2].count("근로장려금") or item[2].count("국가근로")):
        keywords += ["장학"]
        i += 1

    if (item[2].count("논문")):
        keywords += ["논문"]
        i += 1

    if (item[2].count("사업")):
        keywords += ["사업"]
        i += 1

    if (item[2].count("방학")):
        keywords += ["방학"]
        i += 1

    if (item[2].count("휴무")):
        keywords += ["휴무"]
        i += 1

    if (IdToDept(item[0]).count("우정원") or IdToDept(item[0]).count("세화원") or IdToDept(item[0]).count("행복기숙사") or IdToDept(item[0]).count("제2기숙사") or item[2].count("생활관") or item[2].count("기숙사")):
        keywords += ["기숙사"]
        i += 1

    if (item[2].count("모집") or item[2].count("공모")):
        keywords += ["모집"]
        i += 1
    if (item[2].count("전과")):
        keywords += ["전과"]
        i += 1

    if (item[2].count("신청")):
        keywords += ["신청"]
        i += 1

    if (item[2].count("홍보")):
        keywords += ["홍보"]
        i += 1

    if (item[2].count("학점")):
        keywords += ["학점"]
        i += 1

    if (item[2].count("도서관") or item[2].count("자료실")):
        keywords += ["공공시설"]
        i += 1
        
    if (item[2].count("개최")):
        keywords += ["개최"]
        i += 1

    if (item[2].count("프로그램") or item[2].count("rogram") or item[2].count("PROGRAM")):
        keywords += ["프로그램"]
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
        keywords += ["성적"]
        i += 1

    if (item[2].count("행사") or item[2].count("estival") or item[2].count("FESTIVAL") or item[2].count("페스티벌")):
        keywords += ["행사"]
        i += 1

    if (item[2].count("이벤트")):
        keywords += ["이벤트"]
        i += 1

    if (item[2].count("일정")):
        keywords += ["일정"]
        i += 1

    if (item[2].count("워크숍")):
        keywords += ["워크숍"]
        i += 1

    if (item[2].count("동아리")):
        keywords += ["동아리"]
        i += 1

    if (item[2].count("서비스")):
        keywords += ["서비스"]
        i += 1

    if (item[2].count("인턴") or item[2].count("Interns") or item[2].count("INTERNS")):
        keywords += ["인턴"]
        i += 1

    if (item[2].count("강연") or item[2].count("설명회") or item[2].count("특강")):
        keywords += ["강연"]
        i += 1

    if (item[2].count("특강")):
        keywords += ["특강"]
        i += 1

    if (item[2].count("설명회")):
        keywords += ["설명회"]
        i += 1
    
    if (item[2].count("세미나") or item[2].count("eminar") or item[2].count("SEMINAR")):
        keywords += ["세미나"]
        i += 1

    if (item[2].count("셔틀버스") or item[2].count("설국버스")):
        keywords += ["설국버스"]
        i += 1

    if (item[2].count("강의평가")):
        keywords += ["강의평가"]
        i += 1

    if (item[2].count("학생만족도") or item[2].count("학생 만족도") or item[2].count("설문조사")):
        keywords += ["설문조사"]
        i += 1
    
    if (item[2].count("상담")):
        keywords += ["상담"]
        i += 1

    if (item[2].count("실습")):
        keywords += ["실습"]
        i += 1

    if (item[2].count("지침")):
        keywords += ["지침"]
        i += 1

    if (item[2].count("축제")):
        keywords += ["축제"]
        i += 1

    if (item[2].count("행정실")):
        keywords += ["행정실"]
        i += 1

    if (item[2].count("연구")):
        keywords += ["연구"]
        i += 1

    if (item[2].count("학위수여식")):
        keywords += ["학위수여식"]
        i += 1
    
    if (item[2].count("예비군")):
        keywords += ["예비군"]
        i += 1

    if (item[2].count("창업")):
        keywords += ["창업"]
        i += 1
        
    if (item[2].count("개강")):
        keywords += ["개강"]
        i += 1

    if (item[2].count("강의") or item[2].count("교과목") or item[2].count("강좌")):
        keywords += ["강의"]
        i += 1

    if (item[2].count("학생회")):
        keywords += ["학생회"]
        i += 1

    if (item[2].count("재수강")):
        keywords += ["재수강"]
        i += 1

    if (item[2].count("캠프")):
        keywords += ["캠프"]
        i += 1

    if (item[2].count("포럼") or item[2].count("컨퍼런스") or item[2].count("onference") or item[2].count("CONFERENCE") or item[2].count("orum") or item[2].count("FOFUM")):
        keywords += ["컨퍼런스"]
        i += 1

    if (item[2].count("복수전공") or item[2].count("다전공") or item[2].count("부전공")):
        keywords += ["다/부전공"]
        i += 1

    if (item[2].count("오리엔테이션")):
        keywords += ["오리엔테이션"]
        i += 1

    if (item[2].count("중간시험") or item[2].count("중간고사")):
        keywords += ["중간고사"]
        i += 1

    if (item[2].count("기말시험") or item[2].count("기말고사")):
        keywords += ["기말고사"]
        i += 1

    if (item[2].count("휴·복학") or item[2].count("휴학")):
        keywords += ["휴학"]
        i += 1
        
    if (item[2].count("휴·복학") or item[2].count("복학")):
        keywords += ["복학"]
        i += 1

    if (item[2].count("1학기") or item[2].count("-1 ")):
        keywords += ["1학기"]
        i += 1
        
    if (item[2].count("2학기") or item[2].count("-2 ")):
        keywords += ["2학기"]
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
    keywords += IdToDept(item[0]).split(".")[:1]
    keywords.append(item[1][:10])
    keywords += keyfiltering(item)
    return keywords

all = [
    "학생설계전공", "국제교류", "학생증", "졸업", "캠페인", "학사제도", "신입", "편입", "유학", "재입학",
    "취업", "수강신청", "계절학기", "대회", "출결", "공모전", "장학", "논문", "사업", "방학", "휴무",
    "기숙사", "모집", "전과", "신청", "홍보", "학점", "공공시설", "개최", "프로그램", "등록금", "연수",
    "자격증", "학점교류", "스터디", "온라인", "성적", "행사", "이벤트", "일정", "워크숍", "동아리",
    "서비스", "인턴", "강연", "특강", "설명회", "세미나", "설국버스", "강의평가", "설문조사", "상담",
    "실습", "지침", "축제", "행정실", "연구", "학위수여식", "예비군", "창업", "개강", "강의", "학생회",
    "재수강", "캠프", "컨퍼런스", "다/부전공", "오리엔테이션", "중간고사", "기말고사", "휴학", "복학",
    "1학기", "2학기", "학식", "기타"
]

def fromtitles():
    all.sort()
    return all

print(fromtitles())