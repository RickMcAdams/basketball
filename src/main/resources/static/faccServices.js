var faccServices = angular.module('faccServices', []);

faccServices.factory('accStats', ['$http', function accStatsFactory($http) {
	return function accStats(localScope) {		
		$http.get('http://localhost:8080/accStats').success(function(data){		   
        	localScope.players = data;       
      	});
    };
}]);

faccServices.factory('allStats', ['$http', function allStatsFactory($http) {
    return function allStats(localScope){
      	$http.get('http://localhost:8080/seasonStats').success(function(data) {    	
    		console.log('test');
        	localScope.players = data;
      	});
    };
}]);

faccServices.factory('ncaaStats', ['$http', function ncaaStatsFactory($http) {
    return function ncaaStats(localScope){
      	$http.get('http://localhost:8080/ncaaStats').success(function(data) {    	
    		console.log('test');
        	localScope.players = data;
      	});
    };
}]);

faccServices.factory('ncaaStandings', ['$http', function ncaaStandingsFactory($http) {
    return function ncaaStandings(localScope){
      	$http.get('http://localhost:8080/ncaaStandings').success(function(data) {    	
    		console.log('test');
        	localScope.owners = data;
      	});
    };
}]);

