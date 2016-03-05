@extends('layout.contactdefault')
@section('content')

<section id="contact-info">

	<div class="center">                
		<!--<h2>Get in Touch</h2>-->
		<?php // echo $_SERVER['HTTP_REFERER']; ?>
	</div>

	<script>
	//$("#psch11111").attr('src', '/images/105101.jpg');
	//document.getElementById("background_img").src="/images/107202480.jpg";
	</script>
	
	
	<div class="container" >
		<div id="div2" style="font-size: 14px;font-weight: bold;">
			@if(!empty($result))
				@if($result)  
					<div style="color:green;">Successfully sent Message...</div>
				@else  
					<div style="color:red;">Message could not be sent...</div>
				@endif
			@endif
		</div>
		<div class="row contact-wrap"> 
			<div class="status alert alert-success" style="display: none"></div>
			   <!-- <form id="main-contact-form" class="contact-form" name="contact-form" method="post" action="sendemail.php">-->
			<form class="contact-form" action= "/contact_message" name="myForm" method="post" id = "send_message_form">
				<div class="col-md-4 ">
					<div class="form-group ">
						<label class="col-md-2">Name</label>
						<input type="text" name="name" class="form-control" id="userName">
					</div>
				
				  
					 <div class="form-group">
						<label class="col-md-2">Email </label>
						<input type="email" name="email" class="form-control" id="userEmail">
					</div>
					  <div class="form-group">
						<label class="col-md-2">Phone</label>
						<input type="text" class="form-control" name="phoneno" id="phoneno" placeholder = '000-000-0000' >
					  </div>
					<div class="form-group">
						<label class="col-md-2"> Message</label>
						<textarea name="userMsg" id="userMsg" class="form-control" rows="3"></textarea>
					</div>  
					<div class="form-group center">
						<input type="submit" name="submit" class="btn btn-primary btn-lg" value="Submit">
					</div>                      
				</div>
			
			</form> 
			<div class="col-sm-5">
			<div class="gmap"> 
			<!--<iframe src="https://www.google.com/maps/embed?pb=!1m16!1m12!1m3!1d26533.114736033578!2d-117.86254350000002!3d33.7699845!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!2m1!1sRV%E2%80%99s+clinic.++800+North+state+college+blvd++Fullerton%2C+CA+92831.!5e0!3m2!1sen!2sin!4v1429721931154" width="600" height="450" frameborder="0" style="border:0"></iframe>-->
			</div>
			</div>
			<div class="col-md-3 map-content">
			<address class="contact">
								<h5> <b>RV Hospital.</b></h5>
								<p>800 North state college blvd <br>
							   Fullerton, CA 92831.<br>
								Phone:  657-278-2011<br>
									  </p>                                
						   
							</address>
			</div>
		</div>
	</div>
     
</section>

	
@stop