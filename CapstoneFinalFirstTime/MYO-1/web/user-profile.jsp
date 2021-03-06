<%-- 
    Document   : user-profile
    Created on : Mar 16, 2016, 1:05:27 PM
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

        <script>
            function validateUpdate() {
                var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                var email = document.forms["myForm"]["txtEmail"].value;
                var fullname = document.forms["myForm"]["txtFullname"].value;
                var password = document.forms["myForm"]["txtPassword"].value;
                var phone = document.forms["myForm"]["txtPhone"].value;
                var phoneNum = /^\d{10,12}$/;
                var repeat_password = document.forms["myForm"]["txtRepeatpassword"].value;
                var password_old = document.forms["myForm"]["txtPasswordOld"].value;
                var errorContent = "";
                if (!re.test(email)) {
                    errorContent += "Email invalid! (me@example.com) \n";
                }
                if (email.length > 254 || email.length < 10) {
                    errorContent += "Email must be 10 - 254 characters \n";
                }
                if (fullname.length > 50 || fullname.length < 10) {
                    errorContent += "Full name must be 10 - 50 characters \n";
                }
                if (password !== null && repeat_password !== null)
                    if (password !== "" && repeat_password !== "")
                        if (password.length > 12 || password.length < 6) {
                            errorContent += "Password must be 6 - 12  characters \n";

                        }
                if (!phoneNum.test(phone)) {
                    errorContent += "Phone must be numbers and have 10 - 12 numbers\n";
                }
                //                if (phone.length > 12 || phone.length < 10) {
                //                    errorContent += "Phone must be 10 - 12 characters \n";
                //                }
                if (password !== repeat_password) {
                    errorContent += "Repeat password does not match password \n";
                }
                if (errorContent !== "") {
                    alert(errorContent);
                    return false;
                }
            }
        </script>
    </head>
    <body class="no-skin">
        <%request.setCharacterEncoding("UTF-8");%>
        <c:if test="${sessionScope.USER == null}">
            <div style="margin:auto">
                <h3>Click<a href="index.jsp"> here </a>to login</h3>
            </div>
        </c:if>
        <c:if test="${sessionScope.USER != null}">
            <div id="navbar" class="navbar navbar-default navbar-fixed-top">
                <script type="text/javascript">
                    try {
                        ace.settings.check('navbar', 'fixed')
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
                                    <img class="nav-user-photo" src="assets/avatars/avatar2.png" alt="AnhND's photo"/>
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
                        ace.settings.check('main-container', 'fixed')
                    } catch (e) {
                    }
                </script>

                <div id="sidebar" class="sidebar                  responsive sidebar-scroll sidebar-fixed">
                    <script type="text/javascript">
                        try {
                            ace.settings.check('sidebar', 'fixed')
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
                        <li class="active open">
                            <a href="<%=request.getContextPath()%>/LoadDictionaryPageServlet">
                                <i class="menu-icon fa fa-globe"></i>
                                <span class="menu-text"> Dictionary </span>
                            </a>
                        </li>
                        <li class="">
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
                            ace.settings.check('sidebar', 'collapsed')
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
                                        User profile

                                    </h1>
                                </div><!-- /.page-header -->

                                <div class="row">
                                    <div class="col-xs-12">
                                        <!-- PAGE CONTENT BEGINS -->
                                        <form class="form-horizontal" action="CenterServlet" onsubmit="return validateUpdate();" method="POST" name="myForm">
                                            <c:set var="item" value="${requestScope.userProfile}"/>
                                            <c:forEach var="profile" items="${item}">
                                                <div class="tabbable">
                                                    <ul class="nav nav-tabs padding-16">
                                                        <li class="active">
                                                            <a data-toggle="tab" href="#edit-basic" aria-expanded="true">
                                                                <i class="green ace-icon fa fa-pencil-square-o bigger-125"></i>
                                                                Basic Info
                                                            </a>
                                                        </li>
                                                        <li class="">
                                                            <a data-toggle="tab" href="#edit-password" aria-expanded="false">
                                                                <i class="blue ace-icon fa fa-key bigger-125"></i>
                                                                Password
                                                            </a>
                                                        </li>
                                                    </ul>

                                                    <div class="tab-content profile-edit-tab-content">

                                                        <div id="edit-basic" class="tab-pane active">
                                                            <h4 class="header blue bolder smaller">General</h4>

                                                            <input type="hidden" name="txtDetailId" value="${profile.value.detailId}" readonly=""/>
                                                            <div class="form-group">
                                                                <label class="col-sm-3 control-label no-padding-right" for="form-field-1">User name</label>

                                                                <div class="col-sm-9">
                                                                    <input class="col-sm-3" id="form-field-1" type="text" name="txtUsername" readonly="" value="${profile.key.username}"/>
                                                                </div>
                                                            </div>

                                                            <div class="space-4"></div>

                                                            <div class="form-group">
                                                                <label class="col-sm-3 control-label no-padding-right" for="form-field-2">Email(*)</label>

                                                                <div class="col-sm-9">
                                                                    <input class="col-sm-5" id="form-field-2" type="text" name="txtEmail" value="${profile.value.email}" required/>
                                                                </div>
                                                            </div>

                                                            <div class="space-4"></div>

                                                            <div class="form-group">
                                                                <label class="col-sm-3 control-label no-padding-right" for="form-field-3">Full name(*)</label>

                                                                <div class="col-sm-9">
                                                                    <input class="col-sm-4" id="form-field-3" type="text" name="txtFullname" value="${profile.value.fullname}" required/>
                                                                </div>
                                                            </div>

                                                            <div class="space-4"></div>

                                                            <div class="form-group">
                                                                <label class="col-sm-3 control-label no-padding-right" for="form-field-4">License Type</label>

                                                                <div class="col-sm-9">
                                                                    <input class="col-sm-4" id="form-field-4" readonly="" type="text" name="txtLicenseType" value="${profile.key.licenseType}" required/>
                                                                </div>
                                                            </div>

                                                            <div class="space-4"></div>

                                                            <div class="form-group">
                                                                <label class="col-sm-3 control-label no-padding-right" for="form-field-5">Expiration Date</label>

                                                                <div class="col-sm-9">
                                                                    <input class="col-sm-4" id="form-field-5" readonly="" type="text" name="dtExpiredDate" value="${profile.key.expiredDate}" required/>
                                                                </div>
                                                            </div>

                                                            <div class="space-4"></div>

                                                            <div class="form-group">
                                                                <label class="col-sm-3 control-label no-padding-right" for="form-field-6">Phone(*)</label>

                                                                <div class="col-sm-9">
                                                                    <input class="col-sm-2" id="form-field-6" type="text" name="txtPhone" value="${profile.value.phone}" required/>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div id="edit-password" class="tab-pane">
                                                            <div class="space-10"></div>

                                                            <div class="form-group">
                                                                <label class="col-sm-3 control-label no-padding-right" for="password">New Password</label>

                                                                <div class="col-sm-9">
                                                                    <input type="password" name="txtPassword" id="password">
                                                                </div>
                                                                <input type="hidden" name="txtPasswordOld" value="${profile.key.password}" readonly=""/>
                                                            </div>

                                                            <div class="space-4"></div>

                                                            <div class="form-group">
                                                                <label class="col-sm-3 control-label no-padding-right" for="confirm_password">Confirm Password</label>

                                                                <div class="col-sm-9">
                                                                    <input type="password" name="txtRepeatpassword" id="confirm_password">
                                                                    <span id="message"></span>
                                                                </div>

                                                            </div>
                                                        </div>

                                                    </div>

                                                </div>
                                            </c:forEach>
                                            <div class="clearfix form-actions">
                                                <div class="col-md-offset-3 col-md-9">
                                                    <button class="btn btn-info" type="submit" value="UserUpdateProfile" name="btAction">
                                                        <i class="ace-icon fa fa-check bigger-110"></i>
                                                        Save
                                                    </button>

                                                    &nbsp; &nbsp;
                                                    <button class="btn" type="reset">
                                                        <i class="ace-icon fa fa-undo bigger-110"></i>
                                                        Reset
                                                    </button>
                                                </div>
                                            </div>
                                        </form>
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
                                            window.jQuery || document.write("<script src='assets/js/jquery.min.js'>" + "<" + "/script>");</script>

            <!-- <![endif]-->
            <!--[if IE]>
            <script type="text/javascript">
             window.jQuery || document.write("<script src='assets/js/jquery1x.min.js'>"+"<"+"/script>");
            </script>
            <![endif]-->
            <script type="text/javascript">
                jQuery(function ($) {

                    //data for tree element
                    var category = {
                        'for-sale': {text: 'For Sale', type: 'folder'},
                        'vehicles': {text: 'Vehicles', type: 'item'},
                        'rentals': {text: 'Rentals', type: 'item'},
                        'real-estate': {text: 'Real Estate', type: 'item'},
                        'pets': {text: 'Pets', type: 'item'},
                        'tickets': {text: 'Tickets', type: 'item'}
                    }
                    category['for-sale']['additionalParameters'] = {
                        'children': {
                            'appliances': {text: 'Appliances', type: 'item'},
                            'arts-crafts': {text: 'Arts & Crafts', type: 'item'},
                            'clothing': {text: 'Clothing', type: 'item'},
                            'computers': {text: 'Computers', type: 'item'},
                            'jewelry': {text: 'Jewelry', type: 'item'},
                            'office-business': {text: 'Office', type: 'item'},
                            'sports-fitness': {text: 'Sports & Fitness', type: 'item'}
                        }
                    }

                    var dataSource1 = function (options, callback) {
                        var $data = null
                        if (!("text" in options) && !("type" in options)) {
                            $data = category; //the root tree
                            callback({data: $data});
                            return;
                        }
                        else if ("type" in options && options.type == "folder") {
                            if ("additionalParameters" in options && "children" in options.additionalParameters)
                                $data = options.additionalParameters.children || {};
                            else
                                $data = {}//no data
                        }

                        callback({data: $data})
                    }

                    $('#cat-tree').ace_tree({
                        dataSource: dataSource1,
                        multiSelect: true,
                        cacheItems: true,
                        'open-icon': 'ace-icon tree-minus',
                        'close-icon': 'ace-icon tree-plus',
                        'itemSelect': true,
                        'folderSelect': false,
                        'selected-icon': 'ace-icon fa fa-check',
                        'unselected-icon': 'ace-icon fa fa-times',
                        loadingHTML: '<div class="tree-loading"><i class="ace-icon fa fa-refresh fa-spin blue"></i></div>'
                    });
                    $('.tree-container').ace_scroll({size: 250, mouseWheelLock: true});
                    $('#cat-tree').on('closed.fu.tree disclosedFolder.fu.tree', function () {
                        $('.tree-container').ace_scroll('reset').ace_scroll('start');
                    });
                    //select2 location element
                    $('.select2').css('min-width', '150px').select2({allowClear: true});
                    //jQuery ui distance slider
                    $("#slider-range").css('width', '150px').slider({
                        range: true,
                        min: 0,
                        max: 100,
                        values: [17, 67],
                        slide: function (event, ui) {
                            var val = ui.values[$(ui.handle).index() - 1] + "";
                            if (!ui.handle.firstChild) {
                                $("<div class='tooltip bottom in' style='display:none;left:-6px;top:14px;'><div class='tooltip-arrow'></div><div class='tooltip-inner'></div></div>")
                                        .prependTo(ui.handle);
                            }
                            $(ui.handle.firstChild).show().children().eq(1).text(val);
                        }
                    }).find('span.ui-slider-handle').on('blur', function () {
                        $(this.firstChild).hide();
                    });
                    //this is for demo only
                    $('.thumbnail').on('mouseenter', function () {
                        $(this).find('.info-label').addClass('label-primary');
                    }).on('mouseleave', function () {
                        $(this).find('.info-label').removeClass('label-primary');
                    });
                    //toggle display format buttons
                    $('#toggle-result-format .btn').tooltip({container: 'body'}).on('click', function (e) {
                        $(this).siblings().each(function () {
                            $(this).removeClass($(this).attr('data-class')).addClass('btn-grey');
                        });
                        $(this).removeClass('btn-grey').addClass($(this).attr('data-class'));
                    });
                    ////////////////////
                    //show different search page
                    $('#toggle-result-page .btn').on('click', function (e) {
                        var target = $(this).find('input[type=radio]');
                        var which = parseInt(target.val());
                        $('.search-page').parent().addClass('hide');
                        $('#search-page-' + which).parent().removeClass('hide');
                    });
                });
                $('#password, #confirm_password').on('keyup', function () {
                    if ($('#password').val() === $('#confirm_password').val()) {
                        $('#message').html('<i class="ace-icon fa fa-check green"></i>');
                    } else
                        $('#message').html('<i class="ace-icon fa fa-times red"></i>');
                });</script>
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
        </c:if>

    </body>
</html>