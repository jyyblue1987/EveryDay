@extends('layout')
@section('content')

<div id="main_column" class="clear">
   <div>                
		<div class="clear mainbox-title-container">
				<h1 class="mainbox-title float-left">
					Users 
				</h1>
		</div>
		<div id="search-form" class="section-border">
			<form name="user_search_form" action="/index.php/search" method="GET" class="">
				<table cellpadding="0" cellspacing="0" border="0" class="search-header">
					<tr>
						<td style="display:none">
							<input type="hidden" name="r" value="user/admin">
						</td>
						<td class="search-field nowrap">
							<label for="elm_name"></label>
							<div class="break">
								<input class="input-text" type="text" size="15" name="name" id="name" value=""/>
							</div>
						</td>
						<td class="buttons-container">
							<span  class="submit-button ">
								<input type="submit" name="mode" value="Search" />
							</span>
						</td>
					</tr>
				</table>
			</form>
		</div>

@stop
