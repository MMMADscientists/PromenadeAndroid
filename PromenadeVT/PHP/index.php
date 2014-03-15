<?php
/**
 * File to handle all API requests
 * Accepts GET and POST
 * 
 * Each request will be identified by TAG
 * Response will be JSON data
 
  /**
 * check for POST request 
 */
if (isset($_POST['tag']) && $_POST['tag'] != '') {
    // get tag
    $tag = $_POST['tag'];
 
    // include db handler
    require_once 'include/DB_Functions.php';
    $db = new DB_Functions();
 
    // response Array
    $response = array("tag" => $tag, "success" => 0, "error" => 0);
 
    // check for tag type
    if ($tag == 'login') {
        // Request type is check Login
        $email = $_POST['email'];
        $password = $_POST['password'];
 
        // check for username
        $username = $db->getUserByEmailAndPassword($email, $password);
        if ($username != false) {
            // username found
            // echo json with success = 1
            $response["success"] = 1;
            $response["uid"] = $username["unique_id"];
            $response["username"]["name"] = $username["name"];
            $response["username"]["email"] = $username["email"];
            //$response["username"]["created_at"] = $username["created_at"];
            //$response["username"]["updated_at"] = $username["updated_at"];
            echo json_encode($response);
        } else {
            // username not found
            // echo json with error = 1
            $response["error"] = 1;
            $response["error_msg"] = "Incorrect email or password!";
            echo json_encode($response);
        }
    } else if ($tag == 'register') {
        // Request type is Register new username
        $name = $_POST['name'];
        $email = $_POST['email'];
        $password = $_POST['password'];
 
        // check if username is already existed
        if ($db->isUserExisted($email)) {
            // username is already existed - error response
            $response["error"] = 2;
            $response["error_msg"] = "username already existed";
            echo json_encode($response);
        } else {
            // store username
            $username = $db->storeUser($name, $email, $password);
            if ($username) {
                // username stored successfully
                $response["success"] = 1;
                $response["uid"] = $username["unique_id"];
                $response["username"]["name"] = $username["name"];
                $response["username"]["email"] = $username["email"];
                //$response["username"]["created_at"] = $username["created_at"];
                //$response["username"]["updated_at"] = $username["updated_at"];
                echo json_encode($response);
            } else {
                // username failed to store
                $response["error"] = 1;
                $response["error_msg"] = "Error occured in Registartion";
                echo json_encode($response);
            }
        }
    } else {
        echo "Invalid Request";
    }
} else {
    echo "Access Denied";
}
?>