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
							<label for="elm_name">User Name:</label>
							<div class="break">
								<input class="input-text" type="text" size="15" name="name" id="name" value=""/>
							</div>
						</td>
						<td class="search-field nowrap">
							<label for="elm_mobile">Name:</label>
							<div class="break">
								<input class="input-text" type="text" size="15" name="User[mobile]" id="elm_mobile" value=""/>
							</div>
						</td>
						<td class="search-field nowrap">
							<label for="elm_email">Email:</label>
							<div class="break">
								<input class="input-text" type="text" size="15" name="User[email]" id="elm_email" value=""/>
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

		<div class="mainbox-body" >
			<div id="content_manage_users">
				<form action="/index.php?r=user/admin" method="post">
					<div id="data_grid_view" class="grid-view">
					<div class="summary">
						Displaying 1-10 of 22 results.
					</div>
					<table class="items">
						<thead>
							<tr>
								<th class="checkbox-column" id="data_grid_view_c0"><input type="checkbox" value="1" name="data_grid_view_c0_all" id="data_grid_view_c0_all" /></th>
								<th id="data_grid_view_c1"><a class="sort-link" href="/index.php?r=user/admin&amp;User_sort=name">User Name</a></th>
								<th id="data_grid_view_c2"><a class="sort-link" href="/index.php?r=user/admin&amp;User_sort=mobile">Name</a></th>
								<th id="data_grid_view_c3"><a class="sort-link" href="/index.php?r=user/admin&amp;User_sort=email">Email</a></th>
								<th id="data_grid_view_c7"><a class="sort-link" href="/index.php?r=user/admin&amp;User_sort=status">Status</a></th>
								<th class="button-column" id="data_grid_view_c8">
									<select onchange="$.fn.yiiGridView.update(&#039;data_grid_view&#039;,{ data:{pageSize: $(this).val() }})" name="pageSize" id="pageSize">
										<option value="5">5</option>
										<option value="10" selected="selected">10</option>
										<option value="20">20</option>
										<option value="50">50</option>
										<option value="100">100</option>
									</select>
								</th>
							</tr>
						</thead>	
						<tbody>
							<?php
								echo '<tr class="odd">';
								echo '<td class="checkbox-column"><input value="1" id="data_grid_view_c0_0" type="checkbox" name="selected_ids[]" /></td>
									<td width="10%">'.$user['username'].'</td>
									<td><a class="view" href="/index.php?r=contact/admin&amp;id=1">'.$user['name'].'</a></td>';
									
								if (array_key_exists('email', $user)) {
									echo '<td>'.$user['email'].'</td>';
								}
								else 
								{
									echo '<td></td>';
								}
								
								echo '<td>'.$user['state'].'</td>';
								echo '<td width="8%"><a class="view" title="View user profile" href="/index.php?r=user/view&amp;id=1"><img src="/images/customers.png" alt="View user profile" /></a>&nbsp;<a class="view" title="View user contacts" href="/index.php?r=contact/admin&amp;id=1"><img src="/images/usergroups.png" alt="View user contacts" /></a>&nbsp;<a class="update" title="Update" href="/index.php?r=user/update&amp;id=1"><img src="/assets/7b8be64/gridview/update.png" alt="Update" /></a>&nbsp;<a class="delete" title="Delete" href="/index.php?r=user/delete&amp;id=1"><img src="/assets/7b8be64/gridview/delete.png" alt="Delete" /></a></td></tr>';
							?>

						</tbody>
					</table>
					<div class='table-tools'>
						<a id='select_all_rows' href='#' name='check_all' class='cm-check-items cm-on underlined'>Select all</a>
						<a id='unselect_all_rows' href='#' name='check_all' class='cm-check-items cm-off underlined'>Unselect all</a>
					 </div>
					<div class="pager">Go to page: <ul id="yw0" class="yiiPager"><li class="first hidden"><a href="/index.php?r=user/admin">&lt;&lt; First</a></li>
						<li class="previous hidden"><a href="/index.php?r=user/admin">&lt; Previous</a></li>
						<li class="page selected"><a href="/index.php?r=user/admin">1</a></li>
						<li class="page"><a href="/index.php?r=user/admin&amp;User_page=2">2</a></li>
						<li class="page"><a href="/index.php?r=user/admin&amp;User_page=3">3</a></li>
						<li class="next"><a href="/index.php?r=user/admin&amp;User_page=2">Next &gt;</a></li>
						<li class="last"><a href="/index.php?r=user/admin&amp;User_page=3">Last &gt;&gt;</a></li></ul></div><div class="keys" style="display:none" title="/index.php?r=user/admin"><span>1</span><span>2</span><span>3</span><span>4</span><span>5</span><span>6</span><span>7</span><span>8</span><span>9</span><span>10</span></div>
					</div>
					<div class="buttons-container buttons-bg">
						<div id="float-tool-bar" class="float-left">
							<span class="submit-button cm-button-main cm-confirm cm-process-items">
								<input id="btDeleteSelected" class="cm-confirm cm-process-items" type="submit" name="bt_delete_selected" value="Delete selected" />
							</span>
							&nbsp;&nbsp;&nbsp;
						</div>
					</div>

				</form>
			</div>
		</div>
@stop


