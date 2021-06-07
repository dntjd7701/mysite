<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board?search" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				
				
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<!-- ${vo.depth*20} -->	
					
					
					<c:set var='count' value="${fn:length(lists) }" />
					<c:forEach items='${lists }' var='list' varStatus='status' >			
					<tr>
						<td>${map.count - status.index }</td>
						<td style="text-align:left; padding-left:${list.depth*20}px">
							
							<c:if test='${list.depth!=0 }'>		
								<img alt="" src="${pageContext.request.contextPath }/assets/images/reply.png">
							</c:if>
							<a href="${pageContext.request.contextPath }/board?a=view&userNo=${list.userNo}&no=${list.no}">
							${list.title }
							</a>
						
						
						</td>
						<td>${list.userName }</td>
						<td>${list.hit }</td>
						<td>${list.regDate }</td>
						
						<c:if test='${authUser.no == list.userNo }'>
						<td>
						<a href="${pageContext.request.contextPath }/board?a=delete&groupNo=${list.groupNo}" class="del">삭제</a>
						</td>
						</c:if>
					</tr>
					</c:forEach>
					
				</table>
				
				
				
				
				<!-- pager 추가 -->
				
				<div class="pager">
					<ul>
							<c:if test="${map.currentPage!=1 }">
								<li><a href ="${pageContext.request.contextPath }/board?page=${1}">≪</a></li>
							</c:if>	
							<c:if test="${map.currentPage!=1 }">
								<li><a href ="${pageContext.request.contextPath }/board?page=${map.prevPage}">＜</a></li>
							</c:if>	
								
									<c:forEach begin="${map.firstPage }" end="${map.lastPage }" step="1" varStatus="status" var="sel">
										<li  <c:if test="${map.currentPage==sel }"> class="selected" </c:if> ><a href ="${pageContext.request.contextPath }/board?page=${(status.count-1)+map.firstPage }">${(status.count-1)+map.firstPage }</a></li>
									</c:forEach>
									
							
							<c:if test="${map.currentPage<map.totalPage }">
								<li><a href ="${pageContext.request.contextPath }/board?page=${map.nextPage }">＞</a></li>
							</c:if>		
							<c:if test="${map.currentPage<map.totalPage }">
								<li><a href ="${pageContext.request.contextPath }/board?page=${map.totalPage }">≫</a></li>
							</c:if>		
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				
				
				<c:if test='${not empty authUser }'>
				<div class="bottom">
					<a href="${pageContext.request.contextPath }/board?a=writeform" id="new-book">글쓰기</a>
				</div>
				</c:if>
							
							
								
			</div>
		</div>
		
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
		
		
	</div>
</body>
</html>