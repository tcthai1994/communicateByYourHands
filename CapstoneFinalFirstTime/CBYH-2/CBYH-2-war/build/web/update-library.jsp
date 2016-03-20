<%-- 
    Document   : update-library
    Created on : Mar 2, 2016, 9:58:58 PM
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
            function validateUpdateLibrary() {
                var libraryName = document.forms["myForm"]["txtLibraryname"].value;
                if (libraryName.length > 50 || libraryName.length < 3) {
                    alert("Library name must be 3 - 50 characters");
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
            <c:if test="${sessionScope.isAdmin == null}">
                <div style="margin:auto">
                    <h3>You are not allow to access this page, click<a href="<%=request.getContextPath()%>/LoadDictionaryPageServlet"> here </a>to return</h3>
                </div>
            </c:if>
            <c:if test="${sessionScope.isAdmin != null}">
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
                            <a href="<%=request.getContextPath()%>/AccountServlet" class="navbar-brand">
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
<!--                                        <li>
                                            <a href="#">
                                                <i class="ace-icon fa fa-cog"></i>
                                                Settings
                                            </a>
                                        </li>-->

<!--                                        <li>
                                            <a href="<%=request.getContextPath()%>/LoadUserProfileServlet">
                                                <i class="ace-icon fa fa-user"></i>
                                                Profile
                                            </a>
                                        </li>

                                        <li class="divider"></li>-->

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
                                <a href="<%=request.getContextPath()%>/AccountServlet">
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
                            <li class="">
                                <a href="editData.jsp" >
                                    <i class="menu-icon fa fa-pencil-square-o"></i>
                                    <span class="menu-text"> Edit Data Content </span>
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
                                            <form action="LibraryProcessServlet" onsubmit="return validateUpdateLibrary();" method="POST" name="myForm" class="form-horizontal">
                                                
                                                <c:set var="status" value='<%= request.getParameter("chbStatus")%>'></c:set>
                                                    <div class="form-group">
                                                        <label class="col-sm-3 control-label no-padding-right" for="form-input-readonly"> Library ID </label>

                                                        <div class="col-sm-9">
                                                            <input readonly="" name="txtLibraryId" type="text" class="col-xs-10 col-sm-1" id="form-input-readonly" value="<%= request.getParameter("txtLibraryId")%>" />
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> Library Name (*) </label>

                                                    <div class="col-sm-9">
                                                        <input type="text" name="txtLibraryname" value="<%= request.getParameter("txtLibraryname")%>" id="form-field-1" placeholder="Library name" class="col-xs-10 col-sm-3" required/>
                                                    </div>
                                                </div>

                                                <!--                                    <div class="form-group">
                                                                                        <label class="col-sm-3 control-label no-padding-right" for="form-field-1-1"> Full Length </label>
                                                
                                                                                        <div class="col-sm-9">
                                                                                            <input type="text" id="form-field-1-1" placeholder="Text Field" class="form-control" />
                                                                                        </div>
                                                                                    </div>-->

                                                <div class="form-group">
                                                    <label class="col-sm-3 control-label no-padding-right"> Status </label>
                                                    <label>
                                                        <input name="chbStatus" class="ace ace-switch ace-switch-6" type="checkbox" id="checkstatus" value="ON" <c:if test="${status=='true'}">checked="checked"</c:if>/>
                                                            <span class="lbl" ></span>
                                                        </label>
                                                    </div>

                                                    <div class="clearfix form-actions">
                                                        <div class="col-md-offset-3 col-md-9">
                                                            <button class="btn btn-info" type="submit" value="UpdateLibrary" name="btAction">
                                                                <i class="ace-icon fa fa-check bigger-110"></i>
                                                                Submit
                                                            </button>

                                                            &nbsp; &nbsp; &nbsp;
                                                            <button class="btn" type="reset">
                                                                <i class="ace-icon fa fa-refresh bigger-110"></i>
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
                    <script src="assets/js/date-time/bootstrap-datepicker.min.js"></script>
                    <script src="assets/js/date-time/bootstrap-timepicker.min.js"></script>
                    <script src="assets/js/date-time/moment.min.js"></script>
                    <script src="assets/js/date-time/daterangepicker.min.js"></script>
                    <script src="assets/js/date-time/bootstrap-datetimepicker.min.js"></script>
                    <script src="assets/js/jquery.knob.min.js"></script>
                    <script src="assets/js/jquery.autosize.min.js"></script>
                    <script src="assets/js/jquery.inputlimiter.1.3.1.min.js"></script>
                    <script src="assets/js/jquery.maskedinput.min.js"></script>
                    <script src="assets/js/bootstrap-tag.min.js"></script>
                    <script src="assets/js/typeahead.jquery.min.js"></script>

                    <!-- ace scripts -->
                    <script src="assets/js/ace-elements.min.js"></script>
                    <script src="assets/js/ace.min.js"></script>

                    <!-- inline scripts related to this page -->
                    <script type="text/javascript">
                        jQuery(function ($) {
                            $('#id-disable-check').on('click', function () {
                                var inp = $('#form-input-readonly').get(0);
                                if (inp.hasAttribute('disabled')) {
                                    inp.setAttribute('readonly', 'true');
                                    inp.removeAttribute('disabled');
                                    inp.value = "This text field is readonly!";
                                }
                                else {
                                    inp.setAttribute('disabled', 'disabled');
                                    inp.removeAttribute('readonly');
                                    inp.value = "This text field is disabled!";
                                }
                            });


                            $('.chosen-select').chosen({allow_single_deselect: true});
                            //resize the chosen on window resize

                            $(window)
                                    .off('resize.chosen')
                                    .on('resize.chosen', function () {
                                        $('.chosen-select').each(function () {
                                            var $this = $(this);
                                            $this.next().css({'width': $this.parent().width()});
                                        });
                                    }).trigger('resize.chosen');


                            $('#chosen-multiple-style').on('click', function (e) {
                                var target = $(e.target).find('input[type=radio]');
                                var which = parseInt(target.val());
                                if (which === 2)
                                    $('#form-field-select-4').addClass('tag-input-style');
                                else
                                    $('#form-field-select-4').removeClass('tag-input-style');
                            });


                            $('[data-rel=tooltip]').tooltip({container: 'body'});
                            $('[data-rel=popover]').popover({container: 'body'});

                            $('textarea[class*=autosize]').autosize({append: "\n"});
                            $('textarea.limited').inputlimiter({
                                remText: '%n character%s remaining...',
                                limitText: 'max allowed : %n.'
                            });

                            $.mask.definitions['~'] = '[+-]';
                            $('.input-mask-date').mask('99/99/9999');
                            $('.input-mask-phone').mask('(999) 999-9999');
                            $('.input-mask-eyescript').mask('~9.99 ~9.99 999');
                            $(".input-mask-product").mask("a*-999-a999", {placeholder: " ", completed: function () {
                                    alert("You typed the following: " + this.val());
                                }});



                            $("#input-size-slider").css('width', '200px').slider({
                                value: 1,
                                range: "min",
                                min: 1,
                                max: 8,
                                step: 1,
                                slide: function (event, ui) {
                                    var sizing = ['', 'input-sm', 'input-lg', 'input-mini', 'input-small', 'input-medium', 'input-large', 'input-xlarge', 'input-xxlarge'];
                                    var val = parseInt(ui.value);
                                    $('#form-field-4').attr('class', sizing[val]).val('.' + sizing[val]);
                                }
                            });

                            $("#input-span-slider").slider({
                                value: 1,
                                range: "min",
                                min: 1,
                                max: 12,
                                step: 1,
                                slide: function (event, ui) {
                                    var val = parseInt(ui.value);
                                    $('#form-field-5').attr('class', 'col-xs-' + val).val('.col-xs-' + val);
                                }
                            });



                            //"jQuery UI Slider"
                            //range slider tooltip example
                            $("#slider-range").css('height', '200px').slider({
                                orientation: "vertical",
                                range: true,
                                min: 0,
                                max: 100,
                                values: [17, 67],
                                slide: function (event, ui) {
                                    var val = ui.values[$(ui.handle).index() - 1] + "";

                                    if (!ui.handle.firstChild) {
                                        $("<div class='tooltip right in' style='display:none;left:16px;top:-6px;'><div class='tooltip-arrow'></div><div class='tooltip-inner'></div></div>")
                                                .prependTo(ui.handle);
                                    }
                                    $(ui.handle.firstChild).show().children().eq(1).text(val);
                                }
                            }).find('a').on('blur', function () {
                                $(this.firstChild).hide();
                            });


                            $("#slider-range-max").slider({
                                range: "max",
                                min: 1,
                                max: 10,
                                value: 2
                            });

                            $("#slider-eq > span").css({width: '90%', 'float': 'left', margin: '15px'}).each(function () {
                                // read initial values from markup and remove that
                                var value = parseInt($(this).text(), 10);
                                $(this).empty().slider({
                                    value: value,
                                    range: "min",
                                    animate: true

                                });
                            });

                            $("#slider-eq > span.ui-slider-purple").slider('disable');//disable third item


                            $('#id-input-file-1 , #id-input-file-2').ace_file_input({
                                no_file: 'No File ...',
                                btn_choose: 'Choose',
                                btn_change: 'Change',
                                droppable: false,
                                onchange: null,
                                thumbnail: false //| true | large
                                        //whitelist:'gif|png|jpg|jpeg'
                                        //blacklist:'exe|php'
                                        //onchange:''
                                        //
                            });
                            //pre-show a file name, for example a previously selected file
                            //$('#id-input-file-1').ace_file_input('show_file_list', ['myfile.txt'])


                            $('#id-input-file-3').ace_file_input({
                                style: 'well',
                                btn_choose: 'Drop files here or click to choose',
                                btn_change: null,
                                no_icon: 'ace-icon fa fa-cloud-upload',
                                droppable: true,
                                thumbnail: 'small'//large | fit
                                        //,icon_remove:null//set null, to hide remove/reset button
                                        /**,before_change:function(files, dropped) {
                                         //Check an example below
                                         //or examples/file-upload.html
                                         return true;
                                         }*/
                                        /**,before_remove : function() {
                                         return true;
                                         }*/
                                ,
                                preview_error: function (filename, error_code) {
                                    //name of the file that failed
                                    //error_code values
                                    //1 = 'FILE_LOAD_FAILED',
                                    //2 = 'IMAGE_LOAD_FAILED',
                                    //3 = 'THUMBNAIL_FAILED'
                                    //alert(error_code);
                                }

                            }).on('change', function () {
                                //console.log($(this).data('ace_input_files'));
                                //console.log($(this).data('ace_input_method'));
                            });


                            //dynamically change allowed formats by changing allowExt && allowMime function
                            $('#id-file-format').removeAttr('checked').on('change', function () {
                                var whitelist_ext, whitelist_mime;
                                var btn_choose;
                                var no_icon;
                                if (this.checked) {
                                    btn_choose = "Drop images here or click to choose";
                                    no_icon = "ace-icon fa fa-picture-o";

                                    whitelist_ext = ["jpeg", "jpg", "png", "gif", "bmp"];
                                    whitelist_mime = ["image/jpg", "image/jpeg", "image/png", "image/gif", "image/bmp"];
                                }
                                else {
                                    btn_choose = "Drop files here or click to choose";
                                    no_icon = "ace-icon fa fa-cloud-upload";

                                    whitelist_ext = null;//all extensions are acceptable
                                    whitelist_mime = null;//all mimes are acceptable
                                }
                                var file_input = $('#id-input-file-3');
                                file_input
                                        .ace_file_input('update_settings',
                                                {
                                                    'btn_choose': btn_choose,
                                                    'no_icon': no_icon,
                                                    'allowExt': whitelist_ext,
                                                    'allowMime': whitelist_mime
                                                });
                                file_input.ace_file_input('reset_input');

                                file_input
                                        .off('file.error.ace')
                                        .on('file.error.ace', function (e, info) {
                                            //console.log(info.file_count);//number of selected files
                                            //console.log(info.invalid_count);//number of invalid files
                                            //console.log(info.error_list);//a list of errors in the following format

                                            //info.error_count['ext']
                                            //info.error_count['mime']
                                            //info.error_count['size']

                                            //info.error_list['ext']  = [list of file names with invalid extension]
                                            //info.error_list['mime'] = [list of file names with invalid mimetype]
                                            //info.error_list['size'] = [list of file names with invalid size]


                                            /**
                                             if( !info.dropped ) {
                                             //perhapse reset file field if files have been selected, and there are invalid files among them
                                             //when files are dropped, only valid files will be added to our file array
                                             e.preventDefault();//it will rest input
                                             }
                                             */


                                            //if files have been selected (not dropped), you can choose to reset input
                                            //because browser keeps all selected files anyway and this cannot be changed
                                            //we can only reset file field to become empty again
                                            //on any case you still should check files with your server side script
                                            //because any arbitrary file can be uploaded by user and it's not safe to rely on browser-side measures
                                        });

                            });

                            $('#spinner1').ace_spinner({value: 0, min: 0, max: 200, step: 10, btn_up_class: 'btn-info', btn_down_class: 'btn-info'})
                                    .on('change', function () {
                                        //alert(this.value)
                                    });
                            $('#spinner2').ace_spinner({value: 0, min: 0, max: 10000, step: 100, touch_spinner: true, icon_up: 'ace-icon fa fa-caret-up', icon_down: 'ace-icon fa fa-caret-down'});
                            $('#spinner3').ace_spinner({value: 0, min: -100, max: 100, step: 10, on_sides: true, icon_up: 'ace-icon fa fa-plus smaller-75', icon_down: 'ace-icon fa fa-minus smaller-75', btn_up_class: 'btn-success', btn_down_class: 'btn-danger'});
                            //$('#spinner1').ace_spinner('disable').ace_spinner('value', 11);
                            //or
                            //$('#spinner1').closest('.ace-spinner').spinner('disable').spinner('enable').spinner('value', 11);//disable, enable or change value
                            //$('#spinner1').closest('.ace-spinner').spinner('value', 0);//reset to 0


                            //datepicker plugin
                            //link
                            $('.date-picker').datepicker({
                                autoclose: true,
                                todayHighlight: true
                            })
                                    //show datepicker when clicking on the icon
                                    .next().on(ace.click_event, function () {
                                $(this).prev().focus();
                            });

                            //or change it into a date range picker
                            $('.input-daterange').datepicker({autoclose: true});


                            //to translate the daterange picker, please copy the "examples/daterange-fr.js" contents here before initialization
                            $('input[name=date-range-picker]').daterangepicker({
                                'applyClass': 'btn-sm btn-success',
                                'cancelClass': 'btn-sm btn-default',
                                locale: {
                                    applyLabel: 'Apply',
                                    cancelLabel: 'Cancel'
                                }
                            })
                                    .prev().on(ace.click_event, function () {
                                $(this).next().focus();
                            });


                            $('#timepicker1').timepicker({
                                minuteStep: 1,
                                showSeconds: true,
                                showMeridian: false
                            }).next().on(ace.click_event, function () {
                                $(this).prev().focus();
                            });

                            $('#date-timepicker1').datetimepicker().next().on(ace.click_event, function () {
                                $(this).prev().focus();
                            });


                            $('#colorpicker1').colorpicker();

                            $('#simple-colorpicker-1').ace_colorpicker();
                            //$('#simple-colorpicker-1').ace_colorpicker('pick', 2);//select 2nd color
                            //$('#simple-colorpicker-1').ace_colorpicker('pick', '#fbe983');//select #fbe983 color
                            //var picker = $('#simple-colorpicker-1').data('ace_colorpicker')
                            //picker.pick('red', true);//insert the color if it doesn't exist


                            $(".knob").knob();


                            var tag_input = $('#form-field-tags');
                            try {
                                tag_input.tag(
                                        {
                                            placeholder: tag_input.attr('placeholder'),
                                            //enable typeahead by specifying the source array
                                            source: ace.vars['US_STATES'] //defined in ace.js >> ace.enable_search_ahead
                                                    /**
                                                     //or fetch data from database, fetch those that match "query"
                                                     source: function(query, process) {
                                                     $.ajax({url: 'remote_source.php?q='+encodeURIComponent(query)})
                                                     .done(function(result_items){
                                                     process(result_items);
                                                     });
                                                     }
                                                     */
                                        }
                                );

                                //programmatically add a new
                                var $tag_obj = $('#form-field-tags').data('tag');
                                $tag_obj.add('Programmatically Added');
                            }
                            catch (e) {
                                //display a textarea for old IE, because it doesn't support this plugin or another one I tried!
                                tag_input.after('<textarea id="' + tag_input.attr('id') + '" name="' + tag_input.attr('name') + '" rows="3">' + tag_input.val() + '</textarea>').remove();
                                //$('#form-field-tags').autosize({append: "\n"});
                            }


                            //////////

                            //typeahead.js
                            //example taken from plugin's page at: https://twitter.github.io/typeahead.js/examples/
                            var substringMatcher = function (strs) {
                                return function findMatches(q, cb) {
                                    var matches, substringRegex;

                                    // an array that will be populated with substring matches
                                    matches = [];

                                    // regex used to determine if a string contains the substring `q`
                                    substrRegex = new RegExp(q, 'i');

                                    // iterate through the pool of strings and for any string that
                                    // contains the substring `q`, add it to the `matches` array
                                    $.each(strs, function (i, str) {
                                        if (substrRegex.test(str)) {
                                            // the typeahead jQuery plugin expects suggestions to a
                                            // JavaScript object, refer to typeahead docs for more info
                                            matches.push({value: str});
                                        }
                                    });

                                    cb(matches);
                                };
                            };

                            $('input.typeahead').typeahead({
                                hint: true,
                                highlight: true,
                                minLength: 1
                            }, {
                                name: 'states',
                                displayKey: 'value',
                                source: substringMatcher(ace.vars['US_STATES'])
                            });



                            /////////
                            $('#modal-form input[type=file]').ace_file_input({
                                style: 'well',
                                btn_choose: 'Drop files here or click to choose',
                                btn_change: null,
                                no_icon: 'ace-icon fa fa-cloud-upload',
                                droppable: true,
                                thumbnail: 'large'
                            });

                            //chosen plugin inside a modal will have a zero width because the select element is originally hidden
                            //and its width cannot be determined.
                            //so we set the width after modal is show
                            $('#modal-form').on('shown.bs.modal', function () {
                                $(this).find('.chosen-container').each(function () {
                                    $(this).find('a:first-child').css('width', '210px');
                                    $(this).find('.chosen-drop').css('width', '210px');
                                    $(this).find('.chosen-search input').css('width', '200px');
                                });
                            });
                            /**
                             //or you can activate the chosen plugin after modal is shown
                             //this way select element becomes visible with dimensions and chosen works as expected
                             $('#modal-form').on('shown', function () {
                             $(this).find('.modal-chosen').chosen();
                             })
                             */

                        });
                    </script>
            </c:if>
        </c:if>



    </body>
</html>
