<?
$link = mysql_connect("localhost","maps","maps");

if (!$link) {
    die('Could not connect: ' . mysql_error());
}

$db_selected = mysql_select_db("maps");

if (!$db_selected) {
    die ('Can\'t use foo : ' . mysql_error());
}


function getParaForSql($name){
	return mysql_real_escape_string($_GET[$name]);
}

?>
