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
			<div id="board">
				<form class="board-form" method="post" action="${pageContext.request.contextPath }/board/sumitmodify">
				<input type="hidden" name=no value="${no }">
					<table class="tbl-ex">
						<tr>
							<th colspan="2">글수정</th>
						</tr>
						<c:forEach items='${viewInfos }' var='viewinfo' >
								<tr>
									<td class="label">제목</td>
									<td><input type="text" name="title" value="${viewinfo.title }"></td>
								</tr>
								<tr>
									<td class="label">내용</td>
									<td>
										<textarea id="content" name="contents">${viewinfo.contents }</textarea>
									</td>
								</tr>
						
						</c:forEach>
					</table>
					
					
					
					<div class="bottom">
						<a href="${pageContext.request.contextPath }/board/view/${no }">취소</a>
						<input type="submit" value="수정" >
					</div>
				</form>				
			</div>
		</div>
		
		
		<c:import url='/WEB-INF/views/includes/navigation.jsp' />
		<c:import url='/WEB-INF/views/includes/footer.jsp' />
		
	</div>
</body>
</html>