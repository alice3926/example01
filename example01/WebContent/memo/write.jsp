<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/import.jsp" %>    
    
    
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
//페이지 로딩이 끝난 후 실행되는 함수
$(document).ready(function(){
	
	$("#btnSave").click(function(){
		insert();
	});
	list();
});


function insert(){
	var name = $("#name").val();
	var memo = $("#memo").val();
	var param = "name="+name+"&memo="+memo; //쿼리스트링
	$.ajax({
		type:"post",
		data:param,
		url:"${path}/memo_servlet/write.do",
		success:function(){//콜백함수
			$("#name").val(""); //값을 공백으로 덮어써라. 입력다했으니깐 거기 값 지워라
			$("#memo").val("");
			$("#pageNumber").text("1");
			list();
		}
	});

}



function list(){
	var param = {
			"pageNumber":$("#pageNumber").text()
	}
	
	
	$.ajax({
		type:"post",
		data:param,
		url:"${path}/memo_servlet/list.do",
		success: function(result){
			$("#result").html(result);
		}
	});
}


function GoPage(value1){
	
	var pageNumber = value1;
	var param = "pageNumber="+pageNumber;
	
	$.ajax({
		type:"post",
		data:param,
		url:"${path}/memo/write.jsp",
		success:function(){//콜백함수
			$("#pageNumber").text(pageNumber);
			list();
		}
	});

}


</script>

</head>
<body>

<span style="display:none;" id="pageNumber">${pageNumber }</span>

<table border="1" height="600" width="80%" align="center">
	<tr>
		<td colspan="2" height="50">
			<h2>메모장</h2>
		</td>
	</tr>
	<tr>
		<td width="100">
			이름
		</td>
		<td>
		 <input type="text" id="name" size="10">
		</td>
	</tr>
	<tr>
		<td width="100">
			메모
		</td>
		<td>
			<input type="text" id="memo" size="40">
			&nbsp;&nbsp;
		<input type="button" id="btnSave" value="확인">
		</td>
	</tr>
	<tr>
		<td colspan="2" height="400">
		&nbsp;
			<div id="result"></div>
		</td>
	</tr>
</table>










</body>
</html>