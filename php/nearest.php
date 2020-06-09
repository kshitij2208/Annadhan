<?php
$con = mysqli_connect("localhost","root","") or die("unable to connect");
mysqli_select_db($con,'annadhan');
if($con){
	//echo "success";
}
else
	echo "unable to connect";
?>

<?php
//echo "hello";
header("Content-Type: application/json; charset=UTF-8");
$output=array();
$id=1;
$loc=mysqli_query($con,"select * from donor where d_id='$id'");
$count=mysqli_num_rows($loc);
			if ($count > 0) 
			{
				while($row = mysqli_fetch_array($loc))
				{
					$d_loc=$row['d_address'];		
				}
			}



$sql=mysqli_query($con,"select * from volunteer where v_address LIKE '%$d_loc%'");
$count=mysqli_num_rows($sql);
			if ($count > 0) 
			{ $i = 0;
				while($row = mysqli_fetch_array($sql))
				{
					$v_loc=$row['v_address'];
					$v_contact=$row['v_contact'];
					sendMsg($v_contact);
					$v_id=$row['v_id'];
					
					$output[$i++]=$v_loc;
					
				}
				echo json_encode($output);
			}
			
			function sendMsg($v_contact)
			{
				
				//echo "hello".$v_contact;
				
			}
			
			
			
 
?>

