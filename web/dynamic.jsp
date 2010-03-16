<head>
	<!--CSS file (default YUI Sam Skin) -->
	<link type="text/css" rel="stylesheet" href="http://yui.yahooapis.com/2.8.0r4/build/datatable/assets/skins/sam/datatable.css">
	 
	<!-- Dependencies -->
	<script src="http://yui.yahooapis.com/2.8.0r4/build/yahoo-dom-event/yahoo-dom-event.js"></script>
	<script src="http://yui.yahooapis.com/2.8.0r4/build/element/element-min.js"></script>
	<script src="http://yui.yahooapis.com/2.8.0r4/build/datasource/datasource-min.js"></script>
	 
	<!-- OPTIONAL: JSON Utility (for DataSource) -->
	<script src="http://yui.yahooapis.com/2.8.0r4/build/json/json-min.js"></script>
	 
	<!-- OPTIONAL: Connection Manager (enables XHR for DataSource) -->
	<script src="http://yui.yahooapis.com/2.8.0r4/build/connection/connection-min.js"></script>
	 
	<!-- OPTIONAL: Get Utility (enables dynamic script nodes for DataSource) -->
	<script src="http://yui.yahooapis.com/2.8.0r4/build/get/get-min.js"></script>
	 
	<!-- OPTIONAL: Drag Drop (enables resizeable or reorderable columns) -->
	<script src="http://yui.yahooapis.com/2.8.0r4/build/dragdrop/dragdrop-min.js"></script>
	 
	<!-- OPTIONAL: Calendar (enables calendar editors) -->
	<script src="http://yui.yahooapis.com/2.8.0r4/build/calendar/calendar-min.js"></script>
	 
	<!-- Source files -->
	<script src="http://yui.yahooapis.com/2.8.0r4/build/datatable/datatable-min.js"></script>
	
	
	<!-- Combo-handled YUI CSS files: --> 
	<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/combo?2.8.0r4/build/paginator/assets/skins/sam/paginator.css&2.8.0r4/build/datatable/assets/skins/sam/datatable.css"> 
	<!-- Combo-handled YUI JS files: --> 
	<script type="text/javascript" src="http://yui.yahooapis.com/combo?2.8.0r4/build/yahoo-dom-event/yahoo-dom-event.js&2.8.0r4/build/connection/connection-min.js&2.8.0r4/build/element/element-min.js&2.8.0r4/build/paginator/paginator-min.js&2.8.0r4/build/datasource/datasource-min.js&2.8.0r4/build/datatable/datatable-min.js&2.8.0r4/build/json/json-min.js"></script>
	
	<script type="text/javascript">
	//checkout http://developer.yahoo.com/yui/examples/datatable/dt_dynamicdata.html and
	
	YAHOO.util.Event.addListener(window, "load", function(){
			
	//YAHOO.example.DynamicData = function() {
	    // Column definitions
	    var myColumnDefs = [ // sortable:true enables sorting
	        {key:"firstName", label:"Firstname", sortable:true},
	        {key:"lastName", label:"Lastname", sortable:true},
	        {key:"ssn", label:"Social Security No.", sortable:true},
	        {key:"email", label:"Email", sortable:true}
	    ];
	
	    // DataSource instance
	    //var myDataSource = new YAHOO.util.DataSource("http://localhost:8080/yui/servlet/feeder?");
	    var myDataSource = new YAHOO.util.DataSource("servlet/feeder?");
	    //console.log("here 1");
	    //Doing JSP action
	    //var myDataSource = new YAHOO.util.DataSource("http://localhost:8080/yui/datafeed.jsp");
	    //var myDataSource = new YAHOO.util.XHRDataSource("http://localhost:8080/yui/datafeed.jsp?");
	    
	    myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
	    myDataSource.responseSchema = {
	        resultsList: "records",
	        fields: [
	            {key:"firstName"},
	            {key:"lastName"},
	            {key:"ssn"},
	            {key:"email"}
	        ],
	        metaFields: {
	            totalRecords: "totalRecords" // Access to value in the server response
	        }
	    };
	    
	    //console.log("here 2");
	    // DataTable configuration
	    var myConfigs = {
	        initialRequest: "sort=firstName&dir=asc&startIndex=0&results=25", // Initial request for first page of data
	        dynamicData: true, // Enables dynamic server-driven data
	        sortedBy : {key:"firstName", dir:YAHOO.widget.DataTable.CLASS_ASC}, // Sets UI initial sort arrow
	        //paginator: new YAHOO.widget.Paginator({ rowsPerPage: 25 }) // Enables pagination 
	        
	        paginator: new YAHOO.widget.Paginator({ rowsPerPage: 25, rowsPerPageOptions: [10,25,50,100],
	        template: "{FirstPageLink}{PreviousPageLink}{PageLinks}{NextPageLink}{LastPageLink} Items Per Page: {RowsPerPageDropdown}",
	        totalRecords: myDataSource.length }) // Enables pagination
	    };
	    
	    //console.log("here 3");
	    
	    // DataTable instance
	    var myDataTable = new YAHOO.widget.DataTable("dynamicdata", myColumnDefs, myDataSource, myConfigs);
	    // Update totalRecords on the fly with value from server
	    myDataTable.handleDataReturnPayload = function(oRequest, oResponse, oPayload) {
	        oPayload.totalRecords = oResponse.meta.totalRecords;
	        return oPayload;
	    }
	    
	    return {
	        ds: myDataSource,
	        dt: myDataTable
	    };
	        
	//} });
	});
	
	
	
	/*
	YAHOO.util.Event.addListener(window, "load", function(){
	
	YAHOO.example.DynamicData = function() {
    // Column definitions
    var myColumnDefs = [ // sortable:true enables sorting
        {key:"id", label:"ID", sortable:true},
        {key:"name", label:"Name", sortable:true},
        {key:"date", label:"Date", sortable:true, formatter:"date"},
        {key:"price", label:"Price", sortable:true},
        {key:"number", label:"Number", sortable:true}
    ];

    // Custom parser
    var stringToDate = function(sData) {
        var array = sData.split("-");
        return new Date(array[1] + " " + array[0] + ", " + array[2]);
    };
    
    // DataSource instance
    var myDataSource = new YAHOO.util.DataSource("assets/php/json_proxy.php?");
    myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
    myDataSource.responseSchema = {
        resultsList: "records",
        fields: [
            {key:"id", parser:"number"},
            {key:"name"},
            {key:"date", parser:stringToDate},
            {key:"price",parser:"number"},
            {key:"number",parser:"number"}
        ],
        metaFields: {
            totalRecords: "totalRecords" // Access to value in the server response
        }
    };
    
    // DataTable configuration
    var myConfigs = {
        initialRequest: "sort=id&dir=asc&startIndex=0&results=25", // Initial request for first page of data
        dynamicData: true, // Enables dynamic server-driven data
        sortedBy : {key:"id", dir:YAHOO.widget.DataTable.CLASS_ASC}, // Sets UI initial sort arrow
        paginator: new YAHOO.widget.Paginator({ rowsPerPage:25 }) // Enables pagination 
    };
    
    // DataTable instance
    var myDataTable = new YAHOO.widget.DataTable("dynamicdata", myColumnDefs, myDataSource, myConfigs);
    // Update totalRecords on the fly with value from server
    myDataTable.handleDataReturnPayload = function(oRequest, oResponse, oPayload) {
        oPayload.totalRecords = oResponse.meta.totalRecords;
        return oPayload;
    }
    
    return {
        ds: myDataSource,
        dt: myDataTable
    };
        
	} });*/
	

	

	</script>
</head>
<h1>HLLO</h1>

<body class="yui-skin-sam">
	Table should be below
	<div id="dynamicdata">
	should be replaced
	</div>
</body>