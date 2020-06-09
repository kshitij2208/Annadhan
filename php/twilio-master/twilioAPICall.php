<?php 
 
// Get the PHP helper library from twilio.com/docs/php/install 
include('./vendor/autoload.php');
use Twilio\Rest\Client; // Loads the library 

 
$account_sid = 'AC9da241f21b007d19e8f3954bbd6c2758'; 
$auth_token = '8aff357d005af6cc80a4a1b9618884d5'; 
$client = new Client($account_sid, $auth_token); 

$user = '+xxxxxxxxxx';
/*$user = '+9820370292';*/
$TwilioNumber = '+14797772236';
$Body = "Welcome to Twilio Session !";
 
try {
        // Initiate a new outbound call
        $call = $client->account->calls->create(
            // Change the 'To' number below to whatever number you'd like 
            // to call.
            $user,

            // Change the 'From' number below to be a valid Twilio number 
            // that you've purchased or verified with Twilio.
            $TwilioNumber,

            //  Set the URL Twilio will request when the call is answered.
            array("url" => "http://localhost/twilio/basicCallResponse.php")
        );
        echo "Started call: " . $call->sid;

    } catch (Exception $e) {
        echo "Error: " . $e->getMessage();
    }