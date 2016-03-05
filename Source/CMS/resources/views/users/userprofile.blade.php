@extends('users.layout')
@section('content')
    <div id="main_column" class="clear">
        <div>
                
<div class="clear mainbox-title-container">
        <h1 class="mainbox-title float-left">
        Mobile: <?php echo $profile['mobile'];?>    </h1>
</div>
<div class="mainbox-body">
    <div class="cm-tabs-content">
	    <form class="cm-form-highlight">
            <h2 class="subheader">
                User information
            </h2>
			<?php
                echo '<table class="info_detail_view">';
				echo '<tr><td class="title">ID&nbsp;&nbsp;&nbsp;:</td><td class="value">'.$profile['id'].'</td></tr>';
				echo '<tr><td class="title">Full Name&nbsp;&nbsp;&nbsp;:</td><td class="value">'.$profile['fullname'].'</td></tr>';
				echo '<tr><td class="title">Name&nbsp;&nbsp;&nbsp;:</td><td class="value">'.$profile['name'].'</td></tr>';
				echo '<tr><td class="title">Thumbnail&nbsp;&nbsp;&nbsp;:</td><td class="value">'.$profile['thumbnail'].'</td></tr>';
				echo '<tr><td class="title">Email&nbsp;&nbsp;&nbsp;:</td><td class="value">'.$profile['email'].'</td></tr>';				
				echo '<tr><td class="title">Phone&nbsp;&nbsp;&nbsp;:</td><td class="value">'. $profile['phone'] .'</td></tr>';
				echo '<tr><td class="title">Birthday&nbsp;&nbsp;&nbsp;:</td><td class="value">'.$profile['birthday'].'</td></tr>';
				echo '<td class="title">Friend&nbsp;&nbsp;&nbsp;:</td><td class="value">'. $profile['friendnum'].'</td></tr>';
				echo '<tr><td class="title">Address&nbsp;&nbsp;&nbsp;:</td><td class="value">'.$profile['address'].'</td></tr>';
				echo '<tr><td class="title">Support Number &nbsp;&nbsp;&nbsp;:</td><td class="value">'.$profile['supportnum'].'</td></tr>';
				echo '<tr><td class="title">Point Number &nbsp;&nbsp;&nbsp;:</td><td class="value">'.$profile['pointnum'].'</td></tr>';
				echo '<tr><td class="title">Modified Date&nbsp;&nbsp;&nbsp;:</td><td class="value">'.$profile['modifydate'].'</td></tr></table>';
			?>
			
                    
            <div class="buttons-container buttons-bg cm-toggle-button">
                <span class="cm-button-main cm-process-items">
                    <input type="button" onclick="location.href = '/admin/index'"  value="Back" />
                </span>
            </div>
            </form>
    </div>
</div>
        </div>
    </div>

@stop
