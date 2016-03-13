<%-- 
    Document   : manage-library
    Created on : Feb 20, 2016, 11:42:19 PM
    Author     : AnhND
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <meta charset="utf-8" />
        <title>Communication by Your Hands</title>

        <meta name="description" content="overview &amp; stats" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

        <!-- bootstrap & fontawesome -->
        <link rel="stylesheet" href="assets/css/bootstrap.min.css" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" />

        <!-- page specific plugin styles -->
        <!-- text fonts -->
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />

        <!-- ace styles -->
        <link rel="stylesheet" href="assets/css/ace.min.css" id="main-ace-style" />

        <!--[if lte IE 11]>
            <link rel="stylesheet" href="assets/css/ace-part2.min.css" />
        <![endif]-->
        <link rel="stylesheet" href="assets/css/ace-skins.min.css" />
        <link rel="stylesheet" href="assets/css/ace-rtl.min.css" />

        <!--[if lte IE 11]>
          <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
        <![endif]-->
        <!-- inline styles related to this page -->
        <!-- ace settings handler -->
        <script src="assets/js/ace-extra.min.js"></script>

        <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->
        <!--[if lte IE 11]>
        <script src="assets/js/html5shiv.min.js"></script>
        <script src="assets/js/respond.min.js"></script>
        <![endif]-->
    </head>
    <body class="no-skin">
        <div id="navbar" class="navbar navbar-default navbar-fixed-top">
            <script type="text/javascript">
                try {
                    ace.settings.check('navbar', 'fixed');
                } catch (e) {
                }
            </script>

            <div class="navbar-container" id="navbar-container">
                <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler">
                    <span class="sr-only">Toggle sidebar</span>

                    <span class="icon-bar"></span>

                    <span class="icon-bar"></span>

                    <span class="icon-bar"></span>
                </button>

                <div class="navbar-header pull-left">
                    <a href="#" class="navbar-brand">
                        <small>
                            <i class="fa fa-hand-peace-o"></i>
                            Communication by Your Hands
                        </small>
                    </a>
                </div>

                <div class="navbar-buttons navbar-header pull-right" role="navigation">
                    <c:set  var="notifications" scope="session" value="${sessionScope.NOTIFICATION}" />
                    <ul class="nav ace-nav">                     
                        <li class="purple">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <i class="ace-icon fa fa-bell icon-animated-bell"></i>
                                <span class="badge badge-important">${fn:length(notifications)}</span>
                            </a>

                            <ul class="dropdown-menu-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close">
                                <li class="dropdown-header">
                                    <i class="ace-icon fa fa-exclamation-triangle"></i>
                                    ${fn:length(notifications)} Notifications
                                </li>

                                <c:forEach items="${notifications}" var="item">
                                    <c:url value="NotificationServlet" var="url">
                                        <c:param name="redirect_url" value="${requestScope['javax.servlet.forward.servlet_path']}"/>
                                    </c:url>

                                    <li>
                                        <a href="${url}">
                                            <div class="clearfix">
                                                <span class="pull-left">
                                                    <i class="btn btn-xs no-hover btn-pink fa fa-book"></i>
                                                    ${item.notiContent}
                                                </span>
                                                <!--<span class="pull-right badge badge-info">1</span>-->
                                            </div>
                                        </a>
                                    </li>
                                </c:forEach>

                            </ul>
                        </li>

                        <li class="light-blue">
                            <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                                <img class="nav-user-photo" src="assets/avatars/avatar2.png" alt=""/>
                                <span class="user-info">
                                    <small>Welcome,</small>
                                    ${sessionScope.USER}
                                </span>

                                <i class="ace-icon fa fa-caret-down"></i>
                            </a>

                            <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                                <li>
                                    <a href="#">
                                        <i class="ace-icon fa fa-cog"></i>
                                        Settings
                                    </a>
                                </li>

                                <li>
                                    <a href="#">
                                        <i class="ace-icon fa fa-user"></i>
                                        Profile
                                    </a>
                                </li>

                                <li class="divider"></li>

                                <li>
                                    <a href="<%=request.getContextPath()%>/LogoutServlet">
                                        <i class="ace-icon fa fa-power-off"></i>
                                        Logout
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div><!-- /.navbar-container -->
        </div>

        <div class="main-container" id="main-container">
            <script type="text/javascript">
                try {
                    ace.settings.check('main-container', 'fixed');
                } catch (e) {
                }
            </script>

            <div id="sidebar" class="sidebar                  responsive sidebar-scroll sidebar-fixed">
                <script type="text/javascript">
                    try {
                        ace.settings.check('sidebar', 'fixed');
                    } catch (e) {
                    }
                </script>


                <!--                <div class="nav-search" id="nav-search">
                                    <form class="form-search">
                                        <span class="input-icon">
                                            <input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
                                            <i class="ace-icon fa fa-search nav-search-icon"></i>
                                        </span>
                                    </form>
                                </div> /.nav-search -->

                <ul class="nav nav-list">


                    <li class="">
                        <a href="<%=request.getContextPath()%>/AccountServlet" >
                            <i class="menu-icon fa fa-users"></i>
                            <span class="menu-text"> Manage Users </span>
                        </a>
                    </li>
                    <li class="active open">
                        <a href="<%=request.getContextPath()%>/LibraryServlet">
                            <i class="menu-icon fa fa-list"></i>
                            <span class="menu-text"> Manage Library </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="<%=request.getContextPath()%>/DictionaryServlet">
                            <i class="menu-icon fa fa-globe"></i>
                            <span class="menu-text">
                                Manage Dictionary

                                <!--                            <span class="badge badge-transparent tooltip-error" title="2 Important Events">
                                                                <i class="ace-icon fa fa-exclamation-triangle red bigger-130"></i>
                                                            </span>-->
                            </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="<%=request.getContextPath()%>/LicenseServlet" >
                            <i class="menu-icon fa fa-key"></i>
                            <span class="menu-text"> Manage License </span>
                        </a>
                    </li>
                </ul><!-- /.nav-list -->

                <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
                    <i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
                </div>

                <script type="text/javascript">
                    try {
                        ace.settings.check('sidebar', 'collapsed');
                    } catch (e) {
                    }
                </script>
            </div>

            <div class="main-content">
                <!--                <div class="breadcrumbs breadcrumbs-fixed" id="breadcrumbs">
                                    <script type="text/javascript">
                                        try {
                                            ace.settings.check('breadcrumbs', 'fixed')
                                        } catch (e) {
                                        }
                                    </script>
                
                                    <ul class="breadcrumb">
                
                                        <li>
                                            <a href="manage-user.html">Manage Users Page</a>
                                        </li>
                
                                    </ul> /.breadcrumb 
                
                                    
                                </div>-->

                <div class="page-content">

                    <div class="page-content-area">                      
                        <div class="page-content-area">                      
                            <div class="page-header">
                                <h1>
                                    Libraries

                                </h1>
                            </div><!-- /.page-header -->

                            <div class="row">
                                <div class="col-xs-12">
                                    <!-- PAGE CONTENT BEGINS -->
                                    <div class="row">
                                        <div class="col-xs-12">


                                            <!-- <div class="clearfix">
                                                    <div class="pull-right tableTools-container">
                                                            <div class="dt-buttons btn-overlap btn-group">
                                                                    <a class="dt-button buttons-collection buttons-colvis btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table" data-original-title="" title="">
                                                                            <span>
                                                                                    <i class="fa fa-search bigger-110 blue"></i> 
                                                                                    <span class="hidden">Show/hide columns</span>
                                                                            </span>
                                                                    </a>
                                                                    <a class="dt-button buttons-copy buttons-html5 btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table" data-original-title="" title="">
                                                                            <span>
                                                                                    <i class="fa fa-copy bigger-110 pink"></i>
                                                                                    <span class="hidden">Copy to clipboard</span>
                                                                            </span>
                                                                    </a>
                                                                    <a class="dt-button buttons-csv buttons-html5 btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table" data-original-title="" title="">
                                                                            <span>
                                                                                    <i class="fa fa-database bigger-110 orange"></i>
                                                                                    <span class="hidden">Export to CSV</span>
                                                                            </span>
                                                                    </a>
                                                                    <a class="dt-button buttons-excel buttons-flash btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table">
                                                                            <span>
                                                                                    <i class="fa fa-file-excel-o bigger-110 green"></i>
                                                                                    <span class="hidden">Export to Excel</span>
                                                                            </span>
                                                                            <div style="position: absolute; left: 0px; top: 0px; width: 39px; height: 35px; z-index: 99;" data-original-title="" title="">
                                                                                    <embed id="ZeroClipboard_TableToolsMovie_1" src="dist/js/dataTables/extensions/Buttons/swf/flashExport.swf" loop="false" menu="false" quality="best" bgcolor="#ffffff" width="39" height="35" name="ZeroClipboard_TableToolsMovie_1" align="middle" allowscriptaccess="always" allowfullscreen="false" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" flashvars="id=1&amp;width=39&amp;height=35" wmode="transparent">
                                                                            </div>
                                                                    </a>
                                                                    <a class="dt-button buttons-pdf buttons-flash btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table">
                                                                            <span>
                                                                                    <i class="fa fa-file-pdf-o bigger-110 red"></i>
                                                                                    <span class="hidden">Export to PDF</span>
                                                                            </span>
                                                                            <div style="position: absolute; left: 0px; top: 0px; width: 39px; height: 35px; z-index: 99;" data-original-title="" title=""><embed id="ZeroClipboard_TableToolsMovie_2" src="dist/js/dataTables/extensions/Buttons/swf/flashExport.swf" loop="false" menu="false" quality="best" bgcolor="#ffffff" width="39" height="35" name="ZeroClipboard_TableToolsMovie_2" align="middle" allowscriptaccess="always" allowfullscreen="false" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" flashvars="id=2&amp;width=39&amp;height=35" wmode="transparent">
                                                                            </div>
                                                                    </a>
                                                                    <a class="dt-button buttons-print btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table" data-original-title="" title="">
                                                                            <span>
                                                                                    <i class="fa fa-print bigger-110 grey"></i>
                                                                                    <span class="hidden">Print</span>
                                                                            </span>
                                                                    </a>
                                                            </div>
                                                    </div>
                                            </div> -->

                                            <!-- div.table-responsive -->

                                            <!-- div.dataTables_borderWrap -->
                                            <div>
                                                <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                                                    <div class="row">
                                                        <div class="col-xs-6">
                                                            <div class="dataTables_length" id="dynamic-table_length">
                                                                <label>Display
                                                                    <select name="dynamic-table_length" aria-controls="dynamic-table" class="form-control input-sm">
                                                                        <option value="10">10</option>
                                                                        <option value="25">25</option>
                                                                        <option value="50">50</option>
                                                                        <option value="100">100</option>
                                                                    </select> records
                                                                </label>
                                                            </div>
                                                        </div>
                                                        <div class="col-xs-6">

                                                            <div id="dynamic-table_filter" class="dataTables_filter">
                                                                <a href="addLibrary.jsp?btAction=Add" class="dt-button buttons-collection buttons-colvis btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table" data-original-title="" title="">
                                                                    <span>
                                                                        <i class="fa fa-plus bigger-150 green"></i> 
                                                                        <span class="hidden">Add</span>
                                                                    </span>
                                                                </a>

                                                                <label> Search:
                                                                    <input type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                                </label>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <c:set var="info" value="${requestScope.LIBINFO}"/>
                                                    <c:if test="${not empty info}">
                                                        <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                                                            <thead>
                                                                <tr role="row">
                                                                    <th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                                                                        <!--                                                                <label class="pos-rel">
                                                                                                                                            <input type="checkbox" class="ace">
                                                                                                                                            <span class="lbl"></span>
                                                                                                                                        </label>-->
                                                                    </th>
                                                                    <th class="sorting" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">Library Name</th>
                                                                    <th class="sorting" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">Status</th>
                                                                    <th class="sorting_disabled" rowspan="1" colspan="1" aria-label=""></th>
                                                                </tr>
                                                            </thead>

                                                            <tbody>	
                                                                <c:forEach var="items" items="${info}" varStatus="counter">
                                                                    <tr role="row" class="odd">
                                                                        <td class="center">
                                                                            ${counter.count}
                                                                        </td>
                                                                        <td>
                                                                            ${items.libraryName}
                                                                        </td>
                                                                        <td>
                                                                            <label>
                                                                                <input name="switch-field-1" class="ace ace-switch ace-switch-6" type="checkbox" id="checkstatus" <c:if test="${items.status=='true'}">checked="checked"</c:if>/>
                                                                                    <span class="lbl"></span>
                                                                                </label>
                                                                            </td>

                                                                            <td>
                                                                                <div class="action-buttons">
                                                                                    <a class="blue" id="Btn" href="update-library.jsp?txtLibraryId=${items.libraryId}&txtLibraryname=${items.libraryName}&chbStatus=${items.status}&btAction=Update">
                                                                                    <i class="ace-icon fa fa-pencil bigger-130"></i>
                                                                                </a>
                                                                                <c:url var="DelLib" value="LibraryProcessServlet">
                                                                                    <c:param name="btAction" value="DeleteLibrary"/>
                                                                                    <c:param name="txtLibraryId" value="${items.libraryId}"/>
                                                                                </c:url>
                                                                                <a class="red" href="${DelLib}">
                                                                                    <i class="ace-icon fa fa-trash-o bigger-130"></i>
                                                                                </a>
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                </c:forEach>
                                                            </tbody>
                                                        </table>
                                                    </c:if>
                                                    <c:if test="${empty info}">
                                                        <h2>Empty list</h2>
                                                    </c:if>
                                                    <div class="row"><div class="col-xs-6"><div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite"></div></div><div class="col-xs-6"><div class="dataTables_paginate paging_simple_numbers" id="dynamic-table_paginate"><ul class="pagination"><li class="paginate_button previous disabled" aria-controls="dynamic-table" tabindex="0" id="dynamic-table_previous"><a href="#">Previous</a></li><li class="paginate_button active" aria-controls="dynamic-table" tabindex="0"><a href="#">1</a></li><li class="paginate_button " aria-controls="dynamic-table" tabindex="0"><a href="#">2</a></li><li class="paginate_button " aria-controls="dynamic-table" tabindex="0"><a href="#">3</a></li><li class="paginate_button next" aria-controls="dynamic-table" tabindex="0" id="dynamic-table_next"><a href="#">Next</a></li></ul></div></div></div></div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- PAGE CONTENT ENDS -->
                                </div><!-- /.col -->
                            </div><!-- /.row -->
                        </div><!-- /.page-content-area -->
                    </div><!-- /.page-content-area -->
                </div><!-- /.page-content -->
            </div><!-- /.main-content -->

            <div class="footer">
                <div class="footer-inner">
                    <div class="footer-content">
                        <span class="bigger-120">
                            <span class="blue bolder">CBYH</span>
                            Application &copy; 2015-2016
                        </span>

                        &nbsp; &nbsp;
                        <span class="action-buttons">
                            <a href="#">
                                <i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
                            </a>

                            <a href="#">
                                <i class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
                            </a>

                            <a href="#">
                                <i class="ace-icon fa fa-rss-square orange bigger-150"></i>
                            </a>
                        </span>
                    </div>
                </div>
            </div>

            <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
                <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
            </a>
        </div><!-- /.main-container -->
        <!-- basic scripts -->
        <!--[if !IE]> -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

        <!-- <![endif]-->
        <!--[if IE]>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <![endif]-->
        <!--[if !IE]> -->
        <script type="text/javascript">
                    window.jQuery || document.write("<script src='assets/js/jquery.min.js'>" + "<" + "/script>");
        </script>

        <!-- <![endif]-->
        <!--[if IE]>
        <script type="text/javascript">
         window.jQuery || document.write("<script src='assets/js/jquery1x.min.js'>"+"<"+"/script>");
        </script>
        <![endif]-->
        <script type="text/javascript">
            if ('ontouchstart' in document.documentElement)
                document.write("<script src='assets/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
        </script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

        <!-- page specific plugin scripts -->
        <!--[if lte IE 8]>
          <script src="assets/js/excanvas.min.js"></script>
        <![endif]-->
        <script src="assets/js/jquery-ui.custom.min.js"></script>
        <script src="assets/js/jquery.ui.touch-punch.min.js"></script>
        <script src="assets/js/jquery.easypiechart.min.js"></script>
        <script src="assets/js/jquery.sparkline.min.js"></script>
        <script src="assets/js/flot/jquery.flot.min.js"></script>
        <script src="assets/js/flot/jquery.flot.pie.min.js"></script>
        <script src="assets/js/flot/jquery.flot.resize.min.js"></script>

        <!-- ace scripts -->
        <script src="assets/js/ace-elements.min.js"></script>
        <script src="assets/js/ace.min.js"></script>

        <!-- inline scripts related to this page -->
        <script type="text/javascript">
            jQuery(function ($) {
                //initiate dataTables plugin
                var myTable =
                        $('#dynamic-table')
                        //.wrap("<div class='dataTables_borderWrap' />")   //if you are applying horizontal scrolling (sScrollX)
                        .DataTable({
                            bAutoWidth: false,
                            "aoColumns": [
                                {"bSortable": false},
                                null, null, null, null, null,
                                {"bSortable": false}
                            ],
                            "aaSorting": [],
                            //"bProcessing": true,
                            //"bServerSide": true,
                            //"sAjaxSource": "http://127.0.0.1/table.php"	,

                            //,
                            //"sScrollY": "200px",
                            //"bPaginate": false,

                            //"sScrollX": "100%",
                            //"sScrollXInner": "120%",
                            //"bScrollCollapse": true,
                            //Note: if you are applying horizontal scrolling (sScrollX) on a ".table-bordered"
                            //you may want to wrap the table inside a "div.dataTables_borderWrap" element

                            //"iDisplayLength": 50


                            select: {
                                style: 'multi'
                            }
                        });



                $.fn.dataTable.Buttons.swfPath = "dist/js/dataTables/extensions/Buttons/swf/flashExport.swf"; //in Ace demo dist will be replaced by correct assets path
                $.fn.dataTable.Buttons.defaults.dom.container.className = 'dt-buttons btn-overlap btn-group btn-overlap';

                new $.fn.dataTable.Buttons(myTable, {
                    buttons: [
                        {
                            "extend": "colvis",
                            "text": "<i class='fa fa-search bigger-110 blue'></i> <span class='hidden'>Show/hide columns</span>",
                            "className": "btn btn-white btn-primary btn-bold",
                            columns: ':not(:first):not(:last)'
                        },
                        {
                            "extend": "copy",
                            "text": "<i class='fa fa-copy bigger-110 pink'></i> <span class='hidden'>Copy to clipboard</span>",
                            "className": "btn btn-white btn-primary btn-bold"
                        },
                        {
                            "extend": "csv",
                            "text": "<i class='fa fa-database bigger-110 orange'></i> <span class='hidden'>Export to CSV</span>",
                            "className": "btn btn-white btn-primary btn-bold"
                        },
                        {
                            "extend": "excel",
                            "text": "<i class='fa fa-file-excel-o bigger-110 green'></i> <span class='hidden'>Export to Excel</span>",
                            "className": "btn btn-white btn-primary btn-bold"
                        },
                        {
                            "extend": "pdf",
                            "text": "<i class='fa fa-file-pdf-o bigger-110 red'></i> <span class='hidden'>Export to PDF</span>",
                            "className": "btn btn-white btn-primary btn-bold"
                        },
                        {
                            "extend": "print",
                            "text": "<i class='fa fa-print bigger-110 grey'></i> <span class='hidden'>Print</span>",
                            "className": "btn btn-white btn-primary btn-bold",
                            autoPrint: false,
                            message: 'This print was produced using the Print button for DataTables'
                        }
                    ]
                });
                myTable.buttons().container().appendTo($('.tableTools-container'));

                //style the message box
                var defaultCopyAction = myTable.button(1).action();
                myTable.button(1).action(function (e, dt, button, config) {
                    defaultCopyAction(e, dt, button, config);
                    $('.dt-button-info').addClass('gritter-item-wrapper gritter-info gritter-center white');
                });


                var defaultColvisAction = myTable.button(0).action();
                myTable.button(0).action(function (e, dt, button, config) {

                    defaultColvisAction(e, dt, button, config);


                    if ($('.dt-button-collection > .dropdown-menu').length === 0) {
                        $('.dt-button-collection')
                                .wrapInner('<ul class="dropdown-menu dropdown-light dropdown-caret dropdown-caret" />')
                                .find('a').attr('href', '#').wrap("<li />");
                    }
                    $('.dt-button-collection').appendTo('.tableTools-container .dt-buttons');
                });

                ////

                setTimeout(function () {
                    $($('.tableTools-container')).find('a.dt-button').each(function () {
                        var div = $(this).find(' > div').first();
                        if (div.length === 1)
                            div.tooltip({container: 'body', title: div.parent().text()});
                        else
                            $(this).tooltip({container: 'body', title: $(this).text()});
                    });
                }, 500);





                myTable.on('select', function (e, dt, type, index) {
                    if (type === 'row') {
                        $(myTable.row(index).node()).find('input:checkbox').prop('checked', true);
                    }
                });
                myTable.on('deselect', function (e, dt, type, index) {
                    if (type === 'row') {
                        $(myTable.row(index).node()).find('input:checkbox').prop('checked', false);
                    }
                });




                /////////////////////////////////
                //table checkboxes
                $('th input[type=checkbox], td input[type=checkbox]').prop('checked', false);

                //select/deselect all rows according to table header checkbox
                $('#dynamic-table > thead > tr > th input[type=checkbox], #dynamic-table_wrapper input[type=checkbox]').eq(0).on('click', function () {
                    var th_checked = this.checked;//checkbox inside "TH" table header

                    $('#dynamic-table').find('tbody > tr').each(function () {
                        var row = this;
                        if (th_checked)
                            myTable.row(row).select();
                        else
                            myTable.row(row).deselect();
                    });
                });

                //select/deselect a row when the checkbox is checked/unchecked
                $('#dynamic-table').on('click', 'td input[type=checkbox]', function () {
                    var row = $(this).closest('tr').get(0);
                    if (!this.checked)
                        myTable.row(row).deselect();
                    else
                        myTable.row(row).select();
                });



                $(document).on('click', '#dynamic-table .dropdown-toggle', function (e) {
                    e.stopImmediatePropagation();
                    e.stopPropagation();
                    e.preventDefault();
                });



                //And for the first simple table, which doesn't have TableTools or dataTables
                //select/deselect all rows according to table header checkbox
                var active_class = 'active';
                $('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {
                    var th_checked = this.checked;//checkbox inside "TH" table header

                    $(this).closest('table').find('tbody > tr').each(function () {
                        var row = this;
                        if (th_checked)
                            $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
                        else
                            $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
                    });
                });

                //select/deselect a row when the checkbox is checked/unchecked
                $('#simple-table').on('click', 'td input[type=checkbox]', function () {
                    var $row = $(this).closest('tr');
                    if ($row.is('.detail-row '))
                        return;
                    if (this.checked)
                        $row.addClass(active_class);
                    else
                        $row.removeClass(active_class);
                });



                /********************************/
                //add tooltip for small view action buttons in dropdown menu
                $('[data-rel="tooltip"]').tooltip({placement: tooltip_placement});

                //tooltip placement on right or left
                function tooltip_placement(context, source) {
                    var $source = $(source);
                    var $parent = $source.closest('table');
                    var off1 = $parent.offset();
                    var w1 = $parent.width();

                    var off2 = $source.offset();
                    //var w2 = $source.width();

                    if (parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2))
                        return 'right';
                    return 'left';
                }




                /***************/
                $('.show-details-btn').on('click', function (e) {
                    e.preventDefault();
                    $(this).closest('tr').next().toggleClass('open');
                    $(this).find(ace.vars['.icon']).toggleClass('fa-angle-double-down').toggleClass('fa-angle-double-up');
                });
                /***************/





                /**
                 //add horizontal scrollbars to a simple table
                 $('#simple-table').css({'width':'2000px', 'max-width': 'none'}).wrap('<div style="width: 1000px;" />').parent().ace_scroll(
                 {
                 horizontal: true,
                 styleClass: 'scroll-top scroll-dark scroll-visible',//show the scrollbars on top(default is bottom)
                 size: 2000,
                 mouseWheelLock: true
                 }
                 ).css('padding-top', '12px');
                 */


            });
        </script>
    </body>
</html>
