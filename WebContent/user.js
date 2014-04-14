var req;
function validateForm(form) {
	var username = document.forms["myForm"]["username"].value;
	if (username == null || username == "") {
		alert("Please enter a username.");
		return false;
	}
	var password = document.forms["myForm"]["password"].value;
	var facebookId = document.forms["myForm"]["facebookId"].value;
	var linkedInId = document.forms["myForm"]["linkedInId"].value;
	var gender = document.forms["myForm"]["gender"].value;
	//It is really a trick
	loadXMLDoc("/RecommendationEngine/User?username="
			+ username + "&facebookId=" + facebookId + "&linkedInId="
			+ linkedInId + "&gender=" + gender + "&password=" + password);
}

function updateForm(form) {
	updateXMLDoc("/RecommendationEngine/User");
}

function loadXMLDoc(url) {
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
		req.onreadystatechange = processJSON;
		req.open("GET", url, true);
		req.send("");
	}
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


function processJSON() {
	if (req.readyState == 4) {
		if (req.status == 200) {
			var doc = JSON.parse(req.responseText);
			
			//console.debug("%o", doc);
			var outputMsg = "";
			
				// outputMsg += "<div id=fb-root></div>";
				outputMsg += "<table class=output>";
				outputMsg += "<th>Result</th>";
				outputMsg += "<td class=output>" + doc.ok + "</td>";
				outputMsg += "</table>";
				document.getElementById("updateArea").innerHTML = outputMsg;
			
		} else {
			document.getElementById("updateArea").innerHTML = "Failed";
		}
	}
}

function updateJSON() {
	if (req.readyState == 4) {
		if (req.status == 200) {
			var doc = JSON.parse(req.responseText);
			var outputMsg = "";
			//alert(req.responseText);
				// outputMsg += "<div id=fb-root></div>";
			outputMsg += "<table class=output>";
			
			for(index in doc){
				outputMsg += "<tr>";
				var obj = doc[index];
				var record = obj[0];
				var facebook = record["facebook"];
				outputMsg += "<td class=output>facebook</td><td>" + facebook +"</td>";
				var linkedin = record["linkedin"];
				outputMsg += "<td class=output>linkedin</td><td>" + linkedin +"</td>";
				var gender = record["gender"];
				outputMsg += "<td class=output>gender</td><td>" + gender +"</td>";
				outputMsg += "</tr>";
			}		
			
			outputMsg += "</table>";
/*			outputMsg += "<table class=output>";
			
			for(var i=0;i<doc.length;i++){
		        var obj = doc[i];
		        outputMsg += "<tr>";
		        for(var key in obj){
		            var attrName = key;
		            var attrValue = obj[key];
					outputMsg += "<td>" + attrName +"</td>";
					outputMsg += "<td class=output>" + attrValue + "</td>";
		            
		        }
		        outputMsg += "</tr>";
		    }
			
			outputMsg += "</table>";
			*/
			document.getElementById("updateArea").innerHTML = outputMsg;
			
		} else {
			document.getElementById("updateArea").innerHTML = "Failed";
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