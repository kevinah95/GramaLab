<?php
class Database 
{
    private $_Host;
    private $_User;
    private $_Password;
    private $_DataBaseName;
    private $_Connection;
 
    function Database($pHost, $pUser, $pPassword, $pDataBaseName) 
	{
        $this->_Host = $pHost;
        $this->_User = $pUser;
        $this->_Password = $pPassword;
		$this->_DataBaseName = $pDataBaseName;
        $this->connect();
		mysqli_set_charset($this->_Connection, "UTF8");
    }
	
	public function get_Connection()
	{
		return $this->_Connection;
	}
	
	public function doQuery($pQuery)
	{
		if ($this->_Connection->multi_query($pQuery)) 
		{
		    do 
		    {
		        if ($result = $this->_Connection->store_result()) 
		        {
		        	$rows = array();
		            while ($row = $result->fetch_assoc()) 
		            {
		                $rows[] = $row;
		            }
		            $result->free();
		            return json_encode($rows);
		        }
		        else 
	        	{ 
        			echo $this->_Connection->error;
        		}
		    } while ($this->_Connection->next_result() && $this->_Connection->more_results());
		}
		else 
		{ 
			echo $this->_Connection->error;
		}
    }
	 
  	public function close() 
	{
        mysqli_close($this->_Connection);
    }
	
    private function connect() 
	{
        if (!$this->_Connection = mysqli_connect($this->_Host, $this->_User, $this->_Password, $this->_DataBaseName))
		{
            die("Could not create database connection");
        }
    }
}
?>