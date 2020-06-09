<?php
 require_once 'DbConnect.php';
 
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
 $response['message'] = 'food added successfully';
}
?>