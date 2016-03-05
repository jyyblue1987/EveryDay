@extends('users.layout')
@section('content')

<?php

use App\Modules\Functions;

if( $error === 'SUCCESS' )
    Functions::alertSuccessMessage('');
else if( $error !== '' )
    Functions::alertErrorMessage ($error);

?>


<div id="main_column" class="clear">
	<div class="clear mainbox-title-container">
			<h1 class="mainbox-title float-left">
				New User 
			</h1>
  			
	</div>

	<div class="mainbox-body" >

		<script type="text/javascript" src="/js/tabs.js"></script>

		<div class="cm-tabs-content">
			<?php 
				if($mode === 'create')
					$actionurl = '/admin/user_item';
				else
					$actionurl = '/admin/update_user_item/'.$adver->phone;
			?>
			<form name="profile_form" action={{$actionurl}} method="post" class="cm-form-highlight">
				<input type="hidden" name="region_data[mode]" value="" />
				<input type="hidden" name="region_data[thumb_url]" id="thumb_url" value="{{$adver->thumbpath}}" />

				<div id="content_general">
					<fieldset>
						<h2 class="subheader">
							User information
						</h2>

						<div class="form-field">
							<label for="regionname" class="cm-required">Phone Number:</label>
							<input type="text" id="regionname" name="region_data[phone]" class="input-text" size="30" maxlength="50" value="{{$adver->phone}}" />
						</div>


						<div class="form-field">
							<label class="cm-required">Name:</label>
							<div class="select-field">
								<input name="region_data[name]" id='datetimepicker' size='30' value="{{$adver->name}}"/> 
							</div>
						</div>
						
						<div class="form-field">
							<label class="cm-required">Email:</label>
							<div class="select-field">
								<input name="region_data[email]" id='datetimepicker1' size='30' value="{{$adver->email}}"/> 
							</div>
						</div>
						<div class="form-field">
							<label for="regionname" class="cm-required">Address:</label>
							<input type="text" id="sequence" name="region_data[adress]" class="input-text" size="30" maxlength="50" value="{{$adver->adress}}" />
						</div>
						<!--<div class="form-field " id="div_thumb">
							<label class="cm-required ">Upload thumbnail:</label>
							<table border="0">
								<tr>
									<td width="400px">
										<div id="mulitplethumbploader">Upload</div>
									</td>
									<td valign="middle">
										<div><font color="blue">( Max size: 100MB (1000 * 400 pixels) , *.jpg, *.png)</font></div>
									</td>
								</tr>
								<tr>
									<td width="400px">
										<div id="status1">{{$adver->thumbpath}}</div>
									</td>
									<td></td>
								</tr>
							</table>
						</div>-->


					</fieldset>

				</div>

				<?php 
					if($mode === 'create')
						$label = 'Create';
					else
						$label = 'Save';
				?>
				<div class="buttons-container buttons-bg cm-toggle-button">
					<span  class="submit-button cm-button-main ">
						<input   type="submit" name="dispatch[profiles.update]" value="{{$label}}" />
					</span>
					&nbsp;&nbsp;&nbsp;
					<span class="cm-button-main cm-process-items">
						<input type="button" onclick="location.href='/admin/index';"  value="Cancel" />
					</span>
				</div>


			</form>
		</div>
	</div>
</div>

<script>
	$(document).ready(function(){
		$("#status1").html("<div></div>");
		var settings = {
			url: "/models/file_upload.php",
			dragDrop: false,
			fileName: "myfile",
			multiple: false,
			showCancel: false,
			showAbort: false,
			showDone: false,
			showDelete:false,
			showError: true,
			showStatusAfterSuccess: false,
			showStatusAfterError: false,
			showFileCounter:false,
			allowedTypes: "jpg,png",
			maxFileSize: 112000000,
			returnType: "text",
			onSuccess: function(files, data, xhr)
			{
				$("#thumb_url").val(data);
				$("#status1").html("<div>" + data + "</div>");
			},
			 deleteCallback: function(data, pd)
			{
				for (var i = 0; i < data.length; i++)
				{
					$.post("file_delete.php", {op: "delete", name: data[i]},
					function(resp, textStatus, jqXHR)
					{
						//Show Message
						
						$("#status1").html("<div>File Deleted</div>");
					});
				}
				pd.statusbar.hide(); //You choice to hide/not.
			} 
		};
		$("#mulitplethumbploader").uploadFile(settings);
	});
</script>

@stop
