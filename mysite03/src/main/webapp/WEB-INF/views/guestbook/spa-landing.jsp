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
<!-- alert를 이쁘게 꾸미기 위한 dialog 받기 -->
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<!-- alert를 이쁘게 꾸미기 위한 dialog 받기 -->
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<!-- Template(rendering) EJS -->
<script type="text/javascript" src="${pageContext.request.contextPath }/ejs/ejs.js"></script>
 <script>
 
 /* flag */
 var isEnd = false;
 
/* EJS */
/* Template Mapping EJS 객체 생성 */
var listEJS = new EJS({
		url : "${pageContext.request.contextPath }/ejs/list-template.ejs",
	})
var listItemEJS = new EJS({
	url : "${pageContext.request.contextPath }/ejs/listitem-template.ejs",
})
/*  message dialog  */
// alert는 구리니까, 이쁜 dialog쓰자. jQuery ui 쓰면 돼 
var messageDialog = function(message, callback){
	$("#dialog-message").dialog({
		modal: true,
		buttons: {
			"확인": function(){
				$(this).dialog("close");
			}
		},
		close: callback
	});
	$("#dialog-message p").text(message);
}


/* Scroll  */

// jQueyr API를 사용하기 위해 window를 둘러쌈 
$(window).scroll(function(){
			
	// 여기서의 this는 윈도우이다.
	// window height는 고정적이지 않기 때문에(사용자의 창 크기 변형)
	// 이벤트에 따라 구해져야한다.
	var windowHeight = $(this).height();
	var scrollTop = $(this).scrollTop();
	var documentHeight = $(document).height();
	
	// 공식
	if(scrollTop+windowHeight+40 > documentHeight){
			const no = $("#list-guestbook li:last-child").attr("data-no");
			fetch(no);
	} 
});
	
/* List */ 
var fetch = function(no){
	$("#btn-fetch").click(function(){
		no = $("#list-guestbook li:last-child").attr("data-no");
		fetch(no);
	}); 
	
	if(isEnd){
		return;
	}
	$.ajax({
		url:"${pageContext.request.contextPath }/guestbook/api/" + no,
		dataType: "json",
		type: "get",
		success: function(response){
			if(response.result != "success"){
				response.error(response.message);
				return;
			};
			
			// detect end
			if(response.data.length === 0){
				isEnd = true;
				// attr이 아닌 property 사용해줘야한다.
				$("#btn-fetch").prop("disabled", true);
				return;
			}
			
			// rendering
				html = listEJS.render(response);
				$("#list-guestbook").append(html);
			}
		});
};
/* List */

/* Add */
var add = function(){
	$("#add-form").submit(function(event){
		event.preventDefault(); // event 막기 (submit 막기)
		vo = {}; // add-form 의 데이터 받기용
		vo.name = $("#input-name").val(); //form의 input데이터 받아서 담기.
		vo.password = $("#input-password").val();
		vo.message = $("#tx-content").val();
		/* Validations */
		if(vo.name == ""){
			messageDialog("이름을 입력해주세요.");
			return;
		}
		if(vo.password == ""){
			messageDialog("비밀번호를 입력해주세요.");
			return;
		}
		if(vo.message == ""){
			messageDialog("내용을 입력해주세요.");
			return;
		}
		
		// ajax
		$.ajax({
				url: "${pageContext.request.contextPath }/guestbook/api",
				dataType: "json",
				type: "post",
				contentType: "application/json",
				data: JSON.stringify(vo),
				success : function(response){ // callback
					if(response.result != "success"){
						response.error(response.message);
						return;
					};
					/* EJS내부 함수 render 사용, Mapping한 템플릿으로 적용 */
					//rendering
					html = listItemEJS.render(response.data);
					$("#list-guestbook").prepend(html);
					
					// form reset
					$("#add-form")[0].reset();
				},
				error: function(xhr, status, e){
					console.error(status + ":" + e);
				}
			});
		
		});
};
/* Add */

/* delete */
var del = function(){
	//live event: 존재하지 않는 element의 이벤트 핸들러를 미리 등록
	// delegation(위임 -> document)
	$(document).on("click", "#list-guestbook li a", function(event){
		event.preventDefault();
		
		let no = $(this).data("no");
		$("#hidden-no").val(no);
		
		deleteDialog.dialog("open");
	});
	
	// 삭제 다이얼로그 만들기 
	const deleteDialog = $("#dialog-delete-form").dialog({
		autoOpen:false,
		width: 400,
		height: 250,
		modal: true,
		buttons: {
			"삭제": function(){
				const no = $("#hidden-no").val();
				const password = $("#password-delete").val();
				// ajax
				$.ajax({
						url: "${pageContext.request.contextPath }/guestbook/api/" + no,
						dataType: "json",
						type: "delete",
						data: "password=" + password,
						success : function(response){ // callback
							if(response.result != "success"){
								// 비밀번호가 틀린경우
								$(".validateTips.error").show();
								console.error(response.message);
								$("#password-delete").val("");
								return;
							} 
							// 삭제 처리 
							if(response.data != -1){
								$("#list-guestbook li[data-no=" + response.data.no + "]").remove();
								deleteDialog.dialog("close");
								return;
							}
						
						},
						error: function(xhr, status, e){
							console.error(status + ":" + e);
						}
					});
				},
			"취소": function(){
				/* 비밀번호 초기화 작업이 필요. */
				$(this).dialog("close");	
				$("#password-delete").val("");
			}
		},
		close: function(){
			// 1. password 비우기
			// 2. no 비우기
			// 3. error message 숨기기 
			$("#password-delete").val("");
			$("#hidden-no").val("");
			$(".validateTips.error").hide();
			
		}
	});
};
/* delete */

/* main */

$(function(){
	/* List */
		var no = 0;
		// 최초 데이터 가져오기
		fetch(no);
	
	/* Add */	
		
	add();
	
	/* Delete  */
	
	del();
	
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
			<div id="dialog-message" title="ERROR" style="display:none">
  				<p></p>
			</div>
							
		</div>
		
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
		
	</div>
</body>
</html>