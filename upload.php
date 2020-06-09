<?php


// Create MySQL login values and 
// set them to your login information.
$username = "**";
$password = "**";
$host = "**";
$database = "**";

// Make the connect to MySQL or die
// and display an error.
$link = mysql_connect($host, $username, $password);
 if (!$link) {
      die('Could not connect: ' . mysql_error());
   }

   // Select your database
    mysql_select_db ($database);  

     session_start();
     if(!isset($_SESSION['username']))
    {
          die('You have no access to this page.');
    }
  else{
  $username = $_SESSION['username'];
   // Make sure the user actually 
   // selected and uploaded a file
    if (isset($_FILES['image']) && $_FILES['image']['size'] > 0) { 

  // Temporary file name stored on the server
  $tmpName  = $_FILES['image']['tmp_name'];  

  // Read the file 
  $fp      = fopen($tmpName, 'r');
  $data = fread($fp, filesize($tmpName));
  $data = addslashes($data);
  fclose($fp);


  // Create the query and insert
  // into our database.
  $query = "INSERT INTO Members WHERE username = '$username' ";
  $query .= "(image) VALUES ('$data')";
  $results = mysql_query($query, $link);

  // Print results
  print "Thank you, your file has been uploaded.";

 }
else {
 print "No image selected/uploaded";
 }
 }
  // Close our MySQL Link
  mysql_close($link);
  ?>