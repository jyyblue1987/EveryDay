@extends('users.layout')
@section('content')

<div id="main_column" class="clear">
	<div class="clear mainbox-title-container">
			<h1 class="mainbox-title float-left">
				Advertisement 
			</h1>
			<div class="tools-container">
				<span class="action-add">
					<a href="/ad_register">Add Advertisement</a>
				</span>
			</div>
  			
	</div>

	<div class="mainbox-body" >
		<div id="content_manage_users">
			<form action="/ad_totaldelete" method="post">
				<div id="data_grid_view" class="grid-view">
					<div class="summary">
						Total Items: {{ $users->count() }}
					</div>
					<table class="items">
						<thead>
							<tr>
								<th width="1%" class="center cm-no-hide-input">
									<input type="checkbox" name="check_all" value="Y" title="Check / uncheck all" class="checkbox cm-check-items" />
								</th>
								<th id="data_grid_view_c1"><a class="sort-link" href="">ID</a></th>
								<th id="data_grid_view_c1"><a class="sort-link" href="">Title</a></th>
								<th id="data_grid_view_c2"><a class="sort-link" href="">Image</a></th>
								<th id="data_grid_view_c3"><a class="sort-link" href="">Start Date</a></th>
								<th id="data_grid_view_c7"><a class="sort-link" href="">End Date</a></th>
								<th id="data_grid_view_c8"><a class="sort-link" href="">Last Modified</a></th>
							</tr>
						</thead>	
						<tbody>
							<?php
								$i = 1;
								foreach( $users as $value )	
								{ ?>
                                    
 									<tr class="odd">
									<td class="center cm-no-hide-input"><input type="checkbox" name="cat_ids[]" value="{{$value['id']}}" class="checkbox cm-item" /></td>
									<td>{{$value['id']}}</td>
									<td width="20%"><a class="view" href="">{{$value['title']}}</td>
									<td><a class="view" href="/uploads/{{$value['thumbpath']}}">{{$value['thumbpath']}}</a></td>
											<?php	
												try{
													$start = date('Y-m-d', $value['start']);
													$end = date('Y-m-d', $value['end']);
													}
												catch(Exception $e){}
											?>
									
									<td>{{$start}}</td>
									<td>{{$end}}</td>									
									<td>{{$value['published']}}</td>																		
									<td class="nowrap">
										<a class="tool-link " href="/ad_edit/{{$value['id']}}?page={{$users->currentPage()}}" >Edit</a>
										&nbsp;&nbsp;
										<ul class="cm-tools-list tools-list">
											<li><a class="cm-confirm" href="/ad_delete/{{$value['id']}}?page={{$users->currentPage()}}">Delete</a></li>
										</ul>
									</td>

									
							<?php 
								$i++;} 
							?>

						</tbody>
					</table>
	
					<div class="pager"><ul id="yw0" class="yiiPager">
						<?php echo $users->appends(Request::except('page'))->render(); ?>						
					</div>
				
				</div>

			

				
				<div class="buttons-container buttons-bg">
					<div class="float-left">

						
							<span  class="submit-button cm-button-main cm-confirm cm-process-items">
								<input  class="cm-confirm cm-process-items" type="submit" name="dispatch[profiles.m_delete]" value="Delete selected" />
							</span>


					</div>
				</div>				
				
			</form>
		</div>
	</div>
</div>


@stop
