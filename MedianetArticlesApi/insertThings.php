<?php 
    $like=0;
    $newComments=array();
    $response = array();
    $arrayContents = array();
    $arrayUsers = array(); 
    $i=0;
try {
            // Try Connect to the DB with new MySqli object - Params {hostname, userid, password, dbname}
            $mysqli = new mysqli("localhost", "root", "", "medianetarticles");
    
    if(isset($_GET['apicall'])){
        
        switch($_GET['apicall']){
            
            case 'comments':
                if(isTheseParametersAvailable(array('idArticle','content','idUser'))){
          
                $idArticle = (int)$_POST['idArticle']; 
                $content = $_POST['content'];   
                $idUser = (int)$_POST['idUser']; 
                $stmt = $mysqli->prepare("select c.idUsers,c.contents from commentaires c where c.id = ?;");
                $stmt->bind_param("i",$idArticle);
                $stmt->execute(); // Execute the statement.
                 $stmt->bind_result($idUsers,$contents);
                 $stmt->fetch();
                 $stmt->close();

                if($idUsers==""){
                $idUsers=$idUser;
                $contents=$content;

                }
                else{
                $idUsers=$idUsers.','.$idUser;
                $contents=$contents.','.$content;
                    }
              
               $stmt = $mysqli->prepare("UPDATE commentaires set idUsers = ? , contents = ? WHERE id = ?;");
               $stmt->bind_param("ssi",$idUsers,$contents,$idArticle);
               $stmt->execute(); // Execute the statement
               $stmt->close();


                $arrayUsers=explode(',', $idUsers);
                $arrayContents=explode(',', $contents);
                $commentsNbr=sizeof($arrayContents);
                     foreach ($arrayUsers as $value){
                          $stmt = $mysqli->prepare("select p.username,p.image from profiles p where p.id=?");
                          $stmt->bind_param("i",$value);
                          $stmt->execute();
                          $stmt->bind_result($username, $image);
                          $stmt->fetch();
                           
                        $temp=[
                            'id'=>$idArticle,
                            'userImage'=>$image,
                            'username'=>$username,
                            'content'=>$arrayContents[$i],
                        ];
                           array_push($newComments, $temp);
                     
                      $i++;
                    $stmt->close();
                  }
            
                $stmt = $mysqli->prepare("UPDATE articles set commentsNbr = ? WHERE id = ?;");
                $stmt->bind_param("ii",$commentsNbr,$idArticle);
                $stmt->execute();
                $stmt->close();

              echo json_encode($newComments);
         
                

               }else{
                    echo 'required parameters are not available'; 
                } 
 
            break; 
            
            case 'addDelLike':
                
                if(isTheseParametersAvailable(array('idUser','idArticle'))){
                    
                    $idUser = (int)$_POST['idUser'];
                    $idArticle =  (int)$_POST['idArticle'];
               

                  $stmt = $mysqli->prepare("select likes from profiles where id= ?;");
                  $stmt->bind_param("i",$idUser);
                  $stmt->execute(); // Execute the statement.
                  $stmt->store_result();
                  $stmt->bind_result($oldLikes);
                  $stmt->fetch();
                
                if (strpos($oldLikes,','.$idArticle) !== false){
                  $oldLikes=str_replace(','.$idArticle, "", $oldLikes);
                  $like=-1;
                }
                else{
                  $oldLikes=$oldLikes.','.$idArticle;
                  $like=1;
                 }

                  $stmt = $mysqli->prepare("select likes from articles where id= ?;");
                  $stmt->bind_param("i",$idArticle);
                  $stmt->execute(); // Execute the statement.
                  $stmt->store_result();
                  $stmt->bind_result($likes);
                  $stmt->fetch();
                  $likes=$likes+$like;

                 $stmt = $mysqli->prepare("UPDATE articles set likes = ?   where id= ?;");
                 $stmt->bind_param("ii",$likes,$idArticle);
                 $stmt->execute(); // Execute the statement.
              
                 $stmt = $mysqli->prepare("UPDATE profiles set likes = ?  WHERE id = ?;");
                 $stmt->bind_param("si",$oldLikes,$idUser);
                 $stmt->execute(); // Execute the statement
                    $new_string = preg_replace("/[^0-9]/", "", (string)$likes);

                    echo $new_string; // Returns 12345
                     
                      $stmt->close();
                }
                else{
                    echo 'Invalid parameters';
                }

            break; 
        
            case 'profileEdit':

                  if(isTheseParametersAvailable(array('id','username','password','image'))){

                    $id = $_POST['id'];
                    $username = $_POST['username']; 
                    $password = md5($_POST['password']);
                    $image = $_POST['image']; 
                    
                    $stmt = $mysqli->prepare("SELECT id FROM profiles WHERE username = ? ");
                    $stmt->bind_param("s", $username);
                    $stmt->execute();
                    $stmt->store_result();
                    
                    if($stmt->num_rows > 0){
                        $response['error'] = true;
                        $response['message'] = 'Username already chosen';
                        $stmt->close();
                    }else{

                        if($image==""){
                        $stmt = $mysqli->prepare("UPDATE profiles set username = ? , password = ? WHERE id = ?;");
                        $stmt->bind_param("ssi", $username,$password,$id);
                        }else{
                        $stmt = $mysqli->prepare("UPDATE profiles set username = ? , password = ? ,image = ? WHERE id = ?;");
                        $stmt->bind_param("sssi", $username,$password, $image,$id);
                        }
                        if($stmt->execute()){
                            $stmt = $mysqli->prepare("SELECT  id, username, email, image FROM profiles WHERE id = ?;"); 
                            $stmt->bind_param("i",$id);
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
                            $response['message'] = 'Informations modified successfully'; 
                            $response['user'] = $user; 
                        }
                    }
                    
                }else{
                    $response['error'] = true; 
                    $response['message'] = 'required parameters are not available'; 
                }
                
                    echo json_encode($response);

            break; 

 


            default: 
                echo 'Invalid Operation Called';
        
        }

    }else{
        echo 'Invalid API Call';
    }
           
        } catch (mysqli_sql_exception $e) { // Failed to connect? Lets see the exception details..
            echo "MySQLi Error Code: " . $e->getCode() . "<br />";
            echo "Exception Msg: " . $e->getMessage();
            exit(); // exit and close connection.
        }
        
        $mysqli->close();



    function isTheseParametersAvailable($params){
        
        foreach($params as $param){
            if(!isset($_POST[$param])){
                return false; 
            }
        }
        return true; 
    }


          /*  case 'delLike':
                
              if(isTheseParametersAvailable(array('idUser','idArticle'))){
                    
                    $idUser = $_POST['idUser'];
                    $idArticle =  $_POST['idArticle'];
               
                  $stmt = $mysqli->prepare("select likes from articles where id= ?;");
                  $stmt->bind_param("i",$idArticle);
                  $stmt->execute(); // Execute the statement.
                  $stmt->store_result();
                  $stmt->bind_result($likes);
                  $stmt->fetch();
                  $likes=$likes-1;

                    $stmt = $mysqli->prepare("UPDATE articles set likes = ?   where id= ?;");
                    $stmt->bind_param("ii",$likes,$idArticle);
                    $stmt->execute(); // Execute the statement.
                  
                  $stmt = $mysqli->prepare("select likes from profiles where id= ?;");
                  $stmt->bind_param("i",$idUser);
                  $stmt->execute(); // Execute the statement.
                  $stmt->store_result();
                  $stmt->bind_result($oldLikes);
                  $stmt->fetch();
                  
                  if (strpos($oldLikes,','.$idArticle) !== false) 
                    $oldLikes=str_replace(','.$idArticle, "", $oldLikes);
                        
               $stmt = $mysqli->prepare("UPDATE profiles set likes = ?  WHERE id = ?;");
               $stmt->bind_param("si",$oldLikes,$idUser);
               $stmt->execute(); // Execute the statement

                      $stmt->close();
                }
                else{
                    echo 'Invalid parameters';
                }
            break; */


