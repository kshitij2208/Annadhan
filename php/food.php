<?php
 require_once 'DbConnect.php';
 //if it is an api call 
 //that means a get parameter named api call is set in the URL 
 //and with this parameter we are concluding that it is an api call 
 if(isset($_GET['apicall'])){
 $response = array()

 $quantity = $_POST['quantity'];
 $packaging = $_POST['packaging'];
 $location = $_POST['location'];
 $picture = $_POST['picture']; 
 $pickup_time = $_POST['pickup_time'];
 
 $st = $conn->prepare("INSERT INTO food (quantity,packaging,location,picture,pickup_time) VALUES (?, ?, ?, ?, ?)");
 $st->bind_param("sssss", $quantity,$packaging,$location,$picture,$pickup_time);
 
 $st->close();
 $response['error'] = false; 
 $response['message'] = 'Successfully added';
}
?>