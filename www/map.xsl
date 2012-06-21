<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
	<svg id="svgelem" width="3000" height="3000" xmlns="http://www.w3.org/2000/svg">
		<g id="svgtransform">
			<xsl:attribute name="transform">
				<xsl:text>scale(400,-608) translate(-</xsl:text>
				<xsl:value-of select="/ways/bounds/@minlon"/>
				<xsl:text>,-</xsl:text>
				<xsl:value-of select="/ways/bounds/@maxlat"/>
				<xsl:text>)</xsl:text>
			</xsl:attribute>
			<xsl:for-each select="ways/way">
				<polyline style="fill:none;stroke:black;stroke-width:0.00800">
					<xsl:attribute name="points">
						<xsl:for-each select="node">
							<xsl:value-of select="@lon"/>
							<xsl:text>,</xsl:text>
							<xsl:value-of select="@lat"/>
							<xsl:text> </xsl:text>
						</xsl:for-each>
					</xsl:attribute>
					<xsl:attribute name="id"><xsl:text>outerway</xsl:text><xsl:value-of select="@id"/></xsl:attribute>
				</polyline>
				<polyline style="fill:none;stroke:white;stroke-width:0.00600">
					<xsl:attribute name="points">
						<xsl:for-each select="node">
							<xsl:value-of select="@lon"/>
							<xsl:text>,</xsl:text>
							<xsl:value-of select="@lat"/>
							<xsl:text> </xsl:text>
						</xsl:for-each>
					</xsl:attribute>
					<xsl:attribute name="id"><xsl:text>innerway</xsl:text><xsl:value-of select="@id"/></xsl:attribute>
				</polyline>
			</xsl:for-each>
		</g>
	</svg>
	<input type="hidden" id="initialtranslation">
		<xsl:attribute name="value">
			<xsl:text>{"translation": {"x": -</xsl:text><xsl:value-of select="/ways/bounds/@minlon"/><xsl:text>,"y": -</xsl:text><xsl:value-of select="/ways/bounds/@maxlat"/><xsl:text>},"bounds": {"minlat": </xsl:text><xsl:value-of select="/ways/bounds/@minlat"/><xsl:text>,"minlon": </xsl:text><xsl:value-of select="/ways/bounds/@minlon"/><xsl:text>,"maxlat": </xsl:text><xsl:value-of select="/ways/bounds/@maxlat"/><xsl:text>,"maxlon": </xsl:text><xsl:value-of select="/ways/bounds/@maxlon"/><xsl:text>},"wayNames":{</xsl:text><xsl:for-each select="/ways/way"><xsl:text>"way</xsl:text><xsl:value-of select="@id"/><xsl:text>": "</xsl:text><xsl:value-of select="@name"/><xsl:text>",</xsl:text></xsl:for-each><xsl:text>"end":true},"wayTypes":{</xsl:text><xsl:for-each select="/ways/way"><xsl:text>"way</xsl:text><xsl:value-of select="@id"/><xsl:text>": "</xsl:text><xsl:value-of select="@type"/><xsl:text>",</xsl:text></xsl:for-each><xsl:text>"end":true}}</xsl:text>
		</xsl:attribute>
	</input>
</xsl:template>

</xsl:stylesheet> 