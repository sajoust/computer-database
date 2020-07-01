<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
                    <div class="label label-default pull-right">
                        id: 0
                    </div>
                    <h1>Edit Computer</h1>

                    <form action="editComputer" method="POST" onsubmit = "return nameValidator() & dateValidator()">
                        <input type="hidden" value="0" id="id"/> <!-- TODO: Change this value with the computer id -->
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" value="${computerToEditName}" class="form-control" name="computerName" id="computerName" placeholder="Computer name">
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" value="${introducedComputerToEdit}" name="introduced" id="introduced" placeholder="Introduced date">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" value="${discontinuedComputerToEdit}" name="discontinued" id="discontinued" placeholder="Discontinued date">
                            </div>
                            <div class="form-group">
								<label for="companyId">Company</label>
								<select class="form-control" name="companyId">
									<option value="${companyIDComputerToEdit}"><c:out value="${companyIDComputerToEdit} -- ${companNameComputerToEdit}"></c:out></option>
									<c:forEach items="${dtoCompanyList}" var="DTO" varStatus="status">
										<option value="${DTO.id}"><c:out value="${DTO.id} -- ${DTO.name}"></c:out></option>
									</c:forEach>
									<option value="0">Unknown company</option>

								</select>
							</div>
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="dashboard.html" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    <script src="js/AddFormValidator.js"></script>
</body>
</html>