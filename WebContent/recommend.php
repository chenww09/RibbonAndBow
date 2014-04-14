<?php
$userId = '11311023';
// The data to send to the API
$postData = array(
		'userId' => $userId,
		'secretKey' => 'p9Dlai4qgBf6jUIDVnMHM4sda09GqM4pAeRRNZan',
		

);
$username = 'ribbon';
$password = 'Ribbon&Bow';
// Create the context for the request
$context = stream_context_create(array(
		'http' => array(
				// http://www.php.net/manual/en/context.http.php
				'method' => 'POST',
				'header'  => "Authorization: Basic " . base64_encode("$username:$password"),
				"Content-Type: application/json\r\n",
				'content' => json_encode($postData)
		)
));
// Send the request
//$response = file_get_contents('http://ec2-54-201-168-36.us-west-2.compute.amazonaws.com:8080/RecommendationEngine/Recommend', FALSE, $context);
$response = file_get_contents('http://localhost:8080/RecommendationEngine/Recommend', FALSE, $context);


// Check for errors
if($response === FALSE){
	die('Error');
}
$responseData = json_decode($response, TRUE);
$array=$responseData['products'];
//print_r($array);
foreach($array as $obj){
	echo $obj['productName'];
}
?>