@extends('layout')
@section('content')
    <div id="main_column" class="clear">
        <div>
                <div id="content">
	
<div class="clear mainbox-title-container">
    <h1 class="mainbox-title float-left">
        Dashboard
    </h1>
</div>

<div class="statistics-box users">
    <h2>
        <span class="float-right hidden">
            <img src="models/css/images/icons/icon_hide.gif" width="13" height="13" border="0" alt="Hide" title="Hide" />
            <img src="models/css/images/icons/icon_close.gif" width="13" height="13" border="0" alt="Close" title="Close" />
        </span>
        Users
    </h2>	
    <div class="statistics-body clear">
        <ul>
            <li class="customer-users">
                <span>Administrator:</span>
                <em>1</em>
            </li>

            <li class="staff-users">
                <span>Users:</span>
                <em>22</em>
            </li>

            <li class="total-users">
                <span>Total:</span>
                <em>23</em>
            </li>

            <li class="disabled-users">
                <span>Disabled:</span>
                <em>1</em>
            </li>
        </ul>
    </div>
</div></div><!-- content -->
        </div>
    </div>

</body>
</html>
@stop