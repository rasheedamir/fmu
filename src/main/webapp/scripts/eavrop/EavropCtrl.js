'use strict';

angular.module('fmuClientApp').
    controller('EavropCtrl',['$scope','currentEavrop', function($scope,  currentEavrop){

    $scope.currentEavrop = currentEavrop;
}]);
