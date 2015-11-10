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

app.service('searchService', function() {
	var possibleCriteria = ["views", "likes"];
	var possibleMaxResults = ["10", "25", "50", "100"];
	return {
		validateInputs : function(criteria, categories, keyword, maxResults) {
			alert("Criteria: "+criteria+"\nCategories: "+categories+"\nKeyword: "+keyword+"\nMaxresults: "+maxResults);
			if(criteria == null || criteria.length === 0 || (possibleCriteria.indexOf(criteria) < 0)){
				return "Invalid criteria.";
			}
			if((categories == null || categories == "") && (keyword == null || keyword == "")){
				return "Must choose a category, or enter a keyword.";
			}
			if(maxResults == null || maxResults.length === 0 || (possibleMaxResults.indexOf(maxResults) < 0)) {
				return "Invalid # Results";
			}
		},
		doSearch : function (criteria, categories, keyword, maxResults) {
			
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
	$scope.chooseCriteria = function(selectedCriteria) {
		criteriaService.setCriteria(selectedCriteria);
	}
});

app.controller('maxResultsController', function(maxResultsService, $scope){
	$scope.getMaxResults = function(maxResults) {
		maxResultsService.setMaxResults(maxResults);
	}
});

app.controller('keywordController', function(keywordService, $scope){
	$scope.setKeyword = function(){
		keywordService.setKeyword(event);
	}
});

app.controller('searchController', function(checkboxService, criteriaService, maxResultsService, keywordService, searchService, $scope) {
	$scope.startSearch = function(){
		$scope.selectedCategories = checkboxService.getSelectedItems();
		$scope.selectedCriteria = criteriaService.getCriteria();
		$scope.maxResults = maxResultsService.getMaxResults();
		$scope.keyword = keywordService.getKeyword();
				
		$scope.validationStatus = 
			searchService.validateInputs($scope.selectedCriteria, $scope.selectedCategories, $scope.keyword, $scope.maxResults);
		
		alert($scope.validationStatus);
	}
	
});

