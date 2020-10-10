<?php 
 
    $articles = array(); 
    $page = $_GET['page'];  
    $start = 0; 
    $limit = $_GET['limit'];  
    $optArray=array();
    $response = array();
        try {
            // Try Connect to the DB with new MySqli object - Params {hostname, userid, password, dbname}
            $mysqli = new mysqli("localhost", "root", "", "medianetarticles");

 
  
  if((isset($_POST['optArray']))&&(isset($_POST['sortType']))){

            $total = mysqli_num_rows(mysqli_query($mysqli, "select id from articles "));
            $page_limit = $total/$limit; 

             if($page<=$page_limit){
                $start = $page * $limit; 
            
             $optArray=explode(',',$_POST['optArray']);
             $sortType=$_POST['sortType'];
                    
             $query="select a.id,a.title,a.summary,a.content,a.ImgArr,a.vidId,a.PodUrl,a.likes,c.title,au.nomComplet,a.date,a.commentsNbr from articles a,categories c ,auteurs au where  a.CategId=c.CategId and a.AuteurId=au.AuteurId and c.title in (?,?,?,?) order by a.".$sortType." desc limit $start, $limit  ;";

            $statement = $mysqli->prepare($query);    
            $statement->bind_param("ssss",$optArray[0],$optArray[1],$optArray[2],$optArray[3]);
            $statement->execute(); // Execute the statement.
            $statement->bind_result($id, $title,$summary,$content,$ImgArr,$vidId,$PodUrl,$likes,$categTitle,$authorNom,$date,$commentsNbr);
                 
                //looping through all the records
                while($statement->fetch()){
                    //pushing fetched data in an array 
                    $temp = [
                        'id'=>$id,
                        'title'=>$title,
                        'summary'=>$summary,
                        'content'=>$content,
                        'ImgArr'=>explode(',',$ImgArr),  //exploding string ImgArr to an array 
                        'vidId'=>$vidId,
                        'PodUrl'=>$PodUrl,
                        'likes'=>$likes,
                        'categTitle'=>$categTitle,
                        'authorNom'=>$authorNom,
                        'date'=>$date,
                        'commentsNbr'=>$commentsNbr,

                    ];
                     //pushing the array inside the articles array 
                    array_push($articles, $temp);
                }
                $response['message'] = 'Page '.$page.' Loaded'; 
                $response['article'] = $articles;
              
            }
            else{
                      $response['message'] = 'Pages are over'; 
            }
                }
                else
                $response['message'] = 'Invalid Operation Call'; 
            

        } catch (mysqli_sql_exception $e) { // Failed to connect? Lets see the exception details..
            echo "MySQLi Error Code: " . $e->getCode() . "<br />";
            echo "Exception Msg: " . $e->getMessage();
            $response['message'] = 'Error connecting to database'; 
            exit(); // exit and close connection.
        }

        echo json_encode($response);

        $mysqli->close();
 