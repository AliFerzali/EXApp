<?php
require "dbConfig.php";
$db = new dbConfig();
$newconnection=mysqli_connect($db->host,$db->username,$db->password,$db->databasename);

$uname = $_POST['username'];
$pword = $_POST['password'];

if (isset($uname) && isset($pword) && $pword != "" && $uname != "")
{
    
    $sql = "select * from employee where username = '" . $uname . "'";
    $result = mysqli_query($newconnection,$sql);
    $row = mysqli_fetch_assoc($result);
    if (mysqli_num_rows($result) != 0)
    {
        $dbusername = $row['username'];
        $dbpassword = $row['password'];
        if ($dbusername == $uname && $dbpassword == $pword)
            echo "Login succeed!";
        else
            echo "Wrrong password had been entered! + $dbusername $dbpassword";
    }       
    else echo "Login failed!";
}else echo "All fields are required";