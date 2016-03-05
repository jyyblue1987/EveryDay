<?php

$output_dir = "../uploads/";
if (isset($_FILES["myfile"]))
{
    $ret = array();

    $error = $_FILES["myfile"]["error"];
    //You need to handle  both cases
    //If Any browser does not support serializing of multiple files using FormData() 
    if (!is_array($_FILES["myfile"]["name"])) //single file
    {
        $fileName = $_FILES["myfile"]["name"];
		$ext = pathinfo($fileName, PATHINFO_EXTENSION);	
		$filename1 = "ad_".time().".".$ext;
        move_uploaded_file($_FILES["myfile"]["tmp_name"], $output_dir . $filename1);
        $ret[] = $filename1;
    }
    else  //Multiple files, file[]
    {
        $fileCount = count($_FILES["myfile"]["name"]);
        for ($i = 0; $i < $fileCount; $i++)
        {
            $fileName = $_FILES["myfile"]["name"][$i];
			$ext = pathinfo($fileName, PATHINFO_EXTENSION);	
			$filename1 = "ad_".time().".".$ext;
            move_uploaded_file($_FILES["myfile"]["tmp_name"][$i], $output_dir . $filename1);
            $ret[] = $filename1;
        }
    }
    
    echo $ret[0];//json_encode($ret);
}
?>