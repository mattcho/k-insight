package seung.java.lib.ex.dart;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import seung.java.arg.SMap;
import seung.java.lib.http.SHttpU;

public class SExDartU {

	public static void main(String[] args) {
		
		SMap reqMap = new SMap();
//		reqMap.put("textCrpNm"      , "삼성전자");
		reqMap.put("corporationType", "A");
		reqMap.put("x0101"          , "Y");
		
		SExDartU sExDartU = new SExDartU();
		HttpURLConnection httpURLConnection = null;
		sExDartU.x0100(httpURLConnection, reqMap);
	}
	
	public SMap extract(SMap sMap) {
		
		SMap resultMap = new SMap();
		resultMap.put("resultCode",    "9999");
		resultMap.put("resultMessage", "");
		
		HttpURLConnection dartURLConnection = null;
		try {
			
			// coperation list
			if(sMap.getString("jobCd").indexOf("x0100") > -1) {
				resultMap.put("x0100", x0100(dartURLConnection, sMap));
			}
			// coperation information
			if(sMap.getString("jobCd").indexOf("x0101") > -1) resultMap.put("x0100", x0101(dartURLConnection, sMap));
			// document list
			if(sMap.getString("jobCd").indexOf("x0200") > -1) resultMap.put("x0100", x0200(dartURLConnection, sMap));
			// report list
			if(sMap.getString("jobCd").indexOf("x0300") > -1) resultMap.put("x0100", x0300(dartURLConnection, sMap));
			// report: BS
			if(sMap.getString("jobCd").indexOf("x0301") > -1) resultMap.put("x0100", x0301(dartURLConnection, sMap));
			// report: IS
			if(sMap.getString("jobCd").indexOf("x0302") > -1) resultMap.put("x0100", x0302(dartURLConnection, sMap));
			// report: CF
			if(sMap.getString("jobCd").indexOf("x0303") > -1) resultMap.put("x0100", x0303(dartURLConnection, sMap));
			
			resultMap.put("resultCode", "0000");
			
		} catch (Exception e) {
			resultMap.put("resultCode"   , "9999");
			resultMap.put("resultMessage", e.getMessage());
		}
		
		return resultMap;
	}
	
	private SMap x0100(HttpURLConnection httpURLConnection, SMap sMap) {
		
		int count = 0;
		
		SMap resultMap = new SMap();
		resultMap.put("resultCode"   , "9999");
		resultMap.put("resultMessage", "");
		
		ArrayList<SMap> x0100SL = new ArrayList<SMap>();
		DartX0100VO dartX0100VO = null;
		
		SHttpU sHttpU = null;
		String responseText = null;
		try {
			
			int totalNum = -1;
			int pageNum = -1;
			int currentPage = sMap.containsKey("pageNo") ? sMap.getInt("pageNo") : 1;
			String pageInfo = "";
			Document table = null;
			Elements tds = null;
			while(true) {
				
				if(sMap.containsKey("sleep")) Thread.sleep(sMap.getInt("sleep"));
				sHttpU = new SHttpU();
				
				sHttpU.setRequestUrl("dart.fss.or.kr/corp/searchCorpL.ax");
				sHttpU.setRequestMethod("POST");
				sHttpU.addRequestHeader("Content-Type"      , "application/x-www-form-urlencoded");
				sHttpU.addRequestHeader("X-Requested-With"  , "XMLHttpRequest");
				sHttpU.addRequestParameter("currentPage"    , "" + currentPage);
				sHttpU.addRequestParameter("maxResults"     , "");
				sHttpU.addRequestParameter("maxLinks"       , "");
				sHttpU.addRequestParameter("searchIndex"    , "");
				sHttpU.addRequestParameter("textCrpNmAddPer", "");
				sHttpU.addRequestParameter("textCrpNm"      , "");
				// P: 유가증권시장
				// A: 코스닥시장
				// N: 코넥스시장
				// E: 기타법인
				sHttpU.addRequestParameter("corporationType", sMap.containsKey("corporationType") ? sMap.getString("corporationType") : "");
				sHttpU.setReadTimeout(10 * 1000);
				
				sHttpU.httpRequest();
				
				responseText = sHttpU.getResponseText();
				
				if(responseText == null) throw new Exception("0001||response is null.");
				if(responseText.indexOf("일치하는 회사명이 없습니다.") > -1) throw new Exception("0000||no data.");
				if(responseText.indexOf("<caption>회사명 선택 표</caption>") == -1) throw new Exception("0002||table not exist.");
				
				if(totalNum == -1) {
					pageInfo = responseText.split("<p class=\"page_info\">")[1].split("</p>")[0];
					totalNum = Integer.parseInt(pageInfo.split("\\[")[2].replaceAll("[^0-9]", ""));
					pageNum = Integer.parseInt(pageInfo.split("\\[")[1].split("\\/")[1].replaceAll("[^0-9]", ""));
					currentPage = Integer.parseInt(pageInfo.split("\\[")[1].split("\\/")[0].replaceAll("[^0-9]", ""));
				}
				System.out.println(String.format("%s / %s, %s", "" + currentPage, "" + pageNum, "" + totalNum));
				
				responseText = "<table>" + responseText.split("<caption>회사명 선택 표</caption>")[1].split("</table>")[0] + "</table>";
				
				table = Jsoup.parse(responseText);
				for(Element tr : table.select("tbody").select("tr")) {
					
					tds = tr.select("td");
					dartX0100VO = new DartX0100VO();
					dartX0100VO.setCikCd(tds.get(0).select("input[name=hiddenCikCD1]").val());
					
					// contains detail
					if("Y".equals(sMap.getString("x0101"))) {
						
						if(sMap.containsKey("sleep")) Thread.sleep(sMap.getInt("sleep"));
						sMap.put("cikCd", dartX0100VO.getCikCd());
						dartX0100VO = (DartX0100VO) x0101(httpURLConnection, sMap).convertSMapToObject(dartX0100VO);
						
					} else {
						
						dartX0100VO.setCrpNmKr(tds.get(0).text());
						dartX0100VO.setCeoNm(tds.get(1).text());
						dartX0100VO.setItmCd(tds.get(2).text());
						dartX0100VO.setBizTpNm(tds.get(3).text());
						
					}
					
					x0100SL.add(new SMap(dartX0100VO));
					++count;
					if(count % 10 == 0) System.out.println("count: " + count);
//					if(count % 100 == 0) break;
				}
				
				if(pageNum <= currentPage) break;
				currentPage++;
				
//				break;
			}
			
			resultMap.put("totalNum"   , totalNum);
			resultMap.put("pageNum"    , pageNum);
			resultMap.put("currentPage", currentPage);
			resultMap.put("x0100SL"    , x0100SL);
			resultMap.put("resultCode" , "0000");
			
		} catch (Exception e) {
			e.printStackTrace();
			if(e.getMessage().indexOf("\\|\\|") > -1) {
				resultMap.put("resultCode"   , e.getMessage().split("\\|\\|")[0]);
				resultMap.put("resultMessage", e.getMessage().split("\\|\\|")[1]);
			} else {
				resultMap.put("resultCode"   , "9999");
				resultMap.put("resultMessage", e.getMessage());
			}
		}
		
		return resultMap;
	}
	
	private SMap x0101(HttpURLConnection httpURLConnection, SMap sMap) {
		
		SMap resultMap = new SMap();
		
		resultMap.put("resultCode"   , "9999");
		resultMap.put("resultMessage", "");
		
		DartX0100VO dartX0100VO = null;
		
		SHttpU sHttpU = null;
		String responseText = null;
		try {
			
			Document table = null;
			Elements trs = null;
			
			if(sMap.containsKey("sleep")) Thread.sleep(sMap.getInt("sleep"));
			sHttpU = new SHttpU();
			
			sHttpU.setRequestUrl("dart.fss.or.kr/dsae001/selectPopup.ax");
			sHttpU.setRequestMethod("POST");
			sHttpU.addRequestHeader("Content-Type"    , "application/x-www-form-urlencoded");
			sHttpU.addRequestHeader("X-Requested-With", "XMLHttpRequest");
			sHttpU.addRequestParameter("selectKey"    , sMap.getString("cikCd"));
			// P: 유가증권시장
			// A: 코스닥시장
			// N: 코넥스시장
			// E: 기타법인
			
			sHttpU.httpRequest();
			
			responseText = sHttpU.getResponseText();
			
			if(responseText == null) throw new Exception("0001||response is null.");
			if(responseText.indexOf("<tbody>") == -1) throw new Exception("0001||table not exist.");
			
			responseText = "<table>" + responseText.split("<tbody>")[1].split("</tbody>")[0] + "</table>";
			table = Jsoup.parse(responseText);
			
			trs = table.select("tr");
			
			dartX0100VO = new DartX0100VO();
			dartX0100VO.setCikCd(sMap.getString("cikCd"));
			dartX0100VO.setCrpNmKr(trs.get(0).select("td").get(0).text());
			dartX0100VO.setCrpNmEn(trs.get(1).select("td").get(0).text());
			dartX0100VO.setCrpNmMrk(trs.get(2).select("td").get(0).text());
			dartX0100VO.setItmCd(trs.get(3).select("td").get(0).text());
			dartX0100VO.setCeoNm(trs.get(4).select("td").get(0).text());
			dartX0100VO.setMrkTpNm(trs.get(5).select("td").get(0).text());
			dartX0100VO.setCrpNo(trs.get(6).select("td").get(0).text());
			dartX0100VO.setBizNo(trs.get(7).select("td").get(0).text());
			dartX0100VO.setCrpAddr(trs.get(8).select("td").get(0).text());
			dartX0100VO.setCrpUrl(trs.get(9).select("td").get(0).text());
			dartX0100VO.setCrpUrlIR(trs.get(10).select("td").get(0).text());
			dartX0100VO.setCrpTel(trs.get(11).select("td").get(0).text());
			dartX0100VO.setCrpFax(trs.get(12).select("td").get(0).text());
			dartX0100VO.setBizTpNm(trs.get(13).select("td").get(0).text());
			dartX0100VO.setDtFnd(trs.get(14).select("td").get(0).text().replaceAll("[^0-9]", ""));
			dartX0100VO.setDtFy(trs.get(15).select("td").get(0).text().replaceAll("[^0-9]", ""));
			
			resultMap.convertObjectToSMap(dartX0100VO);
			
		} catch (Exception e) {
			if(e.getMessage().indexOf("\\|\\|") > -1) {
				resultMap.put("resultCode"   , e.getMessage().split("\\|\\|")[0]);
				resultMap.put("resultMessage", e.getMessage().split("\\|\\|")[1]);
			} else {
				resultMap.put("resultCode"   , "9999");
				resultMap.put("resultMessage", e.getMessage());
			}
		}
		
		return resultMap;
	}
	
	private SMap x0200(HttpURLConnection httpURLConnection, SMap sMap) {
		SMap resultMap = new SMap();
		return resultMap;
	}
	
	private SMap x0300(HttpURLConnection httpURLConnection, SMap sMap) {
		SMap resultMap = new SMap();
		return resultMap;
	}
	
	private SMap x0301(HttpURLConnection httpURLConnection, SMap sMap) {
		SMap resultMap = new SMap();
		return resultMap;
	}
	
	private SMap x0302(HttpURLConnection httpURLConnection, SMap sMap) {
		SMap resultMap = new SMap();
		return resultMap;
	}
	
	private SMap x0303(HttpURLConnection httpURLConnection, SMap sMap) {
		SMap resultMap = new SMap();
		return resultMap;
	}
}
