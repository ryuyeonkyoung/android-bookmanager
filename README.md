# 📕 도서 관리 어플리케이션
[모바일소프트웨어 수업 과제]

책 정보를 **📖 추가·조회·수정·삭제·검색**할 수 있는 안드로이드 앱입니다.

---

## ✨ 주요 기능
| 기능 | 설명 |
|------|------|
| 🔍 **실시간 검색** | 한 글자 입력할 때마다 검색어를 포함하는 제목·저자 필터링 |
| ➕ **추가** | 새로운 도서 정보 입력 |
| 📝 **수정** | 기존 데이터 업데이트 |
| 🗑️ **삭제** | AlertDialog로 삭제 |
| 📅 **출판일 선택** | `DatePickerDialog` 적용으로 입력 |
| 📃 **리스트 뷰** | `RecyclerView` + ViewBinding을 통한 스크롤 |

---

## 🛠️ 사용 기술
- **Kotlin** & Android SDK
- **SQLite** 로컬 DB
- **ViewBinding**: Null-safety UI 바인딩  
- **RecyclerView**, **AlertDialog**, **DatePickerDialog** 등 고급 위젯 활용

---

## 🚀 실행 방법
1. **Android Studio** → `Open` 프로젝트.
2. 에뮬레이터 또는 실제 기기에서 **Run ▶️**.
3. **➕ 버튼**으로 도서 추가 → 저장 💾.
4. 상단 **검색창**에 제목/저자를 입력해 검색 가능

---

## 📂 폴더 구조
```
app
└─ src
└─ main
├─ java/dduw/com/mobile/finalreport/ # 액티비티·DB·어댑터
├─ res
│ ├─ layout/ # XML 레이아웃
│ └─ drawable/ # 이미지 리소스
└─ AndroidManifest.xml
```

---

## 👩‍💻 개발자
- **류연경** (ryuyeonkyoung)
