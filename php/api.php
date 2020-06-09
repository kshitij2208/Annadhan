<?php 
        //getting the database connection
 require_once 'DbConnect.php';
 // Required if your environment does not handle autoloading
require __DIR__ . '/../twilio-php/vendor/autoload.php';
// Use the REST API Client to make requests to the Twilio REST API
use Twilio\Rest\Client;

 //an array to display response
 header("Content-Type:application/json");
 $response = array();
 
 //if it is an api call 
 //that means a get parameter named api call is set in the URL 
 //and with this parameter we are concluding that it is an api call 
 if(isset($_GET['apicall'])){
 
 switch($_GET['apicall']){
 
 case 'signup':
 //checking the parameters required are available or not 
 if(isTheseParametersAvailable(array('d_id','d_firstname','d_secondname','d_email','d_contact','d_address','d_password'))){
 
 //getting the values
 $d_id = $_POST['d_id'];
 $d_firstname = $_POST['d_firstname'];
 $d_secondname = $_POST['d_secondname'];
 $d_email = $_POST['d_email']; 
 $d_contact = $_POST['d_contact'];
 $d_address = $_POST['d_address'];
 $d_password = ($_POST['d_password']);
  
 
 //checking if the user is already exist with this username or email
 //as the email and username should be unique for every user 
 // $stmt = $conn->prepare("SELECT d_id FROM donor WHERE d_email = ?");
 // $stmt->bind_param("ss", $d_email);
 // $stmt->execute();
 // $stmt->store_result();
 
 // //if the user already exist in the database 
 // if($stmt->num_rows > 0){
 // $response['error'] = true;
 // $response['message'] = 'User already registered';
 // $stmt->close();
 // }else{
 
 //if user is new creating an insert query 
 $stmt = $conn->prepare("INSERT INTO donor (d_id,d_firstname,d_secondname, d_email, d_contact, d_address, d_password) VALUES (?, ?, ?, ?,?,?,?)");
 $stm = $conn->prepare("INSERT INTO volunteer (v_id,v_firstname,v_secondname, v_email, v_contact, v_address, v_password) VALUES (?, ?, ?, ?,?,?,?)");
 $stmt->bind_param("sssssss", $d_id,$d_firstname,$d_secondname, $d_email, $d_contact, $d_address, $d_password);
 $stm->bind_param("sssssss", $d_id,$d_firstname,$d_secondname, $d_email, $d_contact, $d_address, $d_password);
 //if the user is successfully added to the database 
 if($stmt->execute() && $stm->execute()){
 
 //fetching the user back 
 // $stmt = $conn->prepare("SELECT id, id, username, email, gender FROM users WHERE username = ?"); 
 // $stmt->bind_param("s",$username);
 // $stmt->execute();
 // $stmt->bind_result($userid, $id, $username, $email, $gender);
 // $stmt->fetch();
 
 // $user = array(
 // 'id'=>$id, 
 // 'username'=>$username, 
 // 'email'=>$email,
 // 'gender'=>$gender
 // );
 $stm->close();
 $stmt->close();
 
 //adding the user data in response 
 $response['error'] = false; 
 $response['message'] = 'User registered successfully';

 // $response['user'] = $user; 
 }
	 // }
 
 
 }else{
 $response['error'] = true; 
 $response['message'] = 'required parameters are not available'; 
 }
 
 break; 
 
 //in this part we will handle the registration
 
 // break; 
 
 case 'login':
 if(isTheseParametersAvailable(array('v_email', 'v_password'))){
 //getting values 
 $email = $_POST['v_email'];
 $password = ($_POST['v_password']); 
 
  //creating the query 
 $stmt = $conn->prepare("SELECT v_email FROM volunteer WHERE v_email = ? AND v_password = ?");
 $stmt->bind_param("ss",$email,$password);
 
 $stmt->execute();
 
 $stmt->store_result();
 
 //if the user exist with given credentials 
 if($stmt->num_rows > 0){
 
 $stmt->bind_result($v_email);
 $stmt->fetch();
 
  $user = array(
 
 'email'=>$email,
 );
 
 $response['error'] = false; 
 $response['message'] = 'Login successfull'; 
 $response['user'] = $user; 
 }else{
 //if the user not found 
 $response['error'] = false; 
 $response['message'] = 'Invalid username or password';
 }
 }
 
 //this part will handle the login 
 
 break; 
 case 'demo':
 $response['message'] = 'Login successfull';
 break;

 case 'uploadFood':
 $id = $_POST['id'];
 $quantity = $_POST['quantity'];
 $packaging = $_POST['packaging'];
 $location = $_POST['location'];
 $picture = $_POST['picture']; 
 $pickup_time = $_POST['pickup_time'];
 
 $stmt = $conn->prepare("INSERT INTO food (food_id,quantity,packaging,location,picture,pickup_time) VALUES (? ,?, ?, ?, ?, ?)");
 $stmt->bind_param("ssssss", $id,$quantity,$packaging,$location,$picture,$pickup_time);
  $stmt->execute();
 
 $stmt->store_result();


 $id=1;
$loc=mysqli_query($conn,"select * from donor where d_id='$id'");
$count=mysqli_num_rows($loc);
			if ($count > 0) 
			{
				while($row = mysqli_fetch_array($loc))
				{
					$d_loc=$row['d_address'];		
				}
			}



$sql=mysqli_query($conn,"select * from volunteer where v_address LIKE '%$location%'");
$count=mysqli_num_rows($sql);
			if ($count > 0) 
			{ $i = 0;
				while($row = mysqli_fetch_array($sql))
				{
					$v_loc=$row['v_address'];
					$v_contact=$row['v_contact'];
					// sendMsg($v_contact);
					$v_id=$row['v_id'];
					
					$output[$i++]=$v_loc;
					
				}
				// echo json_encode($output);
			}

?>


<?php
// Your Account SID and Auth Token from twilio.com/console
$sid = 'AC9da241f21b007d19e8f3954bbd6c2758';
$token = '8aff357d005af6cc80a4a1b9618884d5';
$client = new Client($sid, $token);
// Use the client to do fun stuff like send text messages!
$client->messages->create(
    // the number you'd like to send the message to
    '+919820370292',
    array(
        // A Twilio phone number you purchased at twilio.com/console
        'from' => '+14797772236',
        // the body of the text message you'd like to send
        'body' => 'Hey, are you available at '.$location.'? Please send us your confirmation asap.'
    )
);
echo "Your message has been sent successfully";
?>

<?php



 
 // $stmt->bind_result($v_email);
 // $stmt->fetch();
 
 //  $user = array(
 
 // 'email'=>$email,
 // );
 $stmt->close();
 $response['error'] = false; 
 $response['message'] = 'Successfully Added'; 
 $response['volunteers'] = $output; 
 
 




 // $st->close();
 // $response['error'] = false; 
 // $response['message'] = 'Successfully added';
 break; 

 case 'needyPeople':
 if(isTheseParametersAvailable(array('location', 'no_of_people'))){
 //getting values 
 $location = $_POST['location'];
 $no_of_people = ($_POST['no_of_people']); 
 
  //creating the query 
 $stmt = $conn->prepare("INSERT INTO needy_people (location,number_of_needies) VALUES (? ,?)");
 $stmt->bind_param("ss", $location, $no_of_people);
  $stmt->execute();
 
 $stmt->store_result();
 
 //if the user exist with given credentials 
  $response['error'] = false; 
 $response['message'] = 'Uploaded Successfully'; 
 // if($stmt->num_rows > 0){
 
 // $stmt->bind_result($v_email);
 // $stmt->fetch();
 
 //  $user = array(
 
 // 'email'=>$email,
 // );
 
 // $response['error'] = false; 
 // $response['message'] = 'Login successfull'; 
 // $response['user'] = $user; 
 // }else{
 // //if the user not found 
 // $response['error'] = false; 
 // $response['message'] = 'Invalid username or password';
 // }
 }else{
 	$response['error'] = true; 
 $response['message'] = 'required parameters are not available'; 

 }
 
 //this part will handle the login 
 
 break;
 
 default: 
 $response['error'] = true; 
 $response['message'] = 'Invalid Operation Called';
 }
 
 }else{
 //if it is not api call 
 //pushing appropriate values to response array 
 $response['error'] = true; 
 $response['message'] = 'Invalid API Call';
 }
 
 //displaying the response in json structure 
 echo json_encode($response);
 function isTheseParametersAvailable($params){
 
 //traversing through all the parameters 
 foreach($params as $param){
 //if the paramter is not available
 if(!isset($_POST[$param])){
 //return false 
 return false; 
 }
 }
 //return true if every param is available 
 return true; 
 }
?>
