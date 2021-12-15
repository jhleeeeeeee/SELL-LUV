package kh.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kh.web.dao.ReviewDAO;
import kh.web.dto.BoardDTO;
import kh.web.dto.ReviewDTO;
import kh.web.statics.IFCPStatics;


@WebServlet("*.review")
public class ReviewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
request.setCharacterEncoding("utf8");  // get방식 한글 깨짐 방지
		
		String uri = request.getRequestURI();
		String ctx = request.getContextPath();
		String cmd = uri.substring(ctx.length());
		System.out.println("사용자가 요청한 기능 : " + cmd);
		
		ReviewDAO reviewDAO = new ReviewDAO();
		
		try {
			if(cmd.equals("/write.reivew")) {
				
				String writer = request.getParameter("writer");
				String content = request.getParameter("content");
				String raiting= request.getParameter("raiting");
				
//				ReviewDTO reviewDTO = new ReviewDTO(writer,content,raiting);
//				
//				int result = reviewDAO.writeReview(reviewDTO);
				
				response.sendRedirect("/resources/searchDetail/searchDetail.jsp");
			}else if(cmd.equals("/IFReviewList.review")) {
				
				int currentPage = Integer.parseInt(request.getParameter("cpage"));
				int pageTotalCount = reviewDAO.getPageTotalCountReview();
				
				if(currentPage < 1) {
					currentPage = 1;
				}
				if(currentPage > pageTotalCount) {
					currentPage = pageTotalCount;
				}
				
				int start = currentPage * IFCPStatics.RECORD_COUNT_PER_PAGE - (IFCPStatics.RECORD_COUNT_PER_PAGE -1) ;
				int end =  currentPage * IFCPStatics.RECORD_COUNT_PER_PAGE;
				
				//List<BoardDTO> list = dao.selectByBound(start, end);
				//String navi = dao.getPageNavi(currentPage);
				request.setAttribute("list",list);
				request.setAttribute("navi", navi);
				request.getRequestDispatcher("/board/boardlist.jsp").forward(request,response);
				
			}
		}catch(Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}
		
		
		
	}
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
