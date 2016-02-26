var faccApp = angular.module('faccApp', ['faccServices']);

faccApp.controller('FaccController',  function($scope, $http, accStats, allStats, ncaaStats, ncaaStandings) {
 
    $scope.allStats = allStats;

    $scope.accStats = accStats;
    
    $scope.ncaaStats = ncaaStats;
    
    $scope.ncaaStandings = ncaaStandings;
      
    allStats($scope);
  });