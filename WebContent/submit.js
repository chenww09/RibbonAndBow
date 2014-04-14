var req;
var secretKey = "p9Dlai4qgBf6jUIDVnMHM4sda09GqM4pAeRRNZan";

function recommendId(form) {
	var userId = document.forms["myForm"]["userId"].value;
	var data = {};
	data["userId"] = userId;
	data["secretKey"] = secretKey;
	loadXMLDoc("/RecommendationEngine/Recommend", data);
}
function recommendKeyword(form) {
	var keywords = document.forms["myForm"]["keywords"].value;
	var data = {};
	data["keywords"] = keywords;
	data["secretKey"] = secretKey;
	loadXMLDoc("/RecommendationEngine/Recommend", data);
}
function clearForm(form) {
	document.getElementById("updateArea").innerHTM="Clear";
	document.getElementById("userProduct").innerHTM="Clear";
	
}


function loadXMLDoc(url, data) {
	req = false;
	if (window.XMLHttpRequest) {
		try {
			req = new XMLHttpRequest();
		} catch (e) {
			req = false;
		}
	} else if (window.ActiveXObject) {
		try {
			req = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			req = false;
		}
	}

	if (req) {
		req.onreadystatechange = showJSON;
		
		req.open("POST", url, true);
		req.send(JSON.stringify(data));
	}
}
function processJSON() {
	if (req.readyState == 4) {
		if (req.status == 200) {
			var doc = JSON.parse(req.responseText);
			
			var outputMsg = "";
			
				outputMsg += "<table class=output>";
				outputMsg += "<th>Result</th>";
				outputMsg += "<td class=output>" + doc.products + "</td>";
				outputMsg += "</table>";
				document.getElementById("updateArea").innerHTML = outputMsg;
			
		} else {
			document.getElementById("updateArea").innerHTML = "Failed";
		}
	}
}
function listUser(form) {
	updateXMLDoc("/RecommendationEngine/User");
}
function listProduct(form) {
	updateXMLDoc("/RecommendationEngine/Product");
}
function updateXMLDoc(url) {
	req = false;
	if (window.XMLHttpRequest) {
		try {
			req = new XMLHttpRequest();
		} catch (e) {
			req = false;
		}
	} else if (window.ActiveXObject) {
		try {
			req = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			req = false;
		}
	}

	if (req) {
		req.onreadystatechange = updateJSON;
		req.open("GET", url, true);
		req.send("");
	}
}
function updateJSON() {
	if (req.readyState == 4) {
		if (req.status == 200) {
			var doc = JSON.parse(req.responseText);
			var outputMsg = "";
			outputMsg += "<table class=output>";
			var values = "";
			var flag = true;
			outputMsg += "<tr>";
			for(index in doc){
				
				var obj = doc[index];
				var record = obj[0];
				values += "<tr>";
				for (var key in record){
					if(key!="_id"){
						if(flag){
							outputMsg += "<td class=output>" + key + "</td>";
							
						}
						var value = record[key];
						values += "<td>" + value +"</td>";
					}
				}
				flag = false;
				values += "</tr>"
				
			}	
			outputMsg += "/<tr>";
			outputMsg += values;
			
			
			outputMsg += "</table>";
			document.getElementById("userProduct").innerHTML = outputMsg;
			
		} else {
			document.getElementById("userProduct").innerHTML = "Failed";
		}
	}

}


function showJSON() {
	if (req.readyState == 4) {
		if (req.status == 200) {
			var doc = JSON.parse(req.responseText);
			var outputMsg = "";
			outputMsg += "<table class=output>";
			var values = "";
			var flag = true;
			outputMsg += "<tr>";
			for(index in doc){
				var obj = doc[index];
				for(var number in obj){
					var record = obj[number];
					values += "<tr>";
					for (var key in record){
						if(key!="_id"){
							if(flag){
								outputMsg += "<td class=output>" + key + "</td>";
								
							}
							var value = record[key];
							values += "<td>" + value +"</td>";
						}
					}
					flag = false;
					values += "</tr>"
				}
				
			}	
			outputMsg += "/<tr>";
			outputMsg += values;
			
			
			outputMsg += "</table>";
			document.getElementById("userProduct").innerHTML = outputMsg;
			
		} else {
			document.getElementById("userProduct").innerHTML = "Failed";
		}
	}

}




function processReqChange() {
	if (req.readyState == 4) {
		if (req.status == 200) {
			var outMsg = req.responseXML;
		} else {
			var outMsg = "Failed";
		}
		document.getElementById("updateArea").innerHTML = outMsg;
	}

}