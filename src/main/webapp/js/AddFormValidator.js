/**
 * 
 */


function nameValidator() {
  var text = "Name cant be empty"
  
  var nameField=document.getElementsByName("computerName")[0].value;

  if (nameField.length==0) {
    window.alert(text);
    return false;
  }
  return true;

}

function dateValidator(){
	var text,dateIntroduced,dateDiscontinued;
	var introduced = document.getElementsByName("introduced")[0].value;
	var discontinued = document.getElementsByName("discontinued")[0].value;
	dateIntroduced = new Date(introduced); 
	dateDiscontinued = new Date(discontinued);
	console.log(dateIntroduced);
	console.log(dateDiscontinued);
	
	if (dateIntroduced>dateDiscontinued){
		window.alert("introduced must be before discontinued");
		return false
	}
	return true
	
	
}