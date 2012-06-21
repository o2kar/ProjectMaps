<?
header("Content-Type: application/xml; charset=utf-8",true);
header("Access-Control-Allow-Origin: *",true);

require("mySqlConnect.php");
require("getFactor.php");

$wn = mysql_query("SELECT railwayNodes.railwayId, railwayNodes.longitude, railwayNodes.latitude, railway.type FROM railwayNodes, railway WHERE railway.id = railwayId AND railwayNodes.longitude<".getParaForSql("lonMax")." AND railwayNodes.latitude<".getParaForSql("latMax")." and railwayNodes.longitude>".getParaForSql("lonMin")." AND railwayNodes.latitude>".getParaForSql("latMin")." AND (type LIKE \"station\" or type LIKE \"tram\") ORDER BY railwayId, posCode");

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
  $wayId=$row->railwayId;
  $xmlWriter->startElement("way");
  $xmlWriter->writeAttribute("id", $row->railwayId);
  $xmlWriter->writeAttribute("type", $row->type);
  do{
	  $xmlWriter->startElement("node");
	  $xmlWriter->writeAttribute("lon", $row->longitude*$factor);
	  $xmlWriter->writeAttribute("lat", $row->latitude*$factor);
	  $xmlWriter->endElement();
  	$row = mysql_fetch_object($wn);
  } while($row != null && $wayId == $row->railwayId);
  $xmlWriter->endElement();
}
$xmlWriter->endElement();
$xmlWriter->endDocument();
$xmlWriter->flush();
?>
