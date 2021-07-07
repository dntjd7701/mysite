<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-spa.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
 <script>
var render = function(vo){
	const html = "<li data-no='" + vo.no + "'>" + 
	"<strong>" + vo.name + "</strong>" +
	"<p>" + vo.message + "</p>" +
	"<strong></strong>" + 
	"<a href='' data-no='" + vo.no + "'>삭제</a>" + 
	"</li>";
	return html;
}
var fetch = function(no){
	$.ajax({
		url:"${pageContext.request.contextPath }/guestbook/api/list",
		dataType: "json",
		data: no,
		type: "get",
		success: function(response){
			if(response.result != "success"){
				response.error(response.message);
				return;
			};
			response.data.forEach(function(vo){
				html = render(vo);
				$("#list-guestbook").append(html);
			});
		}
	})
};

$(function(){
	var no = "no=0";
	$("#btn-fetch").click(function(){
		no = "no=" + $("#list-guestbook li:last-child").attr("data-no");
		fetch(no);
	});
	
	// 최초 데이터 가져오기
	
	fetch(no);
});
</script>

<!-- /* guestbook application based on jQuery */

/* < assignment > 
 	1. List(list)
 		- no 기준의 list를 부분적으로(3개 or 5 ..) 가져와서 append(landering)하는 작업.
 		- button event 구현 -> 스크롤 이벤트로 바꾼다. 
 		- no 기준으로 동적 쿼리를 레포지토리에 구현한다.
 		- 렌더링 참고 : frontend-dev/ch08/test/gb/ex1
 
 	2. 메시지 등록(add)
 		- validation
 		- message 기반 dialog plugin 사용법
 		- form submit 막기(페이지 이동이 안돼!)
 		- 데이터 하나를 렌더링(prepend)
 		- 참고:frontend-dev/ch08/test/gb/ex2
 		
 	3. 삭제(delete)
 		- a tag 동작 막기
 		- live event
 		- form 기반 dialog plugin 사용법
 		- 응답에 대해 해당 li 삭제
 		- 비밀번호가 틀린 경우 사용자에게 알려주는 ui(no->0, 삭제 실패)
 		- 삭제가 성공한 경우(no > 0), data-no=10인 li element를 삭제
 		- 참고:frontend-dev/ch08/test/gb/ex3
 */
  -->

</head>
<body>
	<div id="container">
	
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				
				<!-- write(add) -->
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				
				<!-- list -->
				<ul id="list-guestbook">
					<!-- <li data-no=''>
						<strong>지나가다가</strong>
						<p>
							별루입니다.<br>
							비번:1234 -,.-
						</p>
						<strong></strong>
						<a href='' data-no=''>삭제</a> 
					</li> -->
				</ul>
				
				<!-- next list button  -->
				<div style="margin:20px 0 auto 0">
					<button id="btn-fetch">다음 가져오기</button>
				</div>
				
				<!-- delete dialog -->
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			
			<!-- message dialog -->
			<div id="dialog-message" title="" style="display:none">
  				<p></p>
			</div>
							
		</div>
		
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
		
	</div>
</body>
</html>