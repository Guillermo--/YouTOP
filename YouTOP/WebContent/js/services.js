angular.module('app')

.service('checkboxService', function(){
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
})

.service('criteriaService', function(){
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
})

.service('maxResultsService', function() {
	var selectedMaxResults;
	return {
		setMaxResults : function(maxResults){
			selectedMaxResults = maxResults;
		},
		getMaxResults : function(){
			return selectedMaxResults;
		}
	}
})

.service('keywordService', function() {
	var selectedKeyword;
	return {
		setKeyword : function(event) {
			selectedKeyword = event.target.value;
		},
		getKeyword : function(){
			return selectedKeyword;
		}
	}
})

.service('searchService', function($http) {
	var possibleCriteria = ["views", "popular"];
	var possibleMaxResults = ["5", "10", "25", "50"];
	
	return {
		validateInputs : function(criteria, categories, keyword, maxResults) {
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
})

.service('errorToastService', function() {
	var toast = angular.element(document.getElementById('errorToast'));
	return {
		setMessage : function(msge) {
			$(toast).text(msge);
		},
		showToast : function() {
			$(toast).animate({bottom: '5%'}, "slow");
			setTimeout(function(){
				$(toast).animate({bottom: '-10%'}, "slow");
			}, 3000);
		}
	}
});