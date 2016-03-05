@extends('users.layout')
@section('content')

<div id="main_column" class="clear">
	<div class="clear mainbox-title-container">
		<h1 class="mainbox-title float-left">
			Users 
		</h1>
		
	</div>
	<div id="search-form" class="section-border">
		<form name="user_search_form" action="/admin/search" method="GET" class="">
			<table cellpadding="0" cellspacing="0" border="0" class="search-header">
				<tr>
					<td style="display:none">
						<input type="hidden" name="r" value="user/admin">
					</td>
					<td class="search-field nowrap">
						<label for="elm_name">Option:</label>
						<div class="break">
							<select  name="select_option" id="select_option">
								<option value="2" selected = "selected">Email</option>
								<option value="1" >Name</option>
								<option value="0" >Phone</option>			
										
							</select>
						</div>
					</td>
					<td class="search-field nowrap">
						<label for="elm_mobile">Search:</label>
						<div class="break">
							<input class="input-text" type="text" size="30" name="find" id="elm_mobile" value=""/>
						</div>
					</td>
					<td class="search-field nowrap">
						<label for="elm_email">Order by:</label>
						<div class="break">
							<select onchange="this.form.submit()" name="orderby_option" id="select_option">
								<option  value="" selected = "selected">Alphabet</option>
								<option value="1" > Supported</option>
								<option value="0">Point</option>			
										
							</select>
						</div>
					</td>			
					<td class="buttons-container">
						<span  class="submit-button ">
							<input type="submit" name="mode" value="Search" style="display:none;"/>
						</span>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<div class="mainbox-body" >
		<div id="content_manage_users">
			<form action="/admin/index" method="GET">
				<div id="data_grid_view" class="grid-view">
					<div class="summary">
						Total Items: {{ $users->count() }}
					</div>
					<table class="items">
						<thead>
							<tr>
								
								<th id="data_grid_view_c1"><a class="sort-link" href="/users">No</a></th>
								<th id="data_grid_view_c1"><a class="sort-link" href="/index.php?r=user/admin&amp;User_sort=name">Full Name</a></th>
								<th id="data_grid_view_c1"><a class="sort-link" href="/index.php?r=user/admin&amp;User_sort=name">Name</a></th>
								<th id="data_grid_view_c3"><a class="sort-link" href="/index.php?r=user/admin&amp;User_sort=email">Email</a></th>
								<th id="data_grid_view_c2"><a class="sort-link" href="/index.php?r=user/admin&amp;User_sort=mobile">Supports </a></th>
								<th id="data_grid_view_c2"><a class="sort-link" href="/index.php?r=user/admin&amp;User_sort=mobile">Points </a></th>
								<th id="data_grid_view_c2"><a class="sort-link" href="/index.php?r=user/admin&amp;User_sort=mobile">Comments </a></th>
								<th id="data_grid_view_c2"><a class="sort-link" href="/index.php?r=user/admin&amp;User_sort=mobile">Friends </a></th>
								<th id="data_grid_view_c3"><a class="sort-link" href="/index.php?r=user/admin&amp;User_sort=address">Modified date</a></th>
								<!--<th id="data_grid_view_c7"><a class="sort-link" href="/index.php?r=user/admin&amp;User_sort=status">Status</a></th>-->
								
								
								
							</tr>
						</thead>	
						<tbody>
							<?php
								$i = 1;
								foreach( $users as $value )	
								{
									
									echo '<tr class="odd">';
									
									echo '<td>'.$value['id'].'</td>';
									echo '<td width="10%"><a class="view" href="/admin/userprofile?pname='.$value['id'].'">'.$value['fullname'].'</td>
										<td><a class="view" href="/admin/userprofile?pname='.$value['id'].'">'.$value['name'].'</a></td>';
									
									echo '<td>'.$value['email'].'</td>';
									echo '<td>'.$value['supportnum'].'</td>';									
									echo '<td>'.$value['pointnum'].'</td>';
									echo '<td>'.$value['commentnum'].'</td>';
									echo '<td>'.$value['friendnum'].'</td>';
									echo '<td>'.$value['modifydate'].'</td>';
									echo '<td width="8%"><a class="view" title="View user profile" href="/admin/userprofile?pname='.$value['id'].'"><img src="/images/customers.png" alt="View user profile" /></a></td></tr>';
									//echo '<td width="8%"><input src="/index.php/userprofile'"><img src="/images/customers.png" alt="View user profile" /></td></tr>';
									$i++;
								}

							?>

						</tbody>
					</table>
					
					<div class="pager"><ul id="yw0" class="yiiPager">
						<?php echo $users->appends(Request::except('page'))->render(); ?>						
					</div>
				</div>
				
				
			</form>
		</div>
	</div>
</div>

@stop
