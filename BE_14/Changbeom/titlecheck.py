import re

def is_broken_korean(text):
    """
    한글이 정상적으로 포함되어 있는지 확인하고,
    깨진 문자 (예: 특수 유니코드 문자들이나 알 수 없는 문자들) 위주로 감지합니다.
    """
    # 한글 유니코드 블록 (가-힣, ㄱ-ㅎ, ㅏ-ㅣ 등)
    hangul_regex = re.compile(r'[가-힣ㄱ-ㅎㅏ-ㅣ]+')

    # 비정상 문자 패턴: 한글은 없는데 비표준 문자 (예: кириллица, 라틴 보조, 특수기호 등)가 많은 경우
    messy_unicode_ratio = sum(1 for c in text if ord(c) > 127 and not hangul_regex.match(c)) / max(1, len(text))

    # 한글 포함 여부
    has_korean = bool(hangul_regex.search(text))

    # 깨진 문자가 30% 이상이고, 한글은 없음
    return not has_korean and messy_unicode_ratio > 0.3

'''
text = "[DBВєљьЋ┤в│┤ьЌў] 2024 DBВєљьЋ┤в│┤ьЌў ВІаВъЁВѓгВЏљ Ж│хЖ░юВ▒ёВџЕ вфеВДЉ(~10/4(ЖИѕ)Ж╣їВДђ)"

print(is_broken_korean(text))

text = "채용ㅣ2025년 3월 삼양그룹 신입사원 수시채용 모집 (~3/18(화)) 23:59시까지)"

print(is_broken_korean(text))
'''