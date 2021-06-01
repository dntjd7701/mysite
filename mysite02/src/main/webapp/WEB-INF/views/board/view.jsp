<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		
			<c:import url='/WEB-INF/views/includes/header.jsp' />
			
					<div id="content">
						<div id="board" class="board-form">
								<c:forEach items='${viewInfos }' var='viewinfo' >
											<table class="tbl-ex">
												<tr>
													<th colspan="2">글보기</th>
												</tr>
												<tr>
													<td class="label">제목</td>
													<td>${viewinfo.title }</td>
												</tr>
												<tr>
													<td class="label">내용</td>
													<td>
														<div class="view-content">
														<!-- 첫 번째 방법. 단, 글자가 작아짐  -->
															<!--  <pre>${viewinfo.contents}</pre>  -->  
															
														<!-- 두 번재 방법. 자바 코드를 사용해야함. -->
														<!-- fn:replace 쓰기  -->
														
														<!-- 자바 내에서 치환함. -->
															${viewinfo.contents }
															
														</div>
													</td>
												</tr>
											</table>
										</c:forEach>
										
							<div class="bottom">
								<c:if test='${not empty authUser }'>
									<a href="${pageContext.request.contextPath }/board?a=replyform&no=${param.no}">댓글</a>
								</c:if>
								<a href="${pageContext.request.contextPath }/board">글목록</a>
							<c:if test='${authUser.no==param.userNo }'>
								<a href="${pageContext.request.contextPath }/board?a=modifyform&no=${param.no}">글수정</a>
							</c:if>
							</div>
							
						</div>
					</div>
				
				
			<c:import url='/WEB-INF/views/includes/navigation.jsp' />
			<c:import url='/WEB-INF/views/includes/footer.jsp' />
			
		
	</div>
</body>
</html>