angular.module('app')

.controller('checkboxController', function(checkboxService, $scope) {
	$scope.toggleSelection = function(){
		checkboxService.setSelectedItems(event);
	}
})

.controller('criteriaController', function(criteriaService, $scope) {
	angular.element(document).ready(function () {
		$scope.criteria = 'views';
		criteriaService.setCriteria($scope.criteria);
    });
	
	$scope.chooseCriteria = function(selectedCriteria) {
		criteriaService.setCriteria(selectedCriteria);
	}
})

.controller('maxResultsController', function(maxResultsService, $scope){
	angular.element(document).ready(function () {
		$scope.maxResults = '5';
		maxResultsService.setMaxResults($scope.maxResults);
    });
	
	$scope.getMaxResults = function(maxResults) {
		maxResultsService.setMaxResults(maxResults);
	}
})

.controller('keywordController', function(keywordService, $scope){
	$scope.setKeyword = function(){
		keywordService.setKeyword(event);
	}
})

.controller('searchController', function(checkboxService, criteriaService, maxResultsService, keywordService, searchService, $scope, $rootScope) {
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
})

.controller('displayResultsController', function($scope, $rootScope){
	$rootScope.$on('clearResults', function() {
		$scope.searchResults = {};
	});
	
	$rootScope.$on('searchResultsObtained', function(event, response){
		var spinner = angular.element(document.getElementById('progressBar'));
		spinner.hide();
		$scope.searchResults = response;
		console.log(response);
	});
})

.controller('loginController', function(routeService, $http, $scope, $rootScope, $window, $location){
	console.log("Initializing loginController");
	
	var clientId = '386721466748-hc6or76387qevpajh4iupstfsn11dc83.apps.googleusercontent.com';
	var apiKey = 'AIzaSyDXz-f_jsDJZ8mF46OUjcNPjplnDtyeqeA';
	var scopes = ['https://www.googleapis.com/auth/yt-analytics.readonly', 
	              'https://www.googleapis.com/auth/youtube.readonly', 
	              'https://www.googleapis.com/auth/plus.me'];

	angular.element($window).bind('load', function() {
		handlePageLoad();
	});
	
	function handlePageLoad() {
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
        	authorizeButton.innerHTML = "Logged in!";
        	authorizeButton.onclick = goDashboard;
        	loadAPIClientInterfaces();
      	} else {
      		console.log("Auth error...");
        	authorizeButton.style.visibility = '';
        	authorizeButton.onclick = handleAuthClick;
      	}
    }
    
    function goDashboard() {
		$window.location = './#/dashboard';
    }
    
    function handleAuthClick(event) {
    	console.log("Handling Auth Click");
      	gapi.auth.authorize({client_id: clientId, scope: scopes, immediate: false}, handleAuthResult)
      		.then(function(){        	
      			goDashboard();
      		});
      	return false;
    }
    
    function loadAPIClientInterfaces() {
        gapi.client.load('youtube', 'v3', function() {
          gapi.client.load('youtubeAnalytics', 'v1', function() {
            getUserChannel();
          });
        });
    }

    function getUserChannel() {
    	
        var request = gapi.client.youtube.channels.list({
          mine: true,
          part: 'id,contentDetails,snippet'
        });

        request.execute(function(response) {
          if ('error' in response) {
            console.log(response.error.message);
          } else {
        	  console.log("Getting user info...");
        	  getUserInfo();
            // We need the channel's channel ID to make calls to the Analytics API.
            // The channel ID value has the form "UCdLFeWKpkLhkguiMZUp8lWA".
        	console.log("Response: ", response);
            var channelId = response.items[0].id;
        	console.log("Channel Id: ", channelId);

            // Retrieve the playlist ID that uniquely identifies the playlist of
            // videos uploaded to the authenticated user's channel. This value has
            // the form "UUdLFeWKpkLhkguiMZUp8lWA".
            var uploadsListId = response.items[0].contentDetails.relatedPlaylists.uploads;
            // Use the playlist ID to retrieve the list of uploaded videos.
            console.log("Uploads: ",uploadsListId);
            getPlaylistItems(uploadsListId);

          }
        });
    }
    
    function getPlaylistItems(listId) {
        // See https://developers.google.com/youtube/v3/docs/playlistitems/list
        var request = gapi.client.youtube.playlistItems.list({
          playlistId: listId,
          part: 'snippet'
        });

        request.execute(function(response) {
          if ('error' in response) {
            displayMessage(response.error.message);
          } else {
            if ('items' in response) {
              // The jQuery.map() function iterates through all of the items in
              // the response and creates a new array that only contains the
              // specific property we're looking for: videoId.
              var videoIds = $.map(response.items, function(item) {
                return item.snippet.resourceId.videoId;
              });

              // Now that we know the IDs of all the videos in the uploads list,
              // we can retrieve information about each video.
              // getVideoMetadata(videoIds);
              console.log("Video Ids: ", videoIds);
            } else {
              console.log('There are no videos in your channel.');
            }
          }
        });
      }
    
    function getUserInfo() {
    	gapi.client.load('plus','v1', function(){
    		 var request = gapi.client.plus.people.get({
    		   'userId': 'me'
    		 });
    		 request.execute(function(resp) {
    		   console.log("Retrieved profile for: " + resp.displayName);
    		 });
    		});
    }
    
})

.controller('iframeController', function($scope) {
	$scope.getIframeSrc = function (id) {
		if(id.videoId == null) {
			return 'https://www.youtube.com/embed/' + id;
		} else {
			return 'https://www.youtube.com/embed/' + id.videoId;
		}
		  
	};
});