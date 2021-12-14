<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="apple-touch-icon" href="ifcp/img/apple-icon.png">
<link rel="shortcut icon" type="image/x-icon"
	href="ifcp/img/favicon.ico">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/ifcp/css/custom.css">

<!-- search bar css -->
<link href="${pageContext.request.contextPath}/resources/search/css/main.css" rel="stylesheet" />
<!-- Load fonts style after rendering the layout styles -->
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;200;300;400;500;700;900&display=swap">
<title>Insert title here</title>
<style>
#img {
	width: 100% px;
	height: 100% px;
}
.s003{
	background-color: #fff;min-height: 0vh;
	padding:0;
	display:block;
}
.search-border{
	border: 1px solid rgba(0,0,0,.125); 
}
.s003 form .inner-form .input-field input {
	height:46px;
}
.s003 form .inner-form .input-field {
	height:46px;
}

</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	<!-- Start Top Nav -->
	<jsp:include page="/header.jsp" flush="false" />
	<!-- Close Top Nav -->


	<!-- Header -->
	<nav class="navbar navbar-expand-lg navbar-light shadow">
		<div
			class="container d-flex justify-content-between align-items-center">
		</div>
	</nav>
	<!-- Close Header -->

	<!-- Modal -->
	<div class="modal fade bg-white" id="templatemo_search" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="w-100 pt-1 mb-5 text-right">
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<form action="" method="get"
				class="modal-content modal-body border-0 p-0">
				<div class="input-group mb-2">
					<input type="text" class="form-control" id="inputModalSearch"
						name="q" placeholder="Search ...">
					<button type="submit"
						class="input-group-text bg-success text-light">
						<i class="fa fa-fw fa-search text-white"></i>
					</button>
				</div>
			</form>
		</div>
	</div>



	<!-- Start Content -->
	<div class="container py-5">
		<div class="row">

			<div class="col-lg-3">
				<h1 class="h2 pb-4">카테고리</h1>
				<!--  <ul class="list-unstyled templatemo-accordion"> -->
				<li class="pb-3"><a href="/companyList.ifcp?cpage=1"
					class="collapsed d-flex justify-content-between h3 text-decoration-none">
						기업 </a></li>
				<li class="pb-3"><a href="/influencerList.ifcp?cpage=1"
					class="collapsed d-flex justify-content-between h3 text-decoration-none">
						인플루언서 </a></li>
			</div>

			<div class="col-lg-9">
				<div class="row">
					<div class="col-md-6">
						<ul class="list-inline shop-top-menu pb-3 pt-1">
							<li class="list-inline-item"><a
								class="h3 text-dark text-decoration-none mr-3"
								href="/companyList.ifcp">등급</a></li>
							<li class="list-inline-item"><a
								class="h3 text-dark text-decoration-none mr-3" href="#">리뷰</a></li>
								<c:if test="${loginID != null && cp != null}">
									<li class="list-inline-item"><a
										class="h3 text-dark text-decoration-none mr-3"
										href="write.ifcp">제품등록</a></li>
								</c:if>
						</ul>
					</div>
					<div class="col-md-6 pb-4">
						<div class="d-flex"></div>
					</div>
				</div>
				<div class="row">
					<!--ㅋㅏ드 시작 -->
					<c:forEach var="dto" items="${list }">
						<div class="col-md-4">
							<div class="card mb-4 product-wap rounded-0">
								<div class="card rounded-0">
									<img id="img" class="card-img rounded-0 img-fluid"
										src=${dto.key.photo_cp }>
								</div>
								<div class="card-body">${dto.key.seq_cp}
									<a href="/companyBoard.ifcp?seq=${dto.key.seq_cp}"
										class="h3 text-decoration-none">${dto.key.title_cp}</a>
									<ul
										class="w-100 list-unstyled d-flex justify-content-between mb-0">
										<li>${dto.value.grade }</li>
										<li class="pt-2"><span
											class="product-color-dot color-dot-red float-left rounded-circle ml-1"></span>
											<span
											class="product-color-dot color-dot-blue float-left rounded-circle ml-1"></span>
											<span
											class="product-color-dot color-dot-black float-left rounded-circle ml-1"></span>
											<span
											class="product-color-dot color-dot-light float-left rounded-circle ml-1"></span>
											<span
											class="product-color-dot color-dot-green float-left rounded-circle ml-1"></span>
										</li>
									</ul>
									<ul class="list-unstyled d-flex justify-content-center mb-1">
										<li><i class="text-warning fa fa-star"></i> <i
											class="text-warning fa fa-star"></i> <i
											class="text-warning fa fa-star"></i> <i
											class="text-muted fa fa-star"></i> <i
											class="text-muted fa fa-star"></i></li>
									</ul>
									<p class="text-center mb-0">${dto.value.grade }</p>
								</div>
							</div>
						</div>
					</c:forEach>
					<!--ㅋㅏ드 끝 -->


		<!-- search bar -->				    
	    <div class="s003" style="margin-bottom:20px">
	      <form style="max-width:100%">
	        <div class="inner-form search-border " style="box-shadow:none;">
	          <div class="input-field first-wrap input-category ">
	              <select data-trigger="" name="choices-single-defaul" style="height:100%;">
	                <option>기업</option>
	                <option>인플루언서</option>
	              </select>
	          </div>
	          <div class="input-field second-wrap">
	            <input id="search" type="text" placeholder="이름을 입력하세요." />
	          </div>
	          <div class="input-field third-wrap">
	            <button class="btn-search" type="button" style="background-color: #000000;">
	              <svg class="svg-inline--fa fa-search fa-w-16" aria-hidden="true" data-prefix="fas" data-icon="search" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
	                <path fill="currentColor" d="M505 442.7L405.3 343c-4.5-4.5-10.6-7-17-7H372c27.6-35.3 44-79.7 44-128C416 93.1 322.9 0 208 0S0 93.1 0 208s93.1 208 208 208c48.3 0 92.7-16.4 128-44v16.3c0 6.4 2.5 12.5 7 17l99.7 99.7c9.4 9.4 24.6 9.4 33.9 0l28.3-28.3c9.4-9.4 9.4-24.6.1-34zM208 336c-70.7 0-128-57.2-128-128 0-70.7 57.2-128 128-128 70.7 0 128 57.2 128 128 0 70.7-57.2 128-128 128z"></path>
	              </svg>
	            </button>
	          </div>
	        </div>
	      </form>
	    </div>
	    <script src="<c:url value="/resources/search/js/extention/choices.js" />"></script>
	    <script>
	      const choices = new Choices('[data-trigger]',
	      {
	        searchEnabled: false,
	        itemSelectText: '',
	      });
	
	    </script>


					<!-- page navigation -->
					<div class="row">
						<div class="pagination pagination-lg justify-content-end">
							${navi }
						</div>
					</div>
				</div>

			</div>
		</div>
		<!-- End Content -->





		<!-- Start Script -->
		<script src="<c:url value="/resources/ifcp/js/jquery-1.11.0.min.js"/>"></script>
		<script
			src="<c:url value="/resources/ifcp/js/jquery-migrate-1.2.1.min.js" />"></script>
		<script
			src="<c:url value="/resources/ifcp/js/bootstrap.bundle.min.js" />"></script>
		<script src="<c:url value="/resources/ifcp/js/templatemo.js" />"></script>
		<script src="<c:url value="/resources/ifcp/js/custom.js" />"></script>
		<!-- End Script -->
		<jsp:include page="/footer.jsp" flush="false" />
</body>
</html>