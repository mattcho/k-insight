# Project k-insight

## Installation

## Idea

### Value Proposition
 - SQL 을 모르는 사용자도 조건을 직관적인 User Interface 에서 생성하여 해당 조건에 부합하는 기업을 편리하게 검색할 수 있도록 함.
 - 검색된 기업은 해당 조건과 관련된 data visualization 을 함께 보여 줌으로써 사용자가 그래프, 색깔, 차트 등을 이용해 시각적으로 분석할 수 있도록 함.
 - 다른 사람의 검색 결과 해당 기업이 얼마나 나왔는 지를 함께 보여줌으로써 Social Media 사이트로서의 기능도 제공함.

### Requirements
1. KOSPI/KOSDAQ에 상장되어 있는 모든 기업이 금융감독원 사이트 (dart.fss.or.kr) 에 공시하는 아래 3가지 레포트의 내용을 데이터베이스에 저장한다.
 - 사업보고서 (Optional)
 - 반기보고서 (Optional)
 - 분기보고서
 * 분기보고서의 기재정정 공시도 업데이트 할 수 있어야 함.

2. 분기보고서 내용 중 III. 4. 재무제표 (재무상태표, 손익계산서, 현금흐름표 등) 를 각각 데이터베이스에 저장한다.

궁극적으로 사용자가 다음과 같은 query 를 실행 할 수 있도록 데이터 구조를 설계한다.
 - KOSPI 상장 기업 중 과거 3분기 연속으로 영업이익 증가율이 5%가 넘는 기업 리스트
 - KOSDAQ 상장 기업 중 과거 5년간 배당을 계속했고, 배당수익율이 증가한 기업 리스트
 - KOSPI/KOSDAQ 상장 기업 중 영업활동으로 발생한 현금흐름 항목이 감소하지 않은 기업 리스트






