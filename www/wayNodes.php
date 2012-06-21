<?
header("Content-Type: application/xml; charset=utf-8",true);
header("Access-Control-Allow-Origin: *",true);

require("mySqlConnect.php");
require("getFactor.php");


$sqlStm = "SELECT * FROM maps.wayNodeWayJoin WHERE longitude<".getParaForSql("lonMax")." AND latitude<".getParaForSql("latMax")." and longitude>".getParaForSql("lonMin")." AND latitude>".getParaForSql("latMin").";";
$wn = mysql_query($sqlStm);

$xmlWriter = new XMLWriter();
$xmlWriter->openURI("php://output");
$xmlWriter->startDocument("1.0", "UTF-8");
$xmlWriter->startElement("ways");
$xmlWriter->startElement("bounds");
$xmlWriter->writeAttribute("minlat",doubleval($_GET["latMin"])*$factor);
$xmlWriter->writeAttribute("maxlat",doubleval($_GET["latMax"])*$factor);
$xmlWriter->writeAttribute("minlon",doubleval($_GET["lonMin"])*$factor);
$xmlWriter->writeAttribute("maxlon",doubleval($_GET["lonMax"])*$factor);
$xmlWriter->endElement();
$row = mysql_fetch_object($wn);
while($row!=null)
{
  $wayId=$row->wayId;
  $xmlWriter->startElement("way");
  $xmlWriter->writeAttribute("id", $row->wayId);
  $xmlWriter->writeAttribute("name", utf8_encode($row->name));
  $xmlWriter->writeAttribute("type", $row->type);
  do {
	  $xmlWriter->startElement("node");
	  $xmlWriter->writeAttribute("lon", $row->longitude*$factor);
	  $xmlWriter->writeAttribute("lat", $row->latitude*$factor);
	  $xmlWriter->endElement();
  	$row = mysql_fetch_object($wn);
  } while($row != null && $wayId == $row->wayId);
  $xmlWriter->endElement();
}
$xmlWriter->endElement();
$xmlWriter->endDocument();
$xmlWriter->flush();
?>
