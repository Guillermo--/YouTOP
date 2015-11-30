var app = angular.module('app', []);

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
	var possibleCriteria = ["views", "likes"];
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
		
		doSearch : function (criteria, categories, keyword, maxResults) {
			return $http.get("./search.do", {
					params : {
						"criteria" : criteria, 
						"categories" : categories, 
						"keyword" : keyword, 
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
		criteriaService.setCriteria($scope.criteria);
    });
	
	$scope.chooseCriteria = function(selectedCriteria) {
		criteriaService.setCriteria(selectedCriteria);
	}
});

app.controller('maxResultsController', function(maxResultsService, $scope){
	angular.element(document).ready(function () {
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
		
		if($scope.validationStatus === "VALID") {
			var promise = searchService.doSearch($scope.selectedCriteria, $scope.selectedCategories, $scope.keyword, $scope.maxResults);
			promise.then(function(response){
				$rootScope.$broadcast('searchResultsObtained', response.data);
			});
		}
	}
});

app.controller('displayResultsController', function($scope, $rootScope){
	$rootScope.$on('searchResultsObtained', function(event, response){
		$scope.searchResults = response;
	});
});

