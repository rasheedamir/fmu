'use strict';

angular.module('fmuClientApp').
    controller('EavropCtrl',['$scope','currentEavrop', 'AuthService', 'patientInfo', 'EAVROP_TABLE', function($scope,  currentEavrop, AuthService, patientInfo, EAVROP_TABLE){

    $scope.patientInfo = patientInfo;
    $scope.currentEavrop = currentEavrop;
    $scope.getStatus = function (eavrop) {
        return  EAVROP_TABLE.statusMapping[eavrop.status];
    };
}]);
