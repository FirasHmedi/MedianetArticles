<?php 
 
    $comments = array(); 
    $arrayContents = array();
    $arrayUsers = array(); 
 
    $i=0;
    $articleId =  $_GET['articleId'];  
 
        try {
            // Try Connect to the DB with new MySqli object - Params {hostname, userid, password, dbname}
            $mysqli = new mysqli("localhost", "root", "", "medianetarticles");

           
           $stmt = $mysqli->prepare("select  c.idUsers,c.contents from commentaires c   where  c.id = ?");

            $stmt->bind_param("i", $articleId);
            $stmt->execute(); // Execute the statement.
            $stmt->bind_result( $idUsers,$contents);
            $stmt->fetch();

             $stmt->close();
            $arrayUsers=explode(',', $idUsers);
            $arrayContents=explode(',', $contents);
          /*   $stmt = $mysqli->prepare("select  p.id,p.username,p.image from profiles p ");
                
              $stmt->execute();
              $stmt->bind_result($userId,$username, $image);*/
                
                     foreach ($arrayUsers as $value){
 
                          $stmt = $mysqli->prepare("select p.id,p.username,p.image from profiles p where p.id = ?");
                          $stmt->bind_param("i",$value);
                          $stmt->execute();
                          $stmt->bind_result($newuserId,$username, $image);
                           $stmt->fetch();
                        
                        $temp=[
                            'id'=>$articleId,
                            'userImage'=>$image,
                            'username'=>$username,
                            'content'=>$arrayContents[$i],
                        ];
                           array_push($comments, $temp);
                     
                      $i++;
                    $stmt->close();

                  }
    

               echo json_encode($comments);

         
            
        } catch (mysqli_sql_exception $e) { // Failed to connect? Lets see the exception details..
            echo "MySQLi Error Code: " . $e->getCode() . "<br />";
            echo "Exception Msg: " . $e->getMessage();
            exit(); // exit and close connection.
        }

        $mysqli->close();
 
 

     /*   $  = $mysqli->prepare("select  username, image from profiles  where id = ?");
            $ ->bind_param("i", (int)$value);
            $ ->execute(); // Execute the statement.
            $ ->bind_result($username, $image);
            $temp1=[
                "images"=>$image,
            ];
            $temp1=[
                "usernames"=>$username,
            ];
            array_push($images, $temp1);
            array_push($usernames, $temp2); 
          

            };*/

                /*   //looping through all the records
                while($stmt->fetch()){
                    
                    
                    //pushing fetched data in an array 
                    $temp = [
                        'id'=>$id,
                        'title'=>$title,
                        'summary'=>$summary,
                        'content'=>$content,
                        'comments'=>explode(',',$comments),
                        'ImgArr'=>explode(',',$ImgArr),  //exploding string ImgArr to an array 
                        'vidId'=>$vidId,
                        'PodUrl'=>$PodUrl,
                        'likes'=>$likes,
                        'categTitle'=>$categTitle,
                        'authorNom'=>$authorNom,

                    ];
                    
                    //pushing the array inside the articles array 
                    array_push($articles, $temp);
                }

                echo json_encode($articles);*/
             