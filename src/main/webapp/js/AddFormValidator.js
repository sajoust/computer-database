/**
 * 
 */


function nameValidator() {
  var name, text;

  // Get the value of the input field with id="numb"
  
  name=document.getElementsById('labretagne').value;
  window.alert(name.length);

  // If x is Not a Number or less than one or greater than 10
  if (name.length==0) {
    text = "Input not valid";
    window.alert(text);
  }
  
}