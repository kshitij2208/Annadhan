<?php
$username = "**";
$password = "**";
$host = "**";
$database = "**";
$link = mysql_connect($host, $username, $password);
 if (!$link) 
 {
      die('Could not connect: ' . mysql_error());
 }
    mysql_select_db ($database);  
    session_start();
    if(!isset($_SESSION['username']))
    {
        die('You have no access to this page.');
    }
  else
  {
  $username = $_SESSION['username'];
    if (isset($_FILES['image']) && $_FILES['image']['size'] > 0) 
	{ 
  $tmpName  = $_FILES['image']['tmp_name'];  
  $fp      = fopen($tmpName, 'r');
  $data = fread($fp, filesize($tmpName));
  $data = addslashes($data);
  fclose($fp);
  $query = "INSERT INTO Members WHERE username = '$username' ";
  $query .= "(image) VALUES ('$data')";
  $results = mysql_query($query, $link);
  print "Thank you, your file has been uploaded.";
 }
else 
{
 print "No image selected/uploaded";
 }
 }
  mysql_close($link);
 ?>