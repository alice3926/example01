<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/import.jsp" %>    

<table border="1" align="center" width="80%">
<tr>
	<th>ID</th>
	<th>이름</th>
	<th>메모</th>
	<th>날짜</th>
</tr>
<c:if test="${totalRecord==0 }">
	<tr>
		<td colspan="15" height="200" align="center">
			 등록된 메모가 없습니다.
		</td>
	</tr>
</c:if>	
<c:forEach var="row" items="${list }">
<tr>
	<td>${row.id }</td>
	<td>${row.name }</td>
	<td>${row.memo }</td>
	<td>${row.regiDate }</td>
</tr>
</c:forEach>
<tr>
	<td colspan="4" align="center">
		<a href="#" onclick="GoPage('1');">[첫페이지]</a>
			&nbsp;&nbsp;
			<c:if test="${startPage > blockSize }">
				<a href="#" onclick="GoPage('${startPage-blockSize});">[이전10개]</a>
			</c:if>
			<c:if test="${startPage <=blockSize }">
			[이전10개]
			</c:if>
			&nbsp;
			<c:forEach var="i" begin="${startPage }" end="${lastPage }" step="1">
				<c:if test="${i==pageNumber }">
					[${i }]
				</c:if>
				<c:if test="${i!=pageNumber }">
					<a href="#" onclick="GoPage('${i}');">${i }</a>
				</c:if>
			</c:forEach>		
			&nbsp;
			<c:if test="${lastPage<totalPage }">
				<a href="#" onclick="GoPage('${startPage+blockSize}');">[다음10개]</a>
			</c:if>
			<c:if test="${lastPage >=totalPage }">
				[다음10개]
			</c:if>
			&nbsp;&nbsp;
			<a href="#" onclick="GoPage('${totalPage}');">[끝페이지]</a>
	
	</td>
<tr>
</table>