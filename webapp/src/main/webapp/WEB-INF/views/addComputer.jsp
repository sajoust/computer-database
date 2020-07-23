<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" charset="UTF-8" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="home"> Application - Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<form:form method="POST" action="addComputer" modelAttribute="dtoComputer" accept-charset="UTF-8" onsubmit="return nameValidator() & dateValidator()">
						<fieldset>
							<div class="form-group">
								<form:label path="name">Computer name*</form:label>
								<form:input type="text" class="form-control" name="name" path="name" placeholder="Computer name" />
								<span class="error">${errors['computerName']}</span>
							</div>
							<div class="form-group">
								<form:label path="introduced">Introduced date</form:label>
								<form:input type="date" class="form-control" name="introduced" path="introduced" placeholder="Introduced date" />

							</div>
							<div class="form-group">
								<form:label path="discontinued">Discontinued date</form:label>
								<form:input type="date" class="form-control" name="discontinued" path="discontinued" placeholder="Discontinued date" />
								<span class="error">${errors['discontinued']}</span>
							</div>
							<div class="form-group">
								<form:label path="dtoCompany">Company</form:label>
								<select class="form-control" name="dtoCompany">
									<option value="0">Unknown company</option>
									<c:forEach items="${DTOList}" var="DTO" varStatus="status">
										<option value="${DTO.id}"><c:out value="${DTO.name}"></c:out></option>
									</c:forEach>

								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary">
							or <a href="home" class="btn btn-default">Cancel</a>
						</div>
					</form:form>

					<%-- 					<form action="addComputer" accept-charset="UTF-8" method="post" onsubmit="return nameValidator() & dateValidator()"></form> --%>
				</div>
			</div>
		</div>
	</section>
	<script src="js/AddFormValidator.js"></script>
</body>
</html>