<?php

class dbConfig
{
    public $host;
    public $username;
    public $password;
    public $databasename;

    public function __construct()
    {

        $this->host = 'localhost';
        $this->username = 'root';
        $this->password = '';
        $this->databasename = 'askas';

    }
}

?>