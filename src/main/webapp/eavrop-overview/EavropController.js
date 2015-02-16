'use strict';

angular.module('fmu.eavrop')
    .controller('EavropController', EavropController);

EavropController.$inject = ['$scope', 'currentEavrop', 'AuthService', 'patientInfo', 'eavropService'];
function EavropController($scope, currentEavrop, AuthService, patientInfo, EavropService) {
    $scope.patientInfo = patientInfo;
    $scope.currentEavrop = currentEavrop;

    $scope.getStatus = function(status) {
        return EavropService.getEavropConstants.statusMapping[status];
    };
}