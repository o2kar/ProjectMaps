<?
header("Content-Type: application/xml; charset=utf-8",true);
header("Access-Control-Allow-Origin: *",true);

require("mySqlConnect.php");
require("getFactor.php");

$wn = mysql_query("SELECT greenspaceNodes.greenspaceId, greenspaceNodes.longitude, greenspaceNodes.latitude, greenspace.type, greenspace.subtype FROM greenspaceNodes, greenspace WHERE greenspace.id = greenspaceId AND greenspaceNodes.longitude<".getParaForSql("lonMax")." AND greenspaceNodes.latitude<".getParaForSql("latMax")." and greenspaceNodes.longitude>".getParaForSql("lonMin")." AND greenspaceNodes.latitude>".getParaForSql("latMin")." ORDER BY greenspaceId, posCode");

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
  $wayId=$row->greenspaceId;
  $xmlWriter->startElement("way");
  $xmlWriter->writeAttribute("id", $row->greenspaceId);
  if($row->name!=null) {
    $xmlWriter->writeAttribute("name", $row->name);
  }
  $xmlWriter->writeAttribute("type", $row->type);
  $xmlWriter->writeAttribute("subtype", $row->subtype);
  do{
	  $xmlWriter->startElement("node");
	  $xmlWriter->writeAttribute("lon", $row->longitude*$factor);
	  $xmlWriter->writeAttribute("lat", $row->latitude*$factor);
	  $xmlWriter->endElement();
  	$row = mysql_fetch_object($wn);
  } while($row != null && $wayId == $row->greenspaceId);
  $xmlWriter->endElement();
}
$xmlWriter->endElement();
$xmlWriter->endDocument();
$xmlWriter->flush();
?>
