/**
 * 
 */

function nameValidator() {
	var text = "Name cant be empty"

	var nameField = document.getElementsByName("name")[0].value;
	console.log("verif executée");
	if (nameField.length == 0) {
		window.alert(text);
		return false;
	}
	return true;

}

function dateValidator() {
	var text, dateIntroduced, dateDiscontinued;
	var introduced = document.getElementsByName("introduced")[0].value;
	var discontinued = document.getElementsByName("discontinued")[0].value;
	dateIntroduced = new Date(introduced);
	dateDiscontinued = new Date(discontinued);

	if (dateIntroduced > dateDiscontinued) {
		window.alert("introduced must be before discontinued");
		return false
	}
	return true

}