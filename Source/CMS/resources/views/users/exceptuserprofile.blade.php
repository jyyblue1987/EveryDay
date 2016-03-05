@extends('layout')
@section('content')
    <div id="main_column" class="clear">
        <div>
                
<div class="clear mainbox-title-container">
        <h1 class="mainbox-title float-left">
        Mobile : <?php echo $profile['mobile'];?> </h1>
</div>
<div class="mainbox-body">
    <div class="cm-tabs-content">
	    <form class="cm-form-highlight">
            <h2 class="subheader">
                User information
            </h2>
			<?php
                echo '<table class="info_detail_view">';
				echo '<tr><td class="title">Name&nbsp;&nbsp;&nbsp;:</td><td class="value">'.''.'</td></tr>';
				echo '<tr><td class="title">Email&nbsp;&nbsp;&nbsp;:</td><td class="value">'.''.'</td></tr>';				
				echo '<tr><td class="title">Job&nbsp;&nbsp;&nbsp;:</td><td class="value">'. '' .'</td></tr><tr>';
				echo '<td class="title">Company&nbsp;&nbsp;&nbsp;:</td><td class="value">'. ''.'</td></tr>';
				echo '<tr><td class="title">Address&nbsp;&nbsp;&nbsp;:</td><td class="value">'.''.'</td></tr>';
				echo '<tr><td class="title">Postal code&nbsp;&nbsp;&nbsp;:</td><td class="value">'.''.'</td></tr></table>';
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
