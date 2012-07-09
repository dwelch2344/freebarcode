<?xml version="1.0" encoding="UTF-8" ?>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<tags:template>
	<jsp:attribute name="head">  
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
		<script type="text/javascript">
			$(function(){
				$('form').submit(function(){
					var barcode = $('#barcode').val();
					var url = window.location.href + "UPCA/" + barcode;
					var imgDiv = $('#imageDiv').empty();

					$.get(url)
						.done(function(data, textStatus, jqXHR){
							$('<img>').attr('src', url).appendTo(imgDiv);	
						})
						.fail(function(){
							$('<span>').text("Invalid Barcode: " + barcode).appendTo(imgDiv);
						});
					
					return false;
				});
			});
			
		</script>
  	</jsp:attribute>  
	<jsp:body>
		<h1>Barcode Generator</h1>
		<p>
			This app generates barcode images. Supply a <a href="http://en.wikipedia.org/wiki/UPC-A" target="_blank" >UPC-A</a> barcode below to try it out! (More barcode support coming soon)
		</p>
		<form action="" method="POST">
			<input name="barcode" id="barcode"/> <button>View Barcode</button>
		</form>
		<br/>
		<div id="imageDiv"></div>
		
	</jsp:body>
</tags:template>