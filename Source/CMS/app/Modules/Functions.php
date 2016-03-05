<?php

namespace App\Modules;


	
class Functions
{
	//Functions that do not interact with DB
	//------------------------------------------------------------------------------
	//Retrieve a list of all .php files in models/languages
	static function getLanguageFiles()
	{
		$directory = "models/languages/";
		$languages = glob($directory . "*.php");
		
		//print each file name
		return $languages;
	}

	//Retrieve a list of all .css files in models/site-templates
	static function getTemplateFiles()
	{
		$directory = "models/site-templates/";
		$languages = glob($directory . "*.css");
		
		//print each file name
		return $languages;
	}

	//Retrieve a list of all .php files in root files folder
	static function getPageFiles()
	{
		$directory = "";
		$pages = glob($directory . "*.php");
		
		//print each file name
		foreach ($pages as $page)
		{
			$row[$page] = $page;
		}
		
		return $row;
	}

	//Destroys a session as part of logout
	static function destroySession($name)
	{
		if (isset($_SESSION[$name]))
		{
			$_SESSION[$name] = NULL;
			unset($_SESSION[$name]);
		}
	}

	//Generate a unique code
	static function getUniqueCode($length = "")
	{
		$code = md5(uniqid(rand(), true));
		if ($length !== "")
		{
			return substr($code, 0, $length);
		}
		else
		{
			return $code;
		}
	}

	static function getRandomString($chars = 8)
	{
		$letters = 'abcefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890';

		return substr(str_shuffle($letters), 0, $chars);
	}
	
	static function generateVerifyCode( )
	{
		return Functions::getRandomString(6);
	}

	//Generate an activation key
	static function generateActivationToken($gen = null)
	{
		//do
		//{
		//	$gen = md5(uniqid(mt_rand(), false));
		//}
		//while (validateActivationToken($gen));
		
		$gen = md5(uniqid(mt_rand(), true));

		return $gen;
	}

	//@ Thanks to - http://phpsec.org
	static function generateHash($plainText, $salt = null)
	{
		if ($salt === null)
		{
			$salt = substr(md5(uniqid(rand(), true)), 0, 25);
		}
		else
		{
			$salt = substr($salt, 0, 25);
		}

		$aaa = sha1($salt . $plainText);
		return $salt . sha1($salt . $plainText);
	}

	//Checks if an email is valid
	static function isValidEmail($email)
	{
	//    if (filter_var($email, FILTER_VALIDATE_EMAIL))
	//    {
	//        return true;
	//    }
	//    else
	//    {
	//        return false;
	//    }

		if (preg_match('|^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$|i', $email))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//Checks if an contact no is valid
	static function isValidContactNo($contactno)
	{
		return true;
	}

	//Inputs language strings from selected language.
	static function GetMessage($key, $markers = NULL)
	{
		require_once("Lang_En.php");
		
		if( $markers === NULL )
		{
			$str = $lang[$key];
		}
		else
		{
			//Replace any dyamic markers
			$str = $lang[$key];
			$iteration = 1;
			foreach ($markers as $marker)
			{
				$str = str_replace("%m" . $iteration . "%", $marker, $str);
				$iteration++;
			}
		}
		
		//Ensure we have something to return
		if ($str === "")
		{
			return ("No language key found");
		}
		else
		{
			return $str;
		}
	}

	//Checks if a string is within a min and max length
	static function minMaxRange($min, $max, $what)
	{
		if (strlen(trim($what)) < $min)
		{
			return true;
		}
		else if (strlen(trim($what)) > $max)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//Checks if a string is within a min length
	static function minRange($min, $what)
	{
		if (strlen(trim($what)) < $min)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//Checks if a string is within a max length
	static function maxRange($max, $what)
	{
		if (strlen(trim($what)) > $max)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//Replaces hooks with specified text
	static function replaceDefaultHook($str)
	{
		global $default_hooks, $default_replace;
		return (str_replace($default_hooks, $default_replace, $str));
	}

	//Displays error and success messages
	static function alertNotificationMessages($errors, $successes = null)
	{
		//Error block
		if (count($errors) > 0)
		{
			echo "<div class='notification-e'>
							 <img id='close_notification_f7c717ded4d80bebcb6f777f80e6840f7b48' class='cm-notification-close hand' src='/css/images/icons/icon_close.gif' width='13' height='13' border='0' alt='Close' title='Close'>
							 <div class='notification-header-e'>Error</div>
							 <div>";
			foreach ($errors as $error)
			{
					echo $error . "&nbsp;&nbsp;&nbsp;";
			}
			
			echo "</div></div>";
		}
//Success block
		if (count($successes) > 0)
		{
			echo "<div class='notification-n'>
							 <img id='close_notification_f7c717ded4d80bebcb6f777f80e6840f7b48' class='cm-notification-close hand' src='/css/images/icons/icon_close.gif' width='13' height='13' border='0' alt='Close' title='Close'>
							 <div class='notification-header-n'>Success</div>
							 <div>";
			foreach ($successes as $success)
			{
					echo $success;
			}
			echo "</div></div>";
		}
	}
	
	static function alertErrorMessageByID($str_msg_id, $arrParams = null)
	{
		Functions::alertNotificationMessages(array(Functions::GetMessage($str_msg_id, $arrParams)));
	}
	
	static function alertErrorMessage($str_message)
	{
		Functions::alertNotificationMessages(array($str_message));
	}
	
	static function alertSuccessMessage($str_message)
	{
		Functions::alertNotificationMessages(null, array($str_message));
	}

	//Completely sanitizes text
	static function sanitize($str)
	{
		return strtolower(strip_tags(trim(($str))));
	}

	static function getParameter($param, $default_value = "")
	{
		if (isset($_REQUEST[$param]) && $_REQUEST[$param] !== NULL)
		{
			if(is_array($_REQUEST[$param]))
				return $_REQUEST[$param];
			else
				return trim($_REQUEST[$param]);
		}

		return $default_value;
	}


	// Update a user's secure key
	static function updateSecureKey($email, $securekey)
	{
		global $mysqli, $db_table_prefix;
		$stmt = $mysqli->prepare("UPDATE " . $db_table_prefix . "users
				SET
				secure_key = ?
				WHERE
				email = ?");
		$stmt->bind_param("ss", $securekey, $email);
		$result = $stmt->execute();
		$stmt->close();
		return $result;
	}

	//Check if activation token exists in DB
	static function validateActivationToken($token, $lostpass = NULL)
	{
		global $mysqli, $db_table_prefix;
		if ($lostpass === NULL)
		{
			$stmt = $mysqli->prepare("SELECT active
					FROM " . $db_table_prefix . "users
					WHERE active = 0
					AND
					activation_token = ?
					LIMIT 1");
		}
		else
		{
			$stmt = $mysqli->prepare("SELECT active
					FROM " . $db_table_prefix . "users
					WHERE active = 1
					AND
					activation_token = ?
					AND
					lost_password_request = 1
					LIMIT 1");
		}
		
		$stmt->bind_param("s", $token);
		$stmt->execute();
		$stmt->store_result();
		$num_returns = $stmt->num_rows;
		$stmt->close();

		if ($num_returns > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	// Push functions
	// Google GCM
	static function push2Android($push_keys, $msg, $push_type = "Common")
	{
		$ret = true;

		$apiKey = "AIzaSyCfJ_4O6jIVOZwUF_x-M6tVU5EBvyHvqwQ";
		$headers = array(
				'Authorization: key=' . $apiKey,
				'Content-Type: application/json'
		);

		$arr = array();
		$arr['data'] = array();
		$arr['data']['msg'] = urlencode($msg);
		$arr['data']['type'] = $push_type;
		$arr['registration_ids'] = array();

		$total = sizeof($push_keys);
		$count = (int) ($total / 500);
		$remain = $total % 500;
		if ($remain !== 0)
		{
				$count = $count + 1;
		}

		if ($total > 0)
		{
			for ($i = 0; $i < $count; $i++)
			{
				$registrationIDs = array();
				$k = 0;
				if (($i === ($count - 1)) && $remain != 0)
				{
					for ($j = $i * 500; $j < $i * 500 + $remain; $j++)
					{
						$registrationIDs[$k] = $push_keys[$j];
						$k ++;
					}
				}
				else
				{
					for ($j = $i * 500; $j < ($i + 1) * 500; $j++)
					{
						$registrationIDs[$k] = $push_keys[$j];
						$k ++;
					}
				}

				$arr['registration_ids'] = $registrationIDs;
				$ch = curl_init();
				curl_setopt($ch, CURLOPT_URL, 'http://android.googleapis.com/gcm/send');
				curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
				curl_setopt($ch, CURLOPT_POST, true);
				curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
				curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
				curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($arr));

				// Execute post
				$reponse = curl_exec($ch);

				// Close connection
				curl_close($ch);

				// Get message from GCM server
				//$obj = json_decode($reponse, true);
				//var_dump($registrationIDs);

				sleep(1);
			}
		}
		else
		{
			$ret = false;
		}

		return $ret;
	}

	// iphone APNS
	static function push2IPhone($tokens, $msg, $push_type = "Common", $badge = 0)
	{
		$ret = true;
	
		//$apnsCert = 'D:\xampp\htdocs\smartaxa\models\masix_apns_for_dev.pem';
		//$apnsCert = '/var/www/html/ws/img/first/test/models/masix_apns_for_dev.pem';
		$apnsCert = '/home/axasmart/public_html/models/axaapns.pem';
	
		$passphrase = 'kgsiospush'; //carepetpush2014';
		$payload['aps'] = array('alert' => $msg, 'badge' => $badge, 'sound' => 'default', 'type' => $push_type);
		$message = json_encode($payload);
	
		$index = 0;
		$remain = 0;
		$apns = NULL;
		foreach ($tokens as $token)
		{
			$remain = $index % 500;
	
			if ($remain === 0)
			{
				if ($apns)
				{
					// Close connection
					fclose($apns);
					sleep(1);
				}
	
				// Create stream context
				$streamContext = stream_context_create();
				stream_context_set_option($streamContext, 'ssl', 'local_cert', $apnsCert); //ssl
				stream_context_set_option($streamContext, 'ssl', 'passphrase', $passphrase);
	
				$apns = stream_socket_client('ssl://gateway.push.apple.com:2195', $error, $errorString, 100, STREAM_CLIENT_CONNECT, $streamContext);
				if (!$apns)
				{
					return false;
				}
			}
	
			// Send push
			//$apnsMessage = chr ( 0 ) . chr ( 0 ) . chr ( 32 ) . pack ( 'H*', str_replace ( ' ', '', $token ) ) . chr ( 0 ) . chr ( strlen ( $message ) ) . $message;
			$apnsMessage = chr(0) . pack("n", 32) . pack('H*', str_replace(' ', '', $token)) . pack("n", strlen($message)) . $message;
			//$apnsMessage = chr(0) . pack("n", 32) . pack('H*', trim($token)) . pack("n", strlen($message)) . $message;
			$writeResult = fwrite($apns, $apnsMessage);
	
			$index ++;
	
	//            echo $apnsMessage . "<br>";
	//            echo sizeof($apnsMessage) . "<br>";
	//            echo $writeResult . "<br>";
		}
	
		if ($apns)
		{
			// Close connection
			fclose($apns);
		}
	
		return $ret;
	}

	static function smartaxaLog($username, $action, $content, $result)
	{
		$logClass = new SysLogs(0, $username);
		$logClass->addSysLog($action, $content, $result);
	}

	// Return timestamp from date string (2014-03-16 17:30:30)
	static function getSecondTime($date)
	{
		$yy = substr($date, 0, 4);
		$mm = substr($date, 5, 2);
		$dd = substr($date, 8, 2);
		$hh = substr($date, 11, 2);
		$ii = substr($date, 14, 2);
		$ss = substr($date, 17, 2);

		$sec_date = mktime($hh, $ii, $ss, $mm, $dd, $yy);
		return $sec_date;
	}

	static function fetchGroupsforAgent($category)
	{
		$groupModel = new Role();
		$param = array(
				"chat_writable"=>1
		);
		$groups = $groupModel->fetchGroupsforAgent($category, $param);
		return $groups;
	}

	static function json_decode($content, $assoc = false)
	{
		require_once 'JSON.php';
		if ($assoc)
		{
			$json = new Services_JSON(SERVICES_JSON_LOOSE_TYPE);
		}
		else
		{
			$json = new Services_JSON;
		}

		return $json->decode($content);
	}

	static function json_encode($content)
	{
		require_once 'JSON.php';
		$json = new Services_JSON;

		return $json->encode($content);
	}
}