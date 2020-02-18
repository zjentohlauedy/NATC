var ajaxHttpRequest  = null;
var ajaxHttpURL      = null;
var ajaxHttpResponse = null;
var ajaxHttpCallback = null;

function setAjaxURL( URL ) {
	
	ajaxHttpURL = URL;
}

function setAjaxCallback( func ) {
	
	ajaxHttpCallback = func;
}

function createRequestObject() {
	
    var xmlHttpObject;

    //depending on what the browser supports, use the right way to create the XMLHttpRequest object
    if (window.XMLHttpRequest) {
        // Mozilla, Safari would use this method ...
        xmlHttpObject = new XMLHttpRequest();

    } else if (window.ActiveXObject) {
        // IE would use this method ...
        xmlHttpObject = new ActiveXObject("Microsoft.XMLHTTP");
    }

    return xmlHttpObject;
}

function sendAjaxRequest() {
	
	//call the above function to create the XMLHttpRequest object
	ajaxHttpRequest = createRequestObject();
	
    //make a connection to the server ... specifying that you intend to make a GET request
    //to the server. Specifiy the page name and the URL parameters to send
	//ajaxHttpRequest.open( 'GET', '/natc/Game.do?game_id=' + gameId + '&ajax=1');
	ajaxHttpRequest.open( 'GET', ajaxHttpURL );

    //assign a handler for the response
	ajaxHttpRequest.onreadystatechange = processResponse;

    //actually send the request to the server
	ajaxHttpRequest.send( null );
}

function processResponse() {
	
    //check if the response has been received from the server
    if ( ajaxHttpRequest.readyState == 4  &&  ajaxHttpRequest.status == 200 ){

        //read and assign the response from the server
    	if ( window.DOMParser ) {
    		
    		ajaxHttpResponse = (new DOMParser()).parseFromString( ajaxHttpRequest.responseText, 'text/xml' );
    	}
    	else {
    	
    		ajaxHttpResponse = new ActiveXObject("Microsoft.XMLDOM");
    		ajaxHttpResponse.async = "false";
    		ajaxHttpResponse.loadXML( text ); 
    	}

    	ajaxHttpCallback( ajaxHttpResponse );
    }
}
