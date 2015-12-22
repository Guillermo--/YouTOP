var app = angular.module('app', ['ngRoute']);

app.config(['$routeProvider', '$locationProvider', '$sceDelegateProvider', '$httpProvider', function($routeProvider, $locationProvider, $sceDelegateProvider, $httpProvider) {
	$sceDelegateProvider.
		resourceUrlWhitelist([
			'self',
			'https://accounts.google.com/o/oauth2/auth?'+
			'client_id=386721466748-hc6or76387qevpajh4iupstfsn11dc83.apps.googleusercontent.com&'+
			'redirect_uri=http%3A%2F%2Flocalhost%3A8080%2FYouTOP%2Foauth2callback&'+
			'scope=https://www.googleapis.com/auth/yt-analytics.readonly&'+
			'response_type=code&'+
			'access_type=offline']);
	
	$routeProvider  
	.when('/', {
        templateUrl: 'pages/landingPage.html'
	})
	.when('/main', {
        templateUrl : 'pages/main.html'
	})
	.when('/login', {
		template : " ",
		controller : 'loginController'
	})
    .otherwise({
    	redirectTo : '/'
    });
}]);


//----------------------------------SERVICES--------------------------------------

app.service('checkboxService', function(){
	var selectedCategories = [];
	return {
		setSelectedItems : function(event) {
			var checkboxValue = event.target.value;
			var index = selectedCategories.indexOf(checkboxValue);
			if (index > -1) {
				selectedCategories.splice(index, 1);
			}
			else {
				selectedCategories.push(checkboxValue);
			}	
		},
		getSelectedItems : function(){
			return selectedCategories;
		}
	}
});

app.service('criteriaService', function(){
	var selectedCriteria;
	return {
		setCriteria : function(criteria){
			selectedCriteria = criteria;
			var keyword = angular.element(document.getElementById('textInput'));
			var isDisabled = keyword.prop('disabled');

			if(selectedCriteria == "popular"){
				if(isDisabled == false){
					keyword.prop('disabled', true);
					keyword.attr("placeholder", "Disabled");
				}
			}
			
			if(selectedCriteria == "views") {
				if(isDisabled == true) {
					keyword.prop('disabled', false);
					keyword.attr("placeholder", "Keyword");
				}
			}
		},
		getCriteria : function(){
			return selectedCriteria;
		}
	}
});

app.service('maxResultsService', function() {
	var selectedMaxResults;
	return {
		setMaxResults : function(maxResults){
			selectedMaxResults = maxResults;
		},
		getMaxResults : function(){
			return selectedMaxResults;
		}
	}
});

app.service('keywordService', function() {
	var selectedKeyword;
	return {
		setKeyword : function(event) {
			selectedKeyword = event.target.value;
		},
		getKeyword : function(){
			return selectedKeyword;
		}
	}
});

app.service('searchService', function($http) {
	var possibleCriteria = ["views", "popular"];
	var possibleMaxResults = ["5","10", "25", "50"];
	
	return {
		validateInputs : function(criteria, categories, keyword, maxResults) {
			console.log("Criteria:" +criteria);
			console.log("Categories: "+categories);
			console.log("Keyword: "+keyword);
			console.log("maxResults: "+maxResults);
			if(criteria == null || criteria.length === 0 || (possibleCriteria.indexOf(criteria) < 0)){
				return "Invalid criteria.";
			}
			if((categories == null || categories == "") && (keyword == null || keyword == "")){
				return "Must choose a category, or enter a keyword.";
			}
			if(maxResults == null || maxResults.length === 0 || (possibleMaxResults.indexOf(maxResults) < 0)) {
				return "Invalid # Results";
			}
			else {
				return "VALID";
			}
		},
		
		doSearchViews : function (criteria, categories, keyword, maxResults) {
			return $http.get("./searchViews.do", {
					params : {
						"criteria" : criteria, 
						"categories" : categories, 
						"keyword" : keyword, 
						"maxResults" : maxResults
			}});
		},
		
		doSearchPopular : function (criteria, categories, maxResults) {
			return $http.get("./searchPopular.do", {
					params : {
						"criteria" : criteria, 
						"categories" : categories, 
						"maxResults" : maxResults
			}});
		}
	}
});



//-------------------------------CONTROLLERS-------------------------------



app.controller('checkboxController', function(checkboxService, $scope) {
	$scope.toggleSelection = function(){
		checkboxService.setSelectedItems(event);
	}
});

app.controller('criteriaController', function(criteriaService, $scope) {
	angular.element(document).ready(function () {
		$scope.criteria = 'views';
		criteriaService.setCriteria($scope.criteria);
    });
	
	$scope.chooseCriteria = function(selectedCriteria) {
		criteriaService.setCriteria(selectedCriteria);
	}
});

app.controller('maxResultsController', function(maxResultsService, $scope){
	angular.element(document).ready(function () {
		$scope.maxResults = '5';
		maxResultsService.setMaxResults($scope.maxResults);
    });
	
	$scope.getMaxResults = function(maxResults) {
		maxResultsService.setMaxResults(maxResults);
	}
});

app.controller('keywordController', function(keywordService, $scope){
	$scope.setKeyword = function(){
		keywordService.setKeyword(event);
	}
});

app.controller('searchController', function(checkboxService, criteriaService, maxResultsService, keywordService, searchService, $scope, $rootScope) {
	$scope.startSearch = function(){
		$scope.selectedCategories = checkboxService.getSelectedItems();
		$scope.selectedCriteria = criteriaService.getCriteria();
		$scope.maxResults = maxResultsService.getMaxResults();
		$scope.keyword = keywordService.getKeyword();
		
		$scope.validationStatus = 
			searchService.validateInputs($scope.selectedCriteria, $scope.selectedCategories, $scope.keyword, $scope.maxResults);
		
		console.log("Validation status: ", $scope.validationStatus);
		
		if($scope.validationStatus === "VALID") {
			$rootScope.$broadcast('clearResults');
			var spinner = angular.element(document.getElementById('progressBar'));
			spinner.show();
			
			if($scope.selectedCriteria == "views") {
				var promise = searchService.doSearchViews($scope.selectedCriteria, $scope.selectedCategories, $scope.keyword, $scope.maxResults);
				promise.then(function(response){
					$rootScope.$broadcast('searchResultsObtained', response.data);
				});
			}
			else if($scope.selectedCriteria == "popular") {
				var promise = searchService.doSearchPopular($scope.selectedCriteria, $scope.selectedCategories, $scope.maxResults);
				promise.then(function(response){
					$rootScope.$broadcast('searchResultsObtained', response.data);
				});
			}
		}
	}
});

app.controller('displayResultsController', function($scope, $rootScope){
	$rootScope.$on('clearResults', function() {
		$scope.searchResults = {};
	});
	
	$rootScope.$on('searchResultsObtained', function(event, response){
		var spinner = angular.element(document.getElementById('progressBar'));
		spinner.hide();
		$scope.searchResults = response;
	});
});

app.controller('loginController', function($http, $scope, $rootScope, $window){
	console.log("Initializing loginController");
	
	var clientId = '386721466748-hc6or76387qevpajh4iupstfsn11dc83.apps.googleusercontent.com';
	var apiKey = 'AIzaSyDXz-f_jsDJZ8mF46OUjcNPjplnDtyeqeA';
	var scopes = 'https://www.googleapis.com/auth/yt-analytics.readonly';

	angular.element($window).bind('load', function() {
		handleClientLoad();
	});
	
	function handleClientLoad() {
		gapi.client.setApiKey(apiKey);
		window.setTimeout(checkAuth,1);
		console.log("Client loaded...");
	}
			
    function checkAuth() {
      	gapi.auth.authorize({client_id: clientId, scope: scopes, immediate: true}, handleAuthResult);
    }
    
    function handleAuthResult(authResult) {
      	var authorizeButton = document.getElementById('authorize-button');
      	console.log(authResult);
      	if (authResult && !authResult.error) {
      		console.log("Auth successful!");
        	authorizeButton.style.visibility = 'hidden';
        	//makeApiCall();
      	} else {
      		console.log("Auth result error...");
        	authorizeButton.style.visibility = '';
        	authorizeButton.onclick = handleAuthClick;
      	}
    }
    
    function handleAuthClick(event) {
    	console.log("Handling Auth Click");
      	gapi.auth.authorize({client_id: clientId, scope: scopes, immediate: false}, handleAuthResult);
      	return false;
    }
    

});


//------------------------- ROUTING FUNCTIONS ----------------------------

app.controller('routeController', function($scope, $location){
	$scope.go = function(path) {
		$location.path(path);
	}
});
