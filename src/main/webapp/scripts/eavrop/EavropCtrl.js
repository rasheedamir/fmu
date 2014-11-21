'use strict';

angular.module('fmuClientApp').
    controller('EavropCtrl',['$scope','currentEavrop', 'AuthService', 'patientInfo', function($scope,  currentEavrop, AuthService, patientInfo){

    $scope.patientInfo = patientInfo;
    $scope.currentEavrop = currentEavrop;
}]);
