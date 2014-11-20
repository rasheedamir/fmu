'use strict';

angular.module('fmuClientApp').
    controller('EavropCtrl',['$scope','currentEavrop', 'AuthService', 'patientInfo', function($scope,  currentEavrop, AuthService, patientInfo){

    console.log(currentEavrop);
    $scope.patientInfo = patientInfo;
    $scope.currentEavrop = currentEavrop;
}]);
