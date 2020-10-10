<?php 
 
 $mysqli = new mysqli("localhost", "root", "", "medianetarticles");
	
	$response = array();
	
	if(isset($_GET['apicall'])){
		
		switch($_GET['apicall']){
			
		case 'signup':
			if(isTheseParametersAvailable(array('username','email','password','image'))){
				$username = $_POST['username']; 
				$email = $_POST['email']; 
				$password = md5($_POST['password']);
				$image = $_POST['image']; 
					
				$stmt = $mysqli->prepare("SELECT id FROM profiles WHERE username = ? OR email = ?");
				$stmt->bind_param("ss", $username, $email);
				$stmt->execute();
				$stmt->store_result();
					
				if($stmt->num_rows > 0){
					$response['error'] = true;
					$response['message'] = 'User already registered';
					$stmt->close();
				}else{
					$stmt = $mysqli->prepare("INSERT INTO profiles (username, email, password, image) VALUES (?, ?, ?, ?)");
					$stmt->bind_param("ssss", $username, $email, $password, $image);
 
					if($stmt->execute()){
						$stmt = $mysqli->prepare("SELECT  id, username, email, image FROM profiles WHERE username = ?"); 
						$stmt->bind_param("s",$username);
						$stmt->execute();
						$stmt->bind_result($id, $username, $email, $image);
						$stmt->fetch();
							
						$user = array(
							'id'=>$id, 
							'username'=>$username, 
							'email'=>$email,
							'image'=>$image
						);
							
						$stmt->close();
							
						$response['error'] = false; 
						$response['message'] = 'User registered successfully'; 
						$response['user'] = $user; 
					}
				}
					
			}else{
				$response['error'] = true; 
				$response['message'] = 'required parameters are not available'; 
			}
				
		break; 
			
			case 'login':
				
				if(isTheseParametersAvailable(array('username', 'password'))){
					
					$username = $_POST['username'];
					$email =  $_POST['username'];
					$password = md5($_POST['password']); 
					
					$stmt = $mysqli->prepare("SELECT id, username, email, image FROM profiles WHERE ( username = ? or email = ? )AND password = ?");
					$stmt->bind_param("sss",$username,$email, $password);
					
					$stmt->execute();
					
					$stmt->store_result();
					
					if($stmt->num_rows > 0){
						
						$stmt->bind_result($id, $username, $email, $image);
						$stmt->fetch();
						
						$user = array(
							'id'=>$id, 
							'username'=>$username, 
							'email'=>$email,
							'image'=>$image
						);
						
						$response['error'] = false; 
						$response['message'] = 'Login successfull'; 
						$response['user'] = $user; 
					}else{
						$response['error'] = false; 
						$response['message'] = 'Invalid username or email or password';
					}
				}
			break; 
			
			default: 
				$response['error'] = true; 
				$response['message'] = 'Invalid Operation Called';
		}
		
	}else{
		$response['error'] = true; 
		$response['message'] = 'Invalid API Call';
	}
	
	echo json_encode($response);
	
	function isTheseParametersAvailable($params){
		
		foreach($params as $param){
			if(!isset($_POST[$param])){
				return false; 
			}
		}
		return true; 
	}