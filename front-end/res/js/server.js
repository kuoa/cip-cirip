/*---------------------------------------------*/
/* 				Friends						   */
/*---------------------------------------------*/

var root = "http://li328.lip6.fr:8280/girraffe";

function serverRequest(url, data, doneFun){
	
	var request = $.ajax({
		url: root + url,		
		data: data,
		dataType: "json"	
	});
	
	request.done(doneFun);
	
	request.fail(function(msg) {
		console.log("Fail");
		console.log(msg);
	});		
}
		
function commentGetForFriends(element){
	
	var url = "/comments/get-for-friends";
	
	var data = {
		key : "769221ad24cc4ba6b493b0b4a977e6ad",
		user: "TestUser"
	};
	
	var doneFun = function(msg) {
		console.log("Done");
		console.log(msg);
	};
	
	serverRequest(url, data, doneFun);				
}