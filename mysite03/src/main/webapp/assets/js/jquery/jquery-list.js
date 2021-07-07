// List
/**
	1. api Controller로부터 처리된 데이터를 받아옴
	2. 받아오는 형식 지정
	3. Object 형식으로 들어가 있는 파일을 뽑아서 vo에 삽입
	4. 여기서 response가 받아온 데이터,
	5. 그 response의 data가 우리가 원하는 그 데이터 
	6. 이걸 이용해서 html 형식으로 만들어 넘겨주기.
 */

var fetch = function(){
	$.ajax({
		url:"${pageContext.request.contextPath }/guestbook/api/list",
		dataType: "json",
		type: "get",
		success: function(response){
			if(response.result != "success"){
				response.error(response.message);
				return;
			};
			response.data.forEach(function(vo){
				html =
					"<li data-no='" + vo.no + "'>" + 
						"<strong>" + vo.name + "</strong>" +
						"<p>" + vo.message + "</p>" +
						"<strong></strong>" + 
						"<a href='' data-no='" + vo.no + "'>삭제</a>" + 
					"</li>";
				$("#list-guestbook").append(html);	
				// no = $(this).data("no");
			});
		}
	})
};

$(function(){
	$("#btn-fetch").click(function(){
		fetch();
	});
	
	// 최초 데이터 가져오기

	fetch();
});