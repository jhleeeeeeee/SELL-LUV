<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   	 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>커뮤니티 게시판</title>
	<link
		href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
		rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/css/bootstrap.min.css">

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>

<style>
body {
    margin: 0;
    font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, "Noto Sans", sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji";
    font-size: .88rem;
    font-weight: 400;
    line-height: 1.5;
    color: #495057;    
    background-color: #eef1f3;
}

.mt-100 {
    margin-top: 80px;
}

.card {
    box-shadow: 0 0.46875rem 2.1875rem rgba(4, 9, 20, 0.03), 0 0.9375rem 1.40625rem rgba(4, 9, 20, 0.03), 0 0.25rem 0.53125rem rgba(4, 9, 20, 0.05), 0 0.125rem 0.1875rem rgba(4, 9, 20, 0.03);
    border-width: 0;
    transition: all .2s;
    margin:auto;
}

.card-header:first-child {
    border-radius: calc(.25rem - 1px) calc(.25rem - 1px) 0 0
}

.card-header {
    display: flex;
    align-items: center;
    border-bottom-width: 1px;
    padding-top: 0;
    padding-bottom: 0;
    padding-right: .625rem;
    height: 3.5rem;
    background-color: #fff;
    border-bottom: 1px solid rgba(26, 54, 126, 0.125);
}

.btn-primary.btn-shadow {
    box-shadow: 0 0.125rem 0.625rem rgba(63, 106, 216, 0.4), 0 0.0625rem 0.125rem rgba(63, 106, 216, 0.5);
}

.btn.btn-wide {
    padding: .375rem 1.5rem;
    font-size: .8rem;
    line-height: 1.5;
    border-radius: .25rem;
}

.btn-primary {
    color: #fff;
    background-color: #3f6ad8;
    border-color: #3f6ad8;
}

.form-control {
    display: block;
    width: 100%;
    height: calc(2.25rem + 2px);
    padding: .375rem .75rem;
    font-size: 1rem;
    font-weight: 400;
    line-height: 1.5;
    color: #495057;
    background-color: #fff;
    background-clip: padding-box;
    border: 1px solid #ced4da;
    border-radius: .25rem;
    transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

.card-body {
    flex: 1 1 auto;
    padding: 1.25rem
}

.flex-truncate {
    min-width: 0 !important
}

.d-block {
    display: block !important
}

a {
    color: #E91E63;
    text-decoration: none !important;
    background-color: transparent
}

.media img {
    width: 40px;
    height: auto
}
#board-title {
	text-align:center; 
	height:100px; 
	line-height:100px;
	padding: 100px 0;
	
}
#title{
	width:50px;
	height:50px;
}
.search-bar {
	margin:auto;
}
</style>
</head>
<body>
	<!-- 메인 네비바 -->
	<jsp:include page="/header.jsp" flush="false"/>
	
	<!-- 타이틀  -->
	<div class="container-fluid mt-100">
    <div id="board-title">
    <img id="title" src="/resources/board/image/title.png">
    <span>커뮤니티 게시판</span>
    </div>       
    <br>
	
	<!-- 게시판 박스 -->
    <div class="card mb-3 col-xl-8 col-md-12">
    
      <!-- 분류 네비 -->  
        <div class="card-header pl-0 pr-0">
            <div class="row no-gutters w-100 align-items-center">
                <div class="col ml-3" style="text-align:center;">제목</div>
                <div class="col-4 text-muted">
                    <div class="row no-gutters align-items-center">
                        <div class="d-none d-md-block col-4" style="text-align:center;">댓글</div>
                        <div class="d-none d-md-block col-4" style="text-align:center;">글 작성일</div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- 게시판 내용들 -->
        <div class="card-body py-3">
            <div class="row no-gutters align-items-center">
                <div class="col"> <a href="javascript:void(0)" class="text-big" data-abc="true">How can i change the username?</a>
                    <div class="text-muted small mt-1">Started 25 days ago &nbsp;·&nbsp; <a href="javascript:void(0)" class="text-muted" data-abc="true">Neon Mandela</a></div>
                </div>
                <div class="d-none d-md-block col-4">
                    <div class="row no-gutters align-items-center">
                        <div class="col-4" style="text-align:center;">12</div>
                        <div class="media col-8 align-items-center"> <img src="https://res.cloudinary.com/dxfq3iotg/image/upload/v1574583246/AAA/2.jpg" alt="" class="d-block ui-w-30 rounded-circle">
                            <div class="media-body flex-truncate ml-2">
                                <div class="line-height-1 text-truncate">1 day ago</div> <a href="javascript:void(0)" class="text-muted small text-truncate" data-abc="true">by Tim cook</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <hr class="m-0">
        
        <div class="card-body py-3">
            <div class="row no-gutters align-items-center">
                <div class="col"> <a href="javascript:void(0)" class="text-big" data-abc="true">How to change the theme to dark mode?</a> <span class="badge badge-success align-text-bottom ml-1">Solved</span>
                    <div class="text-muted small mt-1">Started 5 days ago &nbsp;·&nbsp; <a href="javascript:void(0)" class="text-muted" data-abc="true">Allize Rome</a></div>
                </div>
                <div class="d-none d-md-block col-4">
                    <div class="row no-gutters align-items-center">
                        <div class="col-4">43</div>
                        <div class="media col-8 align-items-center"> <img src="https://res.cloudinary.com/dxfq3iotg/image/upload/v1574583319/AAA/3.jpg" alt="" class="d-block ui-w-30 rounded-circle">
                            <div class="media-body flex-truncate ml-2">
                                <div class="line-height-1 text-truncate">1 day ago</div> <a href="javascript:void(0)" class="text-muted small text-truncate" data-abc="true">by Steve smith</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <hr class="m-0">
        
        <div class="card-body py-3">
            <div class="row no-gutters align-items-center">
                <div class="col"> <a href="javascript:void(0)" class="text-big" data-abc="true">Is it possible to get the refund of the product i purhcased today?</a> <span class="badge badge-default align-text-bottom ml-1">Locked</span>
                    <div class="text-muted small mt-1">Started 21 days ago &nbsp;·&nbsp; <a href="javascript:void(0)" class="text-muted" data-abc="true">Kane William</a></div>
                </div>
                <div class="d-none d-md-block col-4">
                    <div class="row no-gutters align-items-center">
                        <div class="col-4">42</div>
                        <div class="media col-8 align-items-center"> <img src="https://res.cloudinary.com/dxfq3iotg/image/upload/v1574583336/AAA/4.jpg" alt="" class="d-block ui-w-30 rounded-circle">
                            <div class="media-body flex-truncate ml-2">
                                <div class="line-height-1 text-truncate">2 day ago</div> <a href="javascript:void(0)" class="text-muted small text-truncate" data-abc="true">by Brethwett sonm</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <hr class="m-0">
        
        <div class="card-body py-3">
            <div class="row no-gutters align-items-center">
                <div class="col"> <a href="javascript:void(0)" class="text-big" data-abc="true">Do you have android application for this tool?</a>
                    <div class="text-muted small mt-1">Started 56 days ago &nbsp;·&nbsp; <a href="javascript:void(0)" class="text-muted" data-abc="true">Glen Maxwell</a></div>
                </div>
                <div class="d-none d-md-block col-4">
                    <div class="row no-gutters align-items-center">
                        <div class="col-4">632</div>
                        <div class="media col-8 align-items-center"> <img src="https://res.cloudinary.com/dxfq3iotg/image/upload/v1574583246/AAA/2.jpg" alt="" class="d-block ui-w-30 rounded-circle">
                            <div class="media-body flex-truncate ml-2">
                                <div class="line-height-1 text-truncate">11 day ago</div> <a href="javascript:void(0)" class="text-muted small text-truncate" data-abc="true">by Neil patels</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <hr class="m-0">
        
        <div class="card-body py-3">
            <div class="row no-gutters align-items-center">
                <div class="col"> <a href="javascript:void(0)" class="text-big" data-abc="true">How can i transfer my fund to my friend</a> <span class="badge badge-secondary align-text-bottom ml-1">Closed</span>
                    <div class="text-muted small mt-1">Started 25 days ago &nbsp;·&nbsp; <a href="javascript:void(0)" class="text-muted" data-abc="true">Marry Tom</a></div>
                </div>
                <div class="d-none d-md-block col-4">
                    <div class="row no-gutters align-items-center">
                        <div class="col-4">53</div>
                        <div class="media col-8 align-items-center"> <img src="https://res.cloudinary.com/dxfq3iotg/image/upload/v1574583336/AAA/4.jpg" alt="" class="d-block ui-w-30 rounded-circle">
                            <div class="media-body flex-truncate ml-2">
                                <div class="line-height-1 text-truncate">1 day ago</div> <a href="javascript:void(0)" class="text-muted small text-truncate" data-abc="true">by Tibok tom</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <hr class="m-0">
        
        <div class="card-body py-3">
            <div class="row no-gutters align-items-center">
                <div class="col"> 
                <a href="javascript:void(0)" class="text-big" data-abc="true">How can i delete my account?</a> 
                <span class="badge badge-danger align-text-bottom ml-1">Hot!</span>
                    <div class="text-muted small mt-1">Started 25 days ago &nbsp;·&nbsp; <a href="javascript:void(0)" class="text-muted" data-abc="true">Bob bulmer</a></div>
                </div>
                <div class="d-none d-md-block col-4">
                    <div class="row no-gutters align-items-center">
                        <div class="col-4">12</div>
                        <div class="media col-8 align-items-center"> <img src="https://res.cloudinary.com/dxfq3iotg/image/upload/v1574583246/AAA/2.jpg" alt="" class="d-block ui-w-30 rounded-circle">
                            <div class="media-body flex-truncate ml-2">
                                <div class="line-height-1 text-truncate">1 day ago</div> <a href="javascript:void(0)" class="text-muted small text-truncate" data-abc="true">by Ross taylor</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
    </div>
    
     <!-- 글쓰기 & 검색 라인 -->
    <div class="row search-bar" style="justify-content: space-around;">
    	<div class="col-xl-8 col-md-12 d-none d-md-block"> 
    		<div class="row">
		        <div class="col-6"><input type="text" placeholder="Search..." style="width:100%; height:100%;">
		        </div>
		        <div class="col-4">
		        <button type="button" class="btn btn-shadow btn-wide btn-primary" style="background-color:rgb(255, 111, 97); border-color:rgb(255, 111, 97);"> 
         		검색하기 </button>     
         		</div>
		    	<div class="col-2" style="text-align:right;"> 
		        <button type="button" class="btn btn-shadow btn-wide btn-primary" style="background-color:rgb(255, 111, 97); border-color:rgb(255, 111, 97);"> 
		         글 쓰기 </button> 
		        </div> 	
    		</div>
    	</div> 
        
        <div class="d-md-none">
        	<div class="row">
        		<div class="col-5">
        			<input type="text" placeholder="Search..." style="width:100%; height:100%;">
		        </div>
		        <div class="col-4">
		        	<button type="button" class="btn btn-shadow btn-wide btn-primary" style="background-color:rgb(255, 111, 97); border-color:rgb(255, 111, 97);"> 
         		검색하기 </button>     
         		</div>
         		<div class="col-3" style="text-align:right;"> 
	        		<button type="button" class="btn btn-shadow btn-wide btn-primary" style="background-color:rgb(255, 111, 97); border-color:rgb(255, 111, 97);"> 
	         		글 쓰기 </button> 
        		</div> 
        	</div>
        </div>
        
        
    </div>
    
    <br>
    <!-- 페이지 네비 -->
    <nav>
        <ul class="pagination mb-5 justify-content-center">
            <li class="page-item disabled"><a class="page-link" href="javascript:void(0)" data-abc="true">«</a></li>
            <li class="page-item active"><a class="page-link" href="javascript:void(0)" data-abc="true">1</a></li>
            <li class="page-item"><a class="page-link" href="javascript:void(0)" data-abc="true">2</a></li>
            <li class="page-item"><a class="page-link" href="javascript:void(0)" data-abc="true">3</a></li>
            <li class="page-item"><a class="page-link" href="javascript:void(0)" data-abc="true">»</a></li>
        </ul>
    </nav>
    
    <!-- 푸터 -->
	<jsp:include page="/footer.jsp" flush="false"/>
</div>                            
<script type="text/javascript"></script>
</body>
</html>