@extends('users.layout')
@section('content')

<?php

use App\Modules\Functions;

if( $error === 'SUCCESS' )
    Functions::alertSuccessMessage('');
else if( $error !== '' )
    Functions::alertErrorMessage ($error);

echo $error;
?>

    <div id="main_column" class="clear">
        <div>
                
<div class="clear mainbox-title-container">
        <h1 class="mainbox-title float-left">
        Edit Profile : admin    </h1>
</div>

<div class="mainbox-body">
    <div class="cm-tabs-content">
        <form id="profile_form_id" name="profile_form" action="" method="post" class="cm-form-highlight">
            <div id="content_general">
                <fieldset>
                   <h2 class="subheader">
                        Admin account information
                    </h2>
 
                    <div class="form-field">
                        <label for="username" class="cm-required">Admin Name:</label>
                        <input type="text" id="username" name="username" class="input-text" size="32" maxlength="50" value={{$admin['name']}} />
                    </div>

                    <div class="form-field">
                        <label for="email" class="cm-required cm-email">Email:</label>
                        <input type="text" id="email" name="email" class="input-text" size="32" maxlength="100" value={{$admin['email']}} />
                    </div>

                    <div class="form-field">
                        <label for="password1" class="cm-required">Password:</label>
                        <input type="password" id="password1" name="password1" class="input-text cm-autocomplete-off" size="32" maxlength="32" value="" />
                    </div>

                    <div class="form-field">
                        <label for="password2" class="cm-required">Confirm password:</label>
                        <input type="password" id="password2" name="password2" class="input-text cm-autocomplete-off" size="32" maxlength="32" value="" />
                    </div>

                    <div class="form-field">
                        <label>Status:</label>
                        <div class="select-field">
                            <input type="radio" name="Admin[status]" id="user_data_0_a"
                                checked="checked"                                   value="1" class="radio" /><label for="user_data_0_a">Active</label>
                            <input type="radio" name="Admin[status]" id="user_data_0_d"
                                                                      value="0" class="radio" /><label for="user_data_0_d">Inactive</label>
                        </div>
                    </div>
                </fieldset>

            </div>

            <div class="buttons-container buttons-bg cm-toggle-button">
                <span  class="submit-button cm-button-main ">
                    <input type="submit" name="dispatch[profiles.update]" value="Save" />
                </span>
                &nbsp;&nbsp;&nbsp;
                <span class="cm-button-main cm-process-items">
                    <input type="button" onclick="location.href = '/'"  value="Cancel" />
                </span>
            </div>
        </form>
    </div>
</div>

@stop	

</body>

