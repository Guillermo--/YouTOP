angular.module('app', ['ngRoute'])

.config(['$routeProvider', '$locationProvider', '$sceDelegateProvider', '$httpProvider', function($routeProvider, $locationProvider, $sceDelegateProvider, $httpProvider) {
	$sceDelegateProvider.
		resourceUrlWhitelist([
			'self',
			'https://accounts.google.com/o/oauth2/auth?'+
			'client_id=386721466748-hc6or76387qevpajh4iupstfsn11dc83.apps.googleusercontent.com&'+
			'redirect_uri=http%3A%2F%2Flocalhost%3A8080%2FYouTOP%2Foauth2callback&'+
			'scope=https://www.googleapis.com/auth/yt-analytics.readonly&'+
			'response_type=code&'+
			'access_type=offline',
			'https://www.youtube.com/embed/*']);
	
	$routeProvider  
	.when('/', {
        templateUrl: 'pages/landingPage.html'
	})
	.when('/main', {
        templateUrl : 'pages/main.html'
	})
	.when('/dashboard', {
		templateUrl : 'pages/userDashboard.html'
	})
    .otherwise({
    	redirectTo : '/'
    });
}])




//------------------------- ROUTING FUNCTIONS ----------------------------
.service('routeService', function($location){
	return{
		go : function(path) {
			$location.path(path);
		}
	}
})

.controller('routeController', function(routeService, $scope){
	$scope.go = function(path) {
		routeService.go(path);
	}
});
