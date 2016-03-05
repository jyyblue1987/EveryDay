<?php

require_once('openfire_api.php');

use GuzzleHttp\Client;

define('OPENFIRE_RESTAPI', 'http://localhost:9090/plugins/restapi/v1');

	function doRequest($type, $endpoint, $params=array())
	{
		$ch = curl_init();

		curl_setopt($ch, CURLOPT_URL, 'localhost:9090/plugins/restapi/v1' . $endpoint);

		$request_headers = array();
		$request_headers[] = 'Accept:application/json';
		$request_headers[] = 'Authorization:Basic ' . base64_encode("admin:admin");
		$request_headers[] = 'Content-Type: application/json';				
		
		curl_setopt($ch, CURLOPT_HTTPHEADER, $request_headers);			
		
		// Option to Return the Result, rather than just true/false
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
		
		curl_setopt($ch, CURLOPT_CUSTOMREQUEST, $type);		

		$postdata = json_encode($params);  
		curl_setopt($ch, CURLOPT_POSTFIELDS, $postdata);				
		
		$output=curl_exec($ch);

		$httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
		//echo $httpCode;
		
		curl_close($ch);

		return $output;	
	}

	/**
     * Get all registered users
     *
     * @return json|false       Json with data or error, or False when something went fully wrong
     */
    function getUsers()
    {
		$endpoint = '/users';        
    	return doRequest('get',$endpoint);
    }


    /**
     * Get information for a specified user
     *
     * @return json|false       Json with data or error, or False when something went fully wrong
     */
    function getUser($username)
    {
		
        $endpoint = '/users/'.$username; 
        return doRequest('get', $endpoint);
    }


    /**
     * Creates a new OpenFire user
     *
     * @param   string          $username   Username
     * @param   string          $password   Password
     * @param   string|false    $name       Name    (Optional)
     * @param   string|false    $email      Email   (Optional)
     * @param   string[]|false  $groups     Groups  (Optional)
     * @return  json|false                 Json with data or error, or False when something went fully wrong
     */
    function addUser($username, $password, $name=false, $email=false, $groups=false)
    {
        $endpoint = '/users'; 
        return doRequest('post', $endpoint, compact('username', 'password','name','email', 'groups'));
    }


    /**
     * Deletes an OpenFire user
     *
     * @param   string          $username   Username
     * @return  json|false                 Json with data or error, or False when something went fully wrong
     */
    function deleteUser($username)
    {
        $endpoint = '/users/'.$username; 
        return doRequest('delete', $endpoint);
    }

    /**
     * Updates an OpenFire user
     *
     * @param   string          $username   Username
     * @param   string|false    $password   Password (Optional)
     * @param   string|false    $name       Name (Optional)
     * @param   string|false    $email      Email (Optional)
     * @param   string[]|false  $groups     Groups (Optional)
     * @return  json|false                 Json with data or error, or False when something went fully wrong
     */
    function updateUser($username, $password, $name=false, $email=false, $groups=false)
    {
        $endpoint = '/users/'.$username; 
        return doRequest('put', $endpoint, compact('username', 'password','name','email', 'groups'));
    }

     /**
     * locks/Disables an OpenFire user
     *
     * @param   string          $username   Username
     * @return  json|false                 Json with data or error, or False when something went fully wrong
     */
    function lockoutUser($username)
    {
        $endpoint = '/lockouts/'.$username; 
        return doRequest('post', $endpoint);
    }


    /**
     * unlocks an OpenFire user
     *
     * @param   string          $username   Username
     * @return  json|false                 Json with data or error, or False when something went fully wrong
     */
    function unlockUser($username)
    {
        $endpoint = '/lockouts/'.$username; 
        return doRequest('delete', $endpoint);
    }


    /**
     * Adds to this OpenFire user's roster
     *
     * @param   string          $username       Username
     * @param   string          $jid            JID
     * @param   string|false    $name           Name         (Optional)
     * @param   int|false       $subscription   Subscription (Optional)
     * @return  json|false                     Json with data or error, or False when something went fully wrong
     */
    function addToRoster($username, $jid, $name=false, $subscription=false)
    {
        $endpoint = '/users/'.$username.'/roster';
        return doRequest('post', $endpoint, compact('jid','name','subscription'));
    }


    /**
     * Removes from this OpenFire user's roster
     *
     * @param   string          $username   Username
     * @param   string          $jid        JID
     * @return  json|false                 Json with data or error, or False when something went fully wrong
     */
    function deleteFromRoster($username, $jid)
    {
        $endpoint = '/users/'.$username.'/roster/'.$jid;
        return doRequest('delete', $endpoint, $jid);
    }

    /**
     * Updates this OpenFire user's roster
     *
     * @param   string          $username           Username
     * @param   string          $jid                 JID
     * @param   string|false    $nickname           Nick Name (Optional)
     * @param   int|false       $subscriptionType   Subscription (Optional)
     * @return  json|false                          Json with data or error, or False when something went fully wrong
     */
    function updateRoster($username, $jid, $nickname=false, $subscriptionType=false)
    {
        $endpoint = '/users/'.$username.'/roster/'.$jid;
        return doRequest('put', $endpoint, $jid, compact('jid','username','subscriptionType'));     
    }

    /**
     * Get all groups
     *
     * @return  json|false      Json with data or error, or False when something went fully wrong
     */
    function getGroups()
    {
        $endpoint = '/groups';
        return doRequest('get', $endpoint);
    }

    /**
     *  Retrieve a group
     *
     * @param  string   $name                       Name of group
     * @return  json|false                          Json with data or error, or False when something went fully wrong
     */
    function getGroup($name)
    {
        $endpoint = '/groups/'.$name;
        return doRequest('get', $endpoint);
    }

    /**
     * Create a group 
     *
     * @param   string   $name                      Name of the group
     * @param   string   $description               Some description of the group
     *
     * @return  json|false                          Json with data or error, or False when something went fully wrong
     */
    function createGroup($name, $description = false)
    {
        $endpoint = '/groups/';
        return doRequest('post', $endpoint, compact('name','description'));
    }

    /**
     * Delete a group
     *
     * @param   string      $name               Name of the Group to delete
     * @return  json|false                          Json with data or error, or False when something went fully wrong
     */
    function deleteGroup($name)
    {
        $endpoint = '/groups/'.$name;
        return doRequest('delete', $endpoint);
    }

    /**
     * Update a group (description)
     *
     * @param   string      $name               Name of group
     * @param   string      $description        Some description of the group
     *
     */
    function updateGroup($name,  $description)
    {
        $endpoint = '/groups/'.$name;
        return doRequest('put', $endpoint, compact('name','description'));
    }
 
?>


