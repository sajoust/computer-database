<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="home"> Application - Computer Database</a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:out value="${entriesCount}">ERREUR</c:out>
				Computers found
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">
						<input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
						<input type="submit" id="searchsubmit" value="Filter by name" class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer">Add Computer</a> <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input type="checkbox" id="selectall" />
							<span style="vertical-align: top;">
								- <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();"> <i class="fa fa-trash-o fa-lg"></i>
								</a>
							</span></th>
						<th>Computer name 
						<a class="glyphicon glyphicon-chevron-up" href="home?pageToDisplay=${pageToDisplay}&computerPerPage=${computerPerPage}&search=${search}&order=computer.name-ASC"></a>
						<a class="glyphicon glyphicon-chevron-down" href="home?pageToDisplay=${pageToDisplay}&computerPerPage=${computerPerPage}&search=${search}&order=computer.name-DESC"></a></th>
						
						<th>Introduced date 
						<a class="glyphicon glyphicon-chevron-up" href="home?pageToDisplay=${pageToDisplay}&computerPerPage=${computerPerPage}&search=${search}&order=introduced-ASC"></a>
						<a class="glyphicon glyphicon-chevron-down" href="home?pageToDisplay=${pageToDisplay}&computerPerPage=${computerPerPage}&search=${search}&order=introduced-DESC"></a></th>
						<!-- Table header for Discontinued Date -->
						
						<th>Discontinued date 
						<a class="glyphicon glyphicon-chevron-up" href="home?pageToDisplay=${pageToDisplay}&computerPerPage=${computerPerPage}&search=${search}&order=discontinued-ASC"></a>
						<a class="glyphicon glyphicon-chevron-down" href="home?pageToDisplay=${pageToDisplay}&computerPerPage=${computerPerPage}&search=${search}&order=discontinued-DESC"></a></th>
						<!-- Table header for Company -->
						
						<th>Company 
						<a class="glyphicon glyphicon-chevron-up" href="home?pageToDisplay=${pageToDisplay}&computerPerPage=${computerPerPage}&search=${search}&order=company_id-ASC"></a>
						<a class="glyphicon glyphicon-chevron-down" href="home?pageToDisplay=${pageToDisplay}&computerPerPage=${computerPerPage}&search=${search}&order=company_id-DESC"></a></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${DTOList}" var="DTO" varStatus="status">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb" class="cb" value="${DTO.id}"></td>
							<td><a href="editComputer?computerToEdit=${DTO.id}" onclick=""><c:out value="${DTO.name}"></c:out></a></td>
							<td><c:out value="${DTO.introduced}"></c:out></td>
							<td><c:out value="${DTO.discontinued}"></c:out></td>
							<td><c:out value="${DTO.dtoCompany.getId()}  ${DTO.dtoCompany.getName()}"></c:out></td>

						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">

				<c:if test="${pageToDisplay>1}">
					<li><a href="home?pageToDisplay=${pageToDisplay-1}&computerPerPage=${computerPerPage}&search=${search}&order=${order}" aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
					</a></li>
				</c:if>
				<c:if test="${pageToDisplay>nbPages}" var="testPages"></c:if>

				<c:forEach var="i" begin="${Math.max(pageToDisplay-2,1)}" end="${Math.min(pageToDisplay+2,nbPages+0)}">


					<li><a href="home?pageToDisplay=${i}&computerPerPage=${computerPerPage}&search=${search}&order=${order}">${i}</a></li>


				</c:forEach>


				<c:if test="${pageToDisplay<nbPages}">
					<li><a href="home?pageToDisplay=${pageToDisplay+1}&computerPerPage=${computerPerPage}&search=${search}&order=${order}" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
					</a></li>
				</c:if>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">

				<a href="home?pageToDisplay=${pageToDisplay}&computerPerPage=10&search=${search}&order=${order}"><button type="button" class="btn btn-default">10</button></a> 
				<a href="home?pageToDisplay=${pageToDisplay}&computerPerPage=50&search=${search}&order=${order}"><button type="button" class="btn btn-default">50</button></a>
				<a href="home?pageToDisplay=${pageToDisplay}&computerPerPage=100&search=${search}&order=${order}"><button type="button" class="btn btn-default">100</button></a>
			</div>
		</div>
	</footer>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>

</body>
</html>