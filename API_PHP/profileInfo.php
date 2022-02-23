<?php
    require "dbConfig.php";
    $db = new dbConfig();
    $newconnection=mysqli_connect($db->host,$db->username,$db->password,$db->databasename);
    
    if(isset($_POST['username']))
    {
        $Uname = $_POST['username'];
        $sql = "SELECT * FROM employee WHERE username = '".$Uname."'";
        $result = mysqli_query($newconnection,$sql);
        $row = mysqli_fetch_assoc($result);
        $arr = array();
        
        //use another array to insert the returned data
        // then push it  to the second array to later convert it
        // to JSON file.
        if (mysqli_num_rows($result) != 0)
        {   
            $arr['username'] = $row['username'];
            $arr['id']=$row['id'];
            $arr['email']= $row['email'];
            $arr['firstname']= $row['firstname'];
            $arr['secondname']= $row['secondname'];
            $arr['phonenumber']=$row['phonenumber'];
            $arr['password']=$row['password'];
            header('Content-Type: application/json; charset=utf-8');
            echo json_encode($arr);
        }
        else
            echo "No data fetched from DB";
    }else echo "all fileds are requiered";
?>