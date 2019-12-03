var pathArray = window.location.pathname.split('/');
var secondLevelLocation = pathArray[1];
var pathUrl = encodeURI(window.location.protocol+"//"+ window.location.host+"/"+secondLevelLocation);
var username=$.cookie("cookieUsername");
var userId=$.cookie("cookieUserId");