
Handlebars.registerHelper("formatName" , function(property){
	return "Hello my name is " + property;
})

$(document).ready(function(){
	var characterTemplate = $("#character-template").html();

	var compiledCharacterTemplate = Handlebars.compile(characterTemplate);


})