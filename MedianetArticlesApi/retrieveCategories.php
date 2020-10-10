<?php 
// going to use above code 
//require 'database.php'; 
  
// printing column name above the data 
 
$categories= array();

 $jsonData = array();
try {
            // Try Connect to the DB with new MySqli object - Params {hostname, userid, password, dbname}
            $mysqli = new mysqli("localhost", "root", "", "medianetarticles");

            
            $statement = $mysqli->prepare("select CategId, title,image from categories");
            $statement->execute(); // Execute the statement.
            $statement->bind_result($CategId, $title,$image);
                 
                //looping through all the records
                while($statement->fetch()){
                    
                    //pushing fetched data in an array 
                    $temp = [
                        'CategId'=>$CategId,
                        'title'=>$title,
                        'image'=>$image,
                       
                    ];
                    
                    //pushing the array inside the categories array 
                    array_push($categories, $temp);
                }

                echo json_encode($categories);
  
        } catch (mysqli_sql_exception $e) { // Failed to connect? Lets see the exception details..
            echo "MySQLi Error Code: " . $e->getCode() . "<br />";
            echo "Exception Msg: " . $e->getMessage();
            exit(); // exit and close connection.
        }

        $mysqli->close();
?> 