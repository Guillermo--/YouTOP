var app = angular.module('app', []);

app.controller('checkboxController', function($scope) {

	$scope.selectedItems = [];
	
	$scope.toggleSelection = function($event) {
		var checkboxValue = $event.target.value;
		var index = $scope.selectedItems.indexOf(checkboxValue);
		
		if (index > -1) {
		    $scope.selectedItems.splice(index, 1);
		}
		else {
		    $scope.selectedItems.push(checkboxValue);
		}
	}
});