angular.module('app', ['ngRoute'])

.config(['$routeProvider', '$locationProvider', '$sceDelegateProvider', '$httpProvider', function($routeProvider, $locationProvider, $sceDelegateProvider, $httpProvider) {
	$sceDelegateProvider.
		resourceUrlWhitelist([
			'self',
			'https://www.youtube.com/embed/*'
		]);
	
	$routeProvider  
	.when('/', {
        templateUrl: 'pages/landingPage.html'
	})
	.when('/main', {
        templateUrl : 'pages/main.html'
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
