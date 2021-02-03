package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import memo.dao.MemoDAO;
import memo.dto.MemoDTO;

@WebServlet("/memo_servlet/*")
public class MemoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProc(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProc(request, response);
	}
	
	protected void doProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String path = request.getContextPath();
		String url=request.getRequestURL().toString();
		String pageNumber_;
		
		pageNumber_ = request.getParameter("pageNumber");

		
		if(pageNumber_==null||pageNumber_.trim().equals("")) {
			pageNumber_="1"; //넘어온게 숫자타입이 아니면 고의 접근이니 팅기도록 만들기
			
		}
		int pageNumber=Integer.parseInt(pageNumber_);
		
		MemoDAO dao = new MemoDAO();
	
		
		if(url.indexOf("write.do")!=-1) {
 			
 			String name = request.getParameter("name");
 	 		String memo = request.getParameter("memo");
 	 		MemoDTO dto = new MemoDTO();
 	 		dto.setName(name);
 	 		dto.setMemo(memo);
 	 		
 	 		int result =dao.insert(dto);
 	 		
 	 		pageNumber=1;
 	 		String page= "${path}/memo/write.jsp";
 	 		
 	 		request.setAttribute("pageNumber",pageNumber);
 	 		RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
 	 		
 			
 		}else if(url.indexOf("list.do")!=-1) {
 			
 			int pageSize = 10;
			int blockSize =10;
			int totalRecord = dao.getTotalRecord();
			int jj = totalRecord - pageSize *(pageNumber-1);
			
			int startRecord = pageSize *(pageNumber -1) +1;
			int lastRecord = pageSize * pageNumber;
			
			
			int totalPage = 0;
			int startPage =1;
			int lastPage =1;
			
			if(totalRecord>0) {
				totalPage=totalRecord/pageSize+(totalRecord % pageSize == 0 ? 0:1);
				startPage = (pageNumber / blockSize - (pageNumber%blockSize !=0?0:1))*blockSize +1;
				lastPage=startPage +blockSize-1;
				if(lastPage>totalPage) {
					lastPage = totalPage;
				}
					
			}
 			
 			ArrayList<MemoDTO> list = dao.getList(startRecord, lastRecord);
 			//request 영역에 저장해라...
 			request.setAttribute("list", list);
 			request.setAttribute("pageNumber",pageNumber);
			request.setAttribute("pageSize",pageSize);
			request.setAttribute("blockSize",blockSize);
			request.setAttribute("totalRecord",totalRecord);
			request.setAttribute("jj",jj);
			
			request.setAttribute("startRecord",startRecord);
			request.setAttribute("lastRecord",lastRecord);
			
			request.setAttribute("totalPage",totalPage);
			request.setAttribute("startPage",startPage);
			request.setAttribute("lastPage",lastPage);
 			String page = "/memo/list.jsp";
 			RequestDispatcher rd = request.getRequestDispatcher(page);
 			rd.forward(request, response);
 		}
	
		
	
	
	
	
	
	
	
	
	
	
	}

	
	
	

}
