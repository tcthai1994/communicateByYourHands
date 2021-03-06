<%-- 
    Document   : buy-license
    Created on : Feb 23, 2016, 4:56:49 PM
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
        <c:if test="${sessionScope.USER == null}">
            <div style="margin:auto">
                <h3>Click<a href="index.jsp"> here </a>to login</h3>
            </div>
        </c:if>
        <c:if test="${sessionScope.USER != null}">
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
                        <a href="<%=request.getContextPath()%>/LoadDictionaryPageServlet" class="navbar-brand">
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
                                    <!--                                    <li>
                                                                            <a href="#">
                                                                                <i class="ace-icon fa fa-cog"></i>
                                                                                Settings
                                                                            </a>
                                                                        </li>-->

                                    <li>
                                        <a href="<%=request.getContextPath()%>/LoadUserProfileServlet">
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
                            <a href="<%=request.getContextPath()%>/LoadDictionaryPageServlet">
                                <i class="menu-icon fa fa-globe"></i>
                                <span class="menu-text"> Dictionary </span>


                            </a>
                        </li>



                        <li class="active open">
                            <a href="<%=request.getContextPath()%>/LoadBuyLicenseServlet">
                                <i class="menu-icon fa fa-key"></i>
                                <span class="menu-text"> Buy License </span>
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
                                        Buy license

                                    </h1>
                                </div><!-- /.page-header -->

                                <div class="row">
                                    <div class="col-xs-12">
                                        <!-- PAGE CONTENT BEGINS -->
                                        <div class="row">
                                            <div class="col-xs-12">
                                                <div class="row">
                                                    <div class="col-xs-4 col-sm-3 pricing-span-header">
                                                        <div class="widget-box transparent">
                                                            <div class="widget-header">
                                                                <h5 class="widget-title bigger lighter">Package Name</h5>
                                                            </div>

                                                            <div class="widget-body">
                                                                <div class="widget-main no-padding">
                                                                    <ul class="list-unstyled list-striped pricing-table-header">
                                                                        <li>Translate Online </li>
                                                                        <li>Translate Offline </li>
                                                                        <li>Dictionary </li>
                                                                        <li>Training New Words </li>
                                                                        <li>Sharing Content </li>
                                                                    </ul>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <c:set var="price" value="${requestScope.PRICE}"/>
                                                    <c:set var="licenseName" value="${requestScope.NAME}"/>
                                                    <c:set var="url" value="<%=request.getServerName() + ':' + request.getServerPort() + request.getContextPath()%>"/>
                                                    <div class="col-xs-8 col-sm-9 pricing-span-body">
                                                        <div class="pricing-span">
                                                            <div class="widget-box pricing-box-small widget-color-red3">
                                                                <div class="widget-header">
                                                                    <h5 class="widget-title bigger lighter">Basic</h5>
                                                                </div>

                                                                <div class="widget-body">
                                                                    <div class="widget-main no-padding">
                                                                        <ul class="list-unstyled list-striped pricing-table">
                                                                            <li> 
                                                                                <i class="ace-icon fa fa-check green"></i>
                                                                            </li>
                                                                            <li>
                                                                                <i class="ace-icon fa fa-times red"></i>
                                                                            </li>
                                                                            <li>
                                                                                <i class="ace-icon fa fa-check green"></i>
                                                                            </li>
                                                                            <li>
                                                                                <i class="ace-icon fa fa-times red"></i>
                                                                            </li>
                                                                            <li>
                                                                                <i class="ace-icon fa fa-times red"></i>
                                                                            </li>
                                                                        </ul>

                                                                        <div class="price">
                                                                            <span class="label label-lg label-inverse arrowed-in arrowed-in-right">
                                                                                Free
                                                                            </span>
                                                                        </div>
                                                                    </div>

                                                                    <div>
                                                                        <!--                                                                        <a href="#" class="btn btn-block btn-sm btn-danger">
                                                                                                                                                    <span>Buy</span>
                                                                                                                                                </a>-->
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="pricing-span">
                                                            <div class="widget-box pricing-box-small widget-color-orange">
                                                                <div class="widget-header">
                                                                    <h5 class="widget-title bigger lighter">${licenseName}</h5>
                                                                </div>

                                                                <div class="widget-body">
                                                                    <div class="widget-main no-padding">
                                                                        <ul class="list-unstyled list-striped pricing-table">
                                                                            <li>
                                                                                <i class="ace-icon fa fa-check green"></i>
                                                                            </li>
                                                                            <li>
                                                                                <i class="ace-icon fa fa-check green"></i>
                                                                            </li>
                                                                            <li>
                                                                                <i class="ace-icon fa fa-check green"></i>
                                                                            </li>
                                                                            <li>
                                                                                <i class="ace-icon fa fa-check green"></i>
                                                                            </li>
                                                                            <li>
                                                                                <i class="ace-icon fa fa-check green"></i>
                                                                            </li>
                                                                        </ul>

                                                                        <div class="price">
                                                                            <span class="label label-lg label-inverse arrowed-in arrowed-in-right">
                                                                                ${price}
                                                                                <small>k/month</small>
                                                                            </span>
                                                                        </div>
                                                                    </div>

                                                                    <div>

                                                                        <form action="https://www.sandbox.paypal.com/cgi-bin/webscr" method="post" target="_top">
                                                                            <input type="hidden" name="cmd" value="_xclick">
                                                                            <input type="hidden" name="business" value="paolo_rossi_92-facilitator@yahoo.com.vn">
                                                                            <input type="hidden" name="lc" value="US">
                                                                            <input type="hidden" name="item_name" value="${licenseName} Package">
                                                                            <input type="hidden" name="item_number" value="1">
                                                                            <input type="hidden" name="amount" value="${price}">
                                                                            <input type="hidden" name="currency_code" value="USD">
                                                                            <input type="hidden" name="button_subtype" value="services">
                                                                            <input type="hidden" name="no_note" value="0">
                                                                            <input type="hidden" name="tax_rate" value="10.000">
                                                                            <input type="hidden" name="bn" value="PP-BuyNowBF:btn_buynow_SM.gif:NonHostedGuest">
                                                                            <input type="hidden" name="return" value="http://${url}/PaymentWithPaypalServlet">
                                                                            <input type="hidden" name="cancel_return" value="http://${url}/payment-fail.jsp">
                                                                            <input type="image" border="0" name="submit" alt="Buy" class="btn btn-block btn-sm btn-warning">
                                                                            <!--                                                                            <img alt="" border="0" src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif" width="1" height="1">-->
                                                                        </form>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>                                                   
                                                    </div>
                                                </div><!-- PAGE CONTENT ENDS -->
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
                if ('ontouchstart' in document.documentElement)
                    document.write("<script src='assets/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
            </script>
            <script type="text/javascript">

            </script>
        </c:if>

    </body>
</html>
