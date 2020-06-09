<?php
// Get the PHP helper library from twilio.com/docs/php/install
include('./vendor/autoload.php');
use Twilio\Rest\Client; // Loads the library 

// Your Account Sid and Auth Token from twilio.com/user/account
$sid = "AC9da241f21b007d19e8f3954bbd6c2758";
$token = "8aff357d005af6cc80a4a1b9618884d5";
$client = new Client($sid, $token);

$validationRequest = $client->validationRequests->create(
    "+contact_no",
    array(
        "friendlyName" => "Volunteer verification"
    )
);

echo $validationRequest->validationCode;